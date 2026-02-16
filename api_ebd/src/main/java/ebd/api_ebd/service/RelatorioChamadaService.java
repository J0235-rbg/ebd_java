package ebd.api_ebd.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import ebd.api_ebd.domain.entity.AlunoDependente;
import ebd.api_ebd.domain.entity.AlunoResponsavel;
import ebd.api_ebd.domain.entity.Chamada;
import ebd.api_ebd.domain.entity.ChamadaDadosAdicionais;
import ebd.api_ebd.domain.entity.RegistroChamada;
import ebd.api_ebd.dto.relatorio.RelatorioAlunoChamadaDTO;
import ebd.api_ebd.dto.relatorio.RelatorioChamadaDTO;
import ebd.api_ebd.exception.NotFoundException;
import ebd.api_ebd.repository.AlunoDependenteRepository;
import ebd.api_ebd.repository.AlunoResponsavelRepository;
import ebd.api_ebd.repository.ChamadaRepository;
import ebd.api_ebd.repository.DadosAdicionaisRepository;
import ebd.api_ebd.repository.RegistroChamadaRepository;

@Service
public class RelatorioChamadaService {

    private final ChamadaRepository chamadaRepository;
    private final RegistroChamadaRepository registroChamadaRepository;
    private final AlunoDependenteRepository alunoDependenteRepository;
    private final AlunoResponsavelRepository alunoResponsavelRepository;
    private final DadosAdicionaisRepository dadosAdicionaisRepository;

    public RelatorioChamadaService(
            ChamadaRepository chamadaRepository,
            RegistroChamadaRepository registroChamadaRepository,
            AlunoDependenteRepository alunoDependenteRepository,
            AlunoResponsavelRepository alunoResponsavelRepository,
            DadosAdicionaisRepository dadosAdicionaisRepository) {
        this.chamadaRepository = chamadaRepository;
        this.registroChamadaRepository = registroChamadaRepository;
        this.alunoDependenteRepository = alunoDependenteRepository;
        this.alunoResponsavelRepository = alunoResponsavelRepository;
        this.dadosAdicionaisRepository = dadosAdicionaisRepository;
    }

    public RelatorioChamadaDTO gerarRelatorioChamada(Integer chamadaId) {
        Chamada chamada = chamadaRepository.findById(chamadaId)
                .orElseThrow(() -> new NotFoundException("Chamada não encontrada"));

        List<RegistroChamada> registros = registroChamadaRepository.findByChamadaId(chamadaId);

        int presentes = (int) registros.stream()
            .filter(r -> r.getStatus() != null && r.getStatus() == 1)
            .count();

        int ausentes = (int) registros.stream()
            .filter(r -> r.getStatus() != null && r.getStatus() == 2)
            .count();

        // Buscar dados adicionais da chamada para obter visitantes
        ChamadaDadosAdicionais dadosAdicionais = dadosAdicionaisRepository
                .findByChamadaId(chamadaId)
                .orElse(null);

        int visitantes = dadosAdicionais != null && dadosAdicionais.getVisitantes() != null 
                ? dadosAdicionais.getVisitantes() 
                : 0;

        int biblias = registros.stream()
                .mapToInt(r -> r.getBiblia() != null ? r.getBiblia() : 0)
                .sum();

        int revistas = registros.stream()
                .mapToInt(r -> r.getRevista() != null ? r.getRevista() : 0)
                .sum();

        // Buscar informações dos alunos
        List<RelatorioAlunoChamadaDTO> alunos = new ArrayList<>();
        for (RegistroChamada registro : registros) {
            String nomeAluno = "N/A";
            
            // Tenta buscar como dependente
            AlunoDependente dependente = alunoDependenteRepository.findById(registro.getaluno()).orElse(null);
            if (dependente != null) {
                nomeAluno = dependente.getNome();
            } else {
                // Se não for dependente, busca como responsável
                AlunoResponsavel responsavel = alunoResponsavelRepository.findById(registro.getaluno()).orElse(null);
                if (responsavel != null) {
                    nomeAluno = responsavel.getNome();
                }
            }
            
            RelatorioAlunoChamadaDTO alunoDTO = new RelatorioAlunoChamadaDTO(
                    registro.getaluno(),
                    nomeAluno,
                    registro.getStatus(),
                    registro.getBiblia(),
                    registro.getRevista()
            );
            alunos.add(alunoDTO);
        }

        RelatorioChamadaDTO relatorio = new RelatorioChamadaDTO();
        relatorio.setChamadaId(chamada.getId());
        relatorio.setData(chamada.getData());
        relatorio.setClasseId(chamada.getClasse());
        relatorio.setStatus(chamada.getStatus() != null ? chamada.getStatus().toString() : "N/A");
        relatorio.setPresentes(presentes);
        relatorio.setAusentes(ausentes);
        relatorio.setVisitantes(visitantes);
        relatorio.setBiblias(biblias);
        relatorio.setRevistas(revistas);
        relatorio.setAlunos(alunos);

        return relatorio;
    }

    public List<RelatorioChamadaDTO> listarRelatorioChamadas(Integer classeId, Integer trimestreId) {
        List<Chamada> chamadas;
        
        if (classeId != null && trimestreId != null) {
            chamadas = chamadaRepository.findByClasseAndTrimestre(classeId, trimestreId);
        } else if (classeId != null) {
            chamadas = chamadaRepository.findByClasse(classeId);
        } else if (trimestreId != null) {
            chamadas = chamadaRepository.findByTrimestre(trimestreId);
        } else {
            chamadas = chamadaRepository.findAll();
        }
        
        // Filtrar apenas chamadas abertas
        chamadas = chamadas.stream()
                .filter(c -> c.getStatus() != null && c.getStatus().name().equals("Aberto"))
                .toList();

        List<RelatorioChamadaDTO> relatorios = new ArrayList<>();
        
        for (Chamada chamada : chamadas) {
            RelatorioChamadaDTO relatorio = gerarRelatorioChamada(chamada.getId());
            relatorios.add(relatorio);
        }

        return relatorios;
    }
}
