package ebd.api_ebd.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;

import ebd.api_ebd.domain.entity.AlunoDependente;
import ebd.api_ebd.domain.entity.AlunoResponsavel;
import ebd.api_ebd.domain.entity.Chamada;
import ebd.api_ebd.domain.entity.Classe;
import ebd.api_ebd.domain.entity.Congregacao;
import ebd.api_ebd.domain.entity.RegistroChamada;
import ebd.api_ebd.dto.relatorio.AlunoInfo;
import ebd.api_ebd.dto.relatorio.RankingAlunoDTO;
import ebd.api_ebd.dto.relatorio.RankingClasseDTO;
import ebd.api_ebd.repository.AlunoDependenteRepository;
import ebd.api_ebd.repository.AlunoResponsavelRepository;
import ebd.api_ebd.repository.ChamadaRepository;
import ebd.api_ebd.repository.ClasseRepository;
import ebd.api_ebd.repository.CongregacaoRepository;
import ebd.api_ebd.repository.RegistroChamadaRepository;

@Service
public class RelatorioRankingService {

    private final AlunoDependenteRepository alunoDependenteRepository;
    private final AlunoResponsavelRepository alunoResponsavelRepository;
    private final ClasseRepository classeRepository;
    private final ChamadaRepository chamadaRepository;
    private final RegistroChamadaRepository registroChamadaRepository;
    private final CongregacaoRepository congregacaoRepository;

    public RelatorioRankingService(
            AlunoDependenteRepository alunoDependenteRepository,
            AlunoResponsavelRepository alunoResponsavelRepository,
            ClasseRepository classeRepository,
            ChamadaRepository chamadaRepository,
            RegistroChamadaRepository registroChamadaRepository,
            CongregacaoRepository congregacaoRepository) {
        this.alunoDependenteRepository = alunoDependenteRepository;
        this.alunoResponsavelRepository = alunoResponsavelRepository;
        this.classeRepository = classeRepository;
        this.chamadaRepository = chamadaRepository;
        this.registroChamadaRepository = registroChamadaRepository;
        this.congregacaoRepository = congregacaoRepository;
    }

    public List<RankingAlunoDTO> gerarRankingAlunos(Integer trimestreId, Integer classeId, Integer congregacaoId, Integer limite) {
        List<AlunoInfo> alunos = new ArrayList<>();
        
        if (congregacaoId != null) {
            // Busca dependentes da congregação
            List<AlunoDependente> dependentes = alunoDependenteRepository.findByCongregacao(congregacaoId);
            for (AlunoDependente d : dependentes) {
                alunos.add(new AlunoInfo(d.getId(), d.getNome(), d.getCongregacao(), d.getClasse(), true));
            }
            
            // Busca responsáveis da congregação
            List<AlunoResponsavel> responsaveis = alunoResponsavelRepository.findByCongregacao(congregacaoId);
            for (AlunoResponsavel r : responsaveis) {
                alunos.add(new AlunoInfo(r.getId(), r.getNome(), r.getCongregacao(), r.getClasse(), false));
            }
        } else {
            // Busca todos ativos
            List<AlunoDependente> dependentes = alunoDependenteRepository.findAllAtivos();
            for (AlunoDependente d : dependentes) {
                alunos.add(new AlunoInfo(d.getId(), d.getNome(), d.getCongregacao(), d.getClasse(), true));
            }
            
            List<AlunoResponsavel> responsaveis = alunoResponsavelRepository.findAllAtivos();
            for (AlunoResponsavel r : responsaveis) {
                alunos.add(new AlunoInfo(r.getId(), r.getNome(), r.getCongregacao(), r.getClasse(), false));
            }
        }

        List<RankingAlunoDTO> ranking = new ArrayList<>();

        for (AlunoInfo aluno : alunos) {
            List<RegistroChamada> registros = registroChamadaRepository.findAll().stream()
                    .filter(r -> r.getaluno().equals(aluno.getId()))
                    .toList();

            // Filtrar por trimestre se especificado
            if (trimestreId != null) {
                List<Integer> chamadasDoTrimestre = chamadaRepository.findByTrimestre(trimestreId).stream()
                        .map(Chamada::getId)
                        .toList();
                registros = registros.stream()
                        .filter(r -> chamadasDoTrimestre.contains(r.getChamada()))
                        .toList();
            }

            int totalPresencas = (int) registros.stream()
                    .filter(r -> r.getStatus() == 1)
                    .count();

            int biblias = registros.stream()
                    .mapToInt(r -> r.getBiblia() != null ? r.getBiblia() : 0)
                    .sum();

            int revistas = registros.stream()
                    .mapToInt(r -> r.getRevista() != null ? r.getRevista() : 0)
                    .sum();

            int totalChamadas = registros.size();
            double percentualPresenca = totalChamadas > 0 
                    ? (totalPresencas * 100.0) / totalChamadas 
                    : 0.0;

            // Calcular pontuação: presenças + bíblias + revistas
            int pontuacao = totalPresencas + biblias + revistas;

            RankingAlunoDTO rankingDTO = new RankingAlunoDTO(
                    0, // Posição será definida depois
                    aluno.getId(),
                    aluno.getNome(),
                    "N/A", // Classe
                    pontuacao,
                    totalPresencas,
                    biblias,
                    revistas,
                    percentualPresenca
            );

            ranking.add(rankingDTO);
        }

        // Ordenar por pontuação (decrescente)
        ranking.sort(Comparator.comparing(RankingAlunoDTO::getPontuacao).reversed());

        // Definir posições
        for (int i = 0; i < ranking.size(); i++) {
            ranking.get(i).setPosicao(i + 1);
        }

        // Limitar resultados se especificado
        if (limite != null && limite > 0 && ranking.size() > limite) {
            return ranking.subList(0, limite);
        }

        return ranking;
    }

