package ebd.api_ebd.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import ebd.api_ebd.domain.entity.Chamada;
import ebd.api_ebd.domain.entity.ChamadaDadosAdicionais;
import ebd.api_ebd.domain.entity.RegistroChamada;
import ebd.api_ebd.repository.ChamadaRepository;
import ebd.api_ebd.repository.DadosAdicionaisRepository;
import ebd.api_ebd.repository.RegistroChamadaRepository;
import jakarta.transaction.Transactional;

@Service
public class RegistroChamadaService {
    
    private final RegistroChamadaRepository registroChamadaRepository;
    private final ChamadaRepository chamadaRepository;
    private final DadosAdicionaisRepository dadosAdicionaisRepository;

    public RegistroChamadaService(
        RegistroChamadaRepository registroChamadaRepository,
        ChamadaRepository chamadaRepository,
        DadosAdicionaisRepository dadosAdicionaisRepository
    ){
        this.registroChamadaRepository = registroChamadaRepository;
        this.chamadaRepository = chamadaRepository;
        this.dadosAdicionaisRepository = dadosAdicionaisRepository;
    }

    @Transactional
    public RegistroChamada registrarOuAtualizar(
        UUID chamadaId,
        UUID alunoId,
        int status,
        int biblia,
        int revista
    ) {
        Chamada chamada = chamadaRepository.findById(chamadaId)
            .orElseThrow(() -> 
                new IllegalStateException("Chamada não encontrada")
            );

        if(!"Aberto".equals(chamada.getStatus())) {
            throw new IllegalStateException("Chamada não está aberta");
        }

        RegistroChamada registro = registroChamadaRepository
            .findByChamada_Id(chamadaId).stream()
            .filter(r -> r.getId_aluno().equals(alunoId))
            .findFirst()
            .orElseGet(() -> {
                RegistroChamada novo = new RegistroChamada();
                novo.setChamada(chamada);
                novo.setId_aluno(alunoId);
                return novo;
            });

        registro.setStatus(status);
        registro.setBiblia(biblia);
        registro.setRevista(revista);

        return registroChamadaRepository.save(registro);
    }

    public List<RegistroChamada> listarPorChamada(UUID chamadaId){
        return registroChamadaRepository.findByChamada_Id(chamadaId);
    }

    @Transactional
    public void consolidarDados(UUID chamadaId, UUID responsavelId){
        List<RegistroChamada> registros = 
            registroChamadaRepository.findByChamada_Id(chamadaId);

        int presentes = 0;
        int ausentes = 0;
        int visitantes = 0;
        int biblias = 0;
        int revistas = 0;

        for(RegistroChamada r : registros){
            switch (r.getStatus()) {
                case 1 -> presentes++;
                case 2 -> ausentes++;
                case 3 -> visitantes++;
            }
            biblias += r.getBiblia();
            revistas += r.getRevista();
        }
        ChamadaDadosAdicionais dados = 
            dadosAdicionaisRepository.findByChamada(chamadaId)
                .orElseGet(() -> {
                    ChamadaDadosAdicionais d = new ChamadaDadosAdicionais();
                    d.setChamada(chamadaId);
                    return d;
                });

            dados.setPresentes(presentes);
            dados.setAusentes(ausentes);
            dados.setVisitantes(visitantes);
            dados.setBiblias(biblias);
            dados.setRevistas(revistas);
            dados.setTotalPresenca(presentes + visitantes);
            dados.setResponsavel(responsavelId);

            dadosAdicionaisRepository.save(dados);
    }
}
