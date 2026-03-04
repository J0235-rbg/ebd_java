package ebd.api_ebd.service;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.stereotype.Service;

import ebd.api_ebd.domain.entity.AlunoResponsavel;
import ebd.api_ebd.domain.entity.Chamada;
import ebd.api_ebd.domain.entity.ChamadaDadosAdicionais;
import ebd.api_ebd.domain.entity.Pessoa;
import ebd.api_ebd.domain.entity.RegistroChamada;
import ebd.api_ebd.dto.request.ChamadaDadosAdicionaisDTO;
import ebd.api_ebd.exception.NotFoundException;
import ebd.api_ebd.repository.DadosAdicionaisRepository;
import ebd.api_ebd.repository.PessoaRepository;
import ebd.api_ebd.repository.RegistroChamadaRepository;
import jakarta.transaction.Transactional;
import ebd.api_ebd.repository.AlunoResponsavelRepository;
import ebd.api_ebd.repository.ChamadaRepository;

@Service
public class DadosAdicionaisService {

    private final DadosAdicionaisRepository dadosAdicionaisRepository;
    private final ChamadaRepository chamadaRepository;
    private final RegistroChamadaRepository registroChamadaRepository;
    private final AlunoResponsavelRepository alunoResponsavelRepository;
    private final PessoaRepository pessoaRepository;
    private final RegistroChamadaService registroChamadaService;


    public DadosAdicionaisService(DadosAdicionaisRepository dadosAdicionaisRepository, ChamadaRepository chamadaRepository, RegistroChamadaRepository registroChamadaRepository, AlunoResponsavelRepository alunoResponsavelRepository, PessoaRepository pessoaRepository, RegistroChamadaService registroChamadaService) {
        this.dadosAdicionaisRepository = dadosAdicionaisRepository;
        this.chamadaRepository = chamadaRepository;
        this.registroChamadaRepository = registroChamadaRepository;
        this.alunoResponsavelRepository = alunoResponsavelRepository;
        this.pessoaRepository = pessoaRepository;
        this.registroChamadaService = registroChamadaService;
    }

    public ChamadaDadosAdicionais buscarPorChamadaId(Integer chamadaId) {
        return dadosAdicionaisRepository.findByChamadaId(chamadaId)
                .orElseThrow(() -> new NotFoundException("Dados adicionais não encontrados para a chamada especificada"));
    }

    // public ChamadaDadosAdicionais atualizarVisitantesEOferta(Integer chamadaId, Integer visitantes, BigDecimal oferta) {
    //     ChamadaDadosAdicionais dados = buscarPorChamadaId(chamadaId);
        
    //     if (visitantes != null) {
    //         dados.setVisitantes(visitantes);
    //     }
        
    //     if (oferta != null) {
    //         dados.setOferta(oferta);
    //     }
        
    //     return dadosAdicionaisRepository.save(dados);
    // }

    public ChamadaDadosAdicionais buscarPorId(Integer id) {
        return dadosAdicionaisRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Dados adicionais não encontrados"));
    }

    @Transactional
    public ChamadaDadosAdicionaisDTO registrarDadosAdicionais(ChamadaDadosAdicionaisDTO dto) {
        // 1. Salva os dados normais (oferta, visitantes, etc)
        if (dto.getProfessorId() != null) {
            darPresencaAutomaticaAoProfessor(dto.getProfessorId(), dto.getChamadaId());
        }

        if(dto.getAuxiliaresIds() != null){
            for(Integer auxiliarId : dto.getAuxiliaresIds()){
                darPresencaAutomaticaAoProfessor(auxiliarId, dto.getChamadaId());
            }
        }

        registroChamadaService.consolidarDados(
        dto.getChamadaId(), 
        dto.getVisitantes(), 
        dto.getOferta(), 
        dto.getProfessorId()
        );

        // 2. LOGICA DA PRESENÇA AUTOMÁTICA


        return dto;
    }

    private void darPresencaAutomaticaAoProfessor(Integer pessoaId, Integer chamadaDadaId) {
    // 1. Busca a chamada que o professor acabou de ministrar para saber a DATA
    Chamada chamadaMinistrada = chamadaRepository.findById(chamadaDadaId).orElse(null);
    if (chamadaMinistrada == null) return;

    // 2. Busca o usuário (Pessoa) para encontrar o vínculo com o Aluno
    Pessoa professorPessoa = pessoaRepository.findById(pessoaId).orElse(null);
    
    // Verificamos se a pessoa existe e se ela tem um aluno_id vinculado
    if (professorPessoa != null && professorPessoa.getAlunoId() != null) {
        Integer idDoProfessorComoAluno = professorPessoa.getAlunoId();

        // 3. Busca os dados de aluno desse professor para saber em qual classe ele estuda
        AlunoResponsavel dadosAluno = alunoResponsavelRepository.findById(idDoProfessorComoAluno).orElse(null);
        
        if (dadosAluno != null) {
            Integer classeOriginalDoProfessor = dadosAluno.getClasse();

            // 4. Busca a chamada da classe ORIGINAL dele no mesmo dia
            Optional<Chamada> chamadaDaClasseDele = chamadaRepository
                    .findByClasseAndData(classeOriginalDoProfessor, chamadaMinistrada.getData());

            chamadaDaClasseDele.ifPresent(ch -> {
                // 5. Cria ou atualiza o registro de presença na classe de origem
                // Usamos o idDoProfessorComoAluno para garantir que o vínculo seja com a matrícula dele
                RegistroChamada registro = registroChamadaRepository
                        .findByChamadaAndAluno(ch.getId(), idDoProfessorComoAluno)
                        .orElse(new RegistroChamada());

                registro.setChamada(ch.getId());
                registro.setaluno(idDoProfessorComoAluno);
                registro.setStatus(1); // PRESENTE!
                registro.setBiblia(1); // Professor é exemplo, leva bíblia
                registro.setRevista(1); // Professor tem a revista da lição
                
                registroChamadaRepository.save(registro);
                System.out.println("Sucesso: Presença automática gerada para " + dadosAluno.getNome() + 
                                   " na classe " + classeOriginalDoProfessor);
            });
        }
    }
}
}