    public List<RankingClasseDTO> gerarRankingClasses(Integer trimestreId, Integer congregacaoId, Integer limite) {
        List<Classe> classes;
        
        if (congregacaoId != null) {
            classes = classeRepository.findByCongregacao(congregacaoId);
        } else {
            classes = classeRepository.findAll();
        }

        List<RankingClasseDTO> ranking = new ArrayList<>();

        for (Classe classe : classes) {
            List<Chamada> chamadas = chamadaRepository.findAll().stream()
                    .filter(c -> c.getClasse().equals(classe.getId()))
                    .filter(c -> trimestreId == null || c.getTrim().equals(trimestreId))
                    .toList();

            List<RegistroChamada> todosRegistros = new ArrayList<>();
            for (Chamada chamada : chamadas) {
                List<RegistroChamada> registros = registroChamadaRepository.findByChamadaId(chamada.getId());
                todosRegistros.addAll(registros);
            }

            int totalPresencas = (int) todosRegistros.stream()
                    .filter(r -> r.getStatus() == 1)
                    .count();

            int totalFaltas = (int) todosRegistros.stream()
                    .filter(r -> r.getStatus() == 0)
                    .count();

            int totalBiblias = todosRegistros.stream()
                    .mapToInt(r -> r.getBiblia() != null ? r.getBiblia() : 0)
                    .sum();

            int totalRevistas = todosRegistros.stream()
                    .mapToInt(r -> r.getRevista() != null ? r.getRevista() : 0)
                    .sum();

            int totalChamadas = chamadas.size();
            int mediaPresencas = totalChamadas > 0 ? totalPresencas / totalChamadas : 0;
            
            double percentualPresenca = (totalPresencas + totalFaltas) > 0 
                    ? (totalPresencas * 100.0) / (totalPresencas + totalFaltas) 
                    : 0.0;

            // Calcular pontuação: média de presenças + total de bíblias + total de revistas
            int pontuacao = mediaPresencas + totalBiblias + totalRevistas;

            Congregacao congregacao = congregacaoRepository.findById(classe.getCongregacao())
                    .orElse(null);

            RankingClasseDTO rankingDTO = new RankingClasseDTO();
            rankingDTO.setPosicao(0); // Será definida depois
            rankingDTO.setClasseId(classe.getId());
            rankingDTO.setNomeClasse(classe.getNome());
            rankingDTO.setCongregacao(congregacao != null ? congregacao.getNome() : "N/A");
            rankingDTO.setPontuacao(pontuacao);
            rankingDTO.setTotalAlunos(0); // Será implementado quando houver tabela de matrícula
            rankingDTO.setMediaPresencas(mediaPresencas);
            rankingDTO.setPercentualPresenca(percentualPresenca);
            rankingDTO.setTotalBiblias(totalBiblias);
            rankingDTO.setTotalRevistas(totalRevistas);

            ranking.add(rankingDTO);
        }

        // Ordenar por pontuação (decrescente)
        ranking.sort(Comparator.comparing(RankingClasseDTO::getPontuacao).reversed());

        // Definir posições
        for (int i = 0; i < ranking.size(); i++) {
            ranking.get(i).setPosicao(i + 1);
        }

        // Limitar resultados se especificado
        if (limite != null && limite > 0 && ranking.size() > limite) {
            return ranking.subList(0, limite);
        }

        return ranking;
    }

    public List<RankingAlunoDTO> gerarRankingAlunosPorFrequencia(Integer trimestreId, Integer limite) {
        return gerarRankingAlunos(trimestreId, null, null, limite);
    }

    public List<RankingClasseDTO> gerarRankingClassesPorFrequencia(Integer trimestreId, Integer limite) {
        return gerarRankingClasses(trimestreId, null, limite);
    }
}
