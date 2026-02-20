package ebd.api_ebd.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import ebd.api_ebd.domain.entity.Chamada;
import ebd.api_ebd.domain.entity.ChamadaDadosAdicionais;
import ebd.api_ebd.domain.entity.Classe;
import ebd.api_ebd.domain.entity.RegistroChamada;
import ebd.api_ebd.domain.enums.ChamadaStatus;
import ebd.api_ebd.dto.request.RegistroChamadaDTO;
import ebd.api_ebd.exception.NotFoundException;
import ebd.api_ebd.repository.AlunoDependenteRepository;
import ebd.api_ebd.repository.AlunoResponsavelRepository;
import ebd.api_ebd.repository.ChamadaRepository;
import ebd.api_ebd.repository.DadosAdicionaisRepository;
import ebd.api_ebd.repository.RegistroChamadaRepository;
import jakarta.transaction.Transactional;

@Service
public class RegistroChamadaService {
    
    private final AlunoDependenteRepository alunoDependenteRepository;
    private final AlunoResponsavelRepository alunoResponsavelRepository;
    private final RegistroChamadaRepository registroChamadaRepository;
    private final ChamadaRepository chamadaRepository;
    private final DadosAdicionaisRepository dadosAdicionaisRepository;

    public RegistroChamadaService(
        AlunoDependenteRepository alunoDependenteRepository,
        AlunoResponsavelRepository alunoResponsavelRepository,
        RegistroChamadaRepository registroChamadaRepository,
        ChamadaRepository chamadaRepository,
        DadosAdicionaisRepository dadosAdicionaisRepository
    ){
        this.alunoDependenteRepository = alunoDependenteRepository;
        this.alunoResponsavelRepository = alunoResponsavelRepository;
        this.registroChamadaRepository = registroChamadaRepository;
        this.chamadaRepository = chamadaRepository;
        this.dadosAdicionaisRepository = dadosAdicionaisRepository;
    }

    @Transactional
    public void registrarLote(Integer chamadaId, List<RegistroChamadaDTO> registroDTO){
        Chamada chamada = chamadaRepository.findById(chamadaId)
            .orElseThrow(() -> new IllegalStateException("Chamada não encontrada"));

        if(chamada.getStatus() != ChamadaStatus.Aberto){
            throw new IllegalStateException("Chamada está fechada");
        }

        List<RegistroChamada> registrosExitentes = registroChamadaRepository.findByChamadaId(chamadaId);

        for(RegistroChamadaDTO dto : registroDTO){
            RegistroChamada registro = registrosExitentes.stream()
                .filter(r -> r.getaluno().equals(dto.alunoId()))
                .findFirst()
                .orElseGet(() ->{
                    RegistroChamada novo = new RegistroChamada();
                    novo.setChamada(chamadaId);
                    novo.setaluno(dto.alunoId());
                    return novo;
                });

                registro.setStatus(dto.status());
                registro.setBiblia(dto.biblia());
                registro.setRevista(dto.revista());

                registroChamadaRepository.save(registro);
        }
    }

   

    public List<RegistroChamada> listarPorChamada(Integer chamadaId){
        return registroChamadaRepository.findByChamadaId(chamadaId);
    }

    @Transactional
    public void consolidarDados(Integer chamadaId, Integer responsavelId, Integer visitantes, BigDecimal oferta ){
        Chamada chamada = chamadaRepository.findById(chamadaId)
            .orElseThrow(() -> new NotFoundException("Chamada não encontrada"));
        
        Integer classeId = chamada.getClasse();

        List<RegistroChamada> registros = 
            registroChamadaRepository.findByChamadaId(chamadaId);

        int presentes = 0;
        int ausentes = 0;
        int biblias = 0;
        int revistas = 0;

        for(RegistroChamada r : registros){
            switch (r.getStatus()) {
                case 1 -> presentes++;
                case 0 -> ausentes++;
            }
            biblias += (r.getBiblia() != null ? r.getBiblia() : 0);
            revistas += (r.getRevista() != null ? r.getRevista() : 0);
        }
        // 3. CALCULAR MATRICULADOS (Soma de dependentes e responsáveis ativos na classe)
        long totalDependentes = alunoDependenteRepository.findByClasse(classeId).stream()
            .filter(a -> a.getStatus() != null && a.getStatus() == 1).count();
    
        long totalResponsaveis = alunoResponsavelRepository.findByClasse(classeId).stream()
            .filter(a -> a.getStatus() != null && a.getStatus() == 1).count();
    
        int matriculadosAtivos = (int) (totalDependentes + totalResponsaveis);
        ChamadaDadosAdicionais dados = 
            dadosAdicionaisRepository.findByChamadaId(chamadaId)
                .orElseGet(() -> {
                    ChamadaDadosAdicionais d = new ChamadaDadosAdicionais();
                    d.setChamada(chamadaRepository.findById(chamadaId).orElseThrow());
                    return d;
                });

            dados.setPresentes(presentes);
            dados.setAusentes(ausentes);
            dados.setVisitantes(visitantes);
            dados.setOferta(oferta);
            dados.setBiblias(biblias);
            dados.setRevistas(revistas);
            dados.setMatriculados(matriculadosAtivos);
            dados.setTotalPresenca(presentes + visitantes);
            dados.setResponsavel(responsavelId);

            dadosAdicionaisRepository.save(dados);
    }
}
