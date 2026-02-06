package ebd.api_ebd.service;

import java.util.List;

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
        Integer chamadaId,
        Integer alunoId,
        int status,
        int biblia,
        int revista
    ) {
        Chamada chamada = chamadaRepository.findById(chamadaId)
            .orElseThrow(() -> 
                new IllegalStateException("Chamada não encontrada")
            );

        

        RegistroChamada registro = registroChamadaRepository
            .findByChamadaId(chamadaId).stream()
            .filter(r -> r.getaluno().equals(alunoId))
            .findFirst()
            .orElseGet(() -> {
                RegistroChamada novo = new RegistroChamada();
                novo.setChamada(chamadaId);
                novo.setaluno(alunoId);
                return novo;
            });

        registro.setStatus(status);
        registro.setBiblia(biblia);
        registro.setRevista(revista);

        return registroChamadaRepository.save(registro);
    }

    public List<RegistroChamada> listarPorChamada(Integer chamadaId){
        return registroChamadaRepository.findByChamadaId(chamadaId);
    }

    @Transactional
    public void consolidarDados(Integer chamadaId, Integer responsavelId){
        List<RegistroChamada> registros = 
            registroChamadaRepository.findByChamadaId(chamadaId);

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
            dadosAdicionaisRepository.findByChamadaId(chamadaId)
                .orElseGet(() -> {
                    ChamadaDadosAdicionais d = new ChamadaDadosAdicionais();
                    d.setChamada(chamadaRepository.findById(chamadaId).orElseThrow());
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
