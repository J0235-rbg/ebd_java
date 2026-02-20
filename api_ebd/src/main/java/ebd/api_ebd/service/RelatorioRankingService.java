package ebd.api_ebd.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import ebd.api_ebd.domain.entity.AlunoDependente;
import ebd.api_ebd.domain.entity.AlunoResponsavel;
import ebd.api_ebd.domain.entity.Chamada;
import ebd.api_ebd.domain.entity.Classe;
import ebd.api_ebd.domain.entity.Congregacao;
import ebd.api_ebd.domain.entity.RegistroChamada;
import ebd.api_ebd.domain.enums.ChamadaStatus;
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
        
       
            // Busca dependentes filtrados
            List<AlunoDependente> dependentes = alunoDependenteRepository.findAll().stream()
                .filter(d -> (classeId == null || d.getClasse().equals(classeId)))
                .filter(d -> (congregacaoId == null || d.getCongregacao().equals(congregacaoId)))
                .filter(d -> d.getStatus() != null && d.getStatus() == 1)
                .toList();
            
            // Busca responsáveis filtrados
            List<AlunoResponsavel> responsaveis = alunoResponsavelRepository.findAll().stream()
                .filter(r -> (classeId == null || r.getClasse().equals(classeId)))
                .filter(r -> (congregacaoId == null || r.getCongregacao().equals(congregacaoId)))
                .filter(r -> r.getStatus() != null && r.getStatus() == 1)
                .toList();
        
            for(AlunoDependente d : dependentes) alunos.add(new AlunoInfo(d.getId(), d.getNome(), d.getCongregacao(), d.getClasse(), true));
            for(AlunoResponsavel r : responsaveis) alunos.add(new AlunoInfo(r.getId(), r.getNome(), r.getCongregacao(), r.getClasse(), false));

            List<Integer> idsChamadaTrimestre = (trimestreId != null)
                ? chamadaRepository.findByTrimestre(trimestreId).stream().map(Chamada::getId).toList()
                : null;
            String nomeClasse = "N/A";
            if(classeId != null){
                nomeClasse = classeRepository.findById(classeId).map(Classe::getNome).orElse("N/A");
            }

        List<RankingAlunoDTO> ranking = new ArrayList<>();

        for (AlunoInfo aluno : alunos) {
            List<RegistroChamada> registros = registroChamadaRepository.findByAluno(aluno.getId());

            if(idsChamadaTrimestre != null){
                registros = registros.stream()
                    .filter(r -> idsChamadaTrimestre.contains(r.getChamada()))
                    .toList();
            }

            int totalPresencas = (int) registros.stream()
                    .filter(r -> r.getStatus() != null && r.getStatus() == 1)
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
                    nomeClasse, 
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
        return (limite != null && limite > 0 && ranking.size() > limite) 
           ? ranking.subList(0, limite) : ranking;
    }

    public List<RankingClasseDTO> gerarRankingClasses(Integer trimestreId, Integer congregacaoId, Integer limite) {
        List<Classe> classes = (congregacaoId != null)
            ? classeRepository.findByCongregacao(congregacaoId)
            : classeRepository.findAll();

        List<RankingClasseDTO> ranking = new ArrayList<>();

        for (Classe classe : classes) {
            List<Chamada> chamadas = chamadaRepository.findAll().stream()
                    .filter(c -> c.getClasse().equals(classe.getId()))
                    .filter(c -> c.getStatus() == ChamadaStatus.Fechado)
                    .filter(c -> trimestreId == null || c.getTrim().equals(trimestreId))
                    .toList();

            int totalChamadas = chamadas.size();
            if(totalChamadas == 0) continue;

            List<Integer> idsChamadas = chamadas.stream().map(Chamada::getId).toList();
            List<RegistroChamada> registros = registroChamadaRepository.findByChamadaIn(idsChamadas);

            long totalAtivos = alunoDependenteRepository.findByClasse(classe.getId()).stream()
                    .filter(d -> d.getStatus() != null && d.getStatus() == 1)
                    .count() +
                    alunoResponsavelRepository.findByClasse(classe.getId()).stream()
                    .filter(r -> r.getStatus() != null && r.getStatus() ==1)
                    .count();
            if(totalAtivos == 0) continue;

            int totalPresencas = (int) registros.stream()
                    .filter(r -> r.getStatus() != null && r.getStatus() == 1)
                    .count();
            int totalBiblias = registros.stream().mapToInt(r -> r.getBiblia() != null ? r.getBiblia() : 0).sum();
            int totalRevistas = registros.stream().mapToInt(r -> r.getRevista() != null ? r.getRevista() : 0).sum();

                                        

            double mediaPresencas = (double) totalPresencas / totalChamadas;
            double mediaBiblias = (double) totalBiblias / totalChamadas;
            double mediaRevistas = (double) totalRevistas / totalChamadas;
            double mediaAusentes = (double) totalAtivos- mediaPresencas;

            double presencasPossiveis = (double) totalAtivos * totalChamadas;
            double percentualPresenca = (totalPresencas * 100.0) / presencasPossiveis;

            
            int totalAusentes = (int) presencasPossiveis - totalPresencas;
            // Calcular pontuação: média de presenças + média de bíblias + média de revistas
            double pontuacao = mediaPresencas + mediaBiblias + mediaRevistas;


            RankingClasseDTO rankingDTO = new RankingClasseDTO();
            rankingDTO.setClasseId(classe.getId());
            rankingDTO.setNomeClasse(classe.getNome());
            rankingDTO.setTotalAlunos((int) totalAtivos);
            rankingDTO.setPontuacao((int) Math.round(pontuacao));
            rankingDTO.setMediaPresencas((int) Math.round(mediaPresencas));
            rankingDTO.setTotalAusentes((int) Math.round(mediaAusentes));
            rankingDTO.setPercentualPresenca(percentualPresenca);
            rankingDTO.setTotalBiblias((int) Math.round(mediaBiblias));
            rankingDTO.setTotalRevistas((int) Math.round(mediaRevistas));

            congregacaoRepository.findById(classe.getCongregacao())
                .ifPresent(cong -> rankingDTO.setCongregacao(cong.getNome()));

            ranking.add(rankingDTO);
        }

        // Ordenar por pontuação (decrescente)
        ranking.sort(Comparator.comparing(RankingClasseDTO::getPontuacao).reversed());

        // Definir posições
        for (int i = 0; i < ranking.size(); i++) {
            ranking.get(i).setPosicao(i + 1);
        }

        // Limitar resultados se especificado
         
        

        return (limite != null && limite > 0 && ranking.size() > limite) 
            ? ranking.subList(0, limite) : ranking;
    }

    public List<RankingAlunoDTO> rankingDependentes(Integer trimestreId, Integer limite) {
        Map<Integer, String> nomesClasses = carregarNomesClasses();
        List<AlunoInfoUnico> alunos = alunoDependenteRepository.findAll().stream()
                .filter(d -> d.getStatus() != null && d.getStatus() == 1)
            .map(d -> new AlunoInfoUnico(
                d.getId(),
                d.getNome(),
                nomesClasses.getOrDefault(d.getClasse(), "N/A"),
                d.getClasse()
            ))
                .toList();

        return processarRanking(trimestreId, alunos, limite);
    }

    // RANKING PARA RESPONSÁVEIS
    public List<RankingAlunoDTO> rankingResponsaveis(Integer trimestreId, Integer limite) {
        Map<Integer, String> nomesClasses = carregarNomesClasses();
        List<AlunoInfoUnico> alunos = alunoResponsavelRepository.findAll().stream()
                .filter(r -> r.getStatus() != null && r.getStatus() == 1)
            .map(r -> new AlunoInfoUnico(
                r.getId(),
                r.getNome(),
                nomesClasses.getOrDefault(r.getClasse(), "N/A"),
                r.getClasse()
            ))
                .toList();

        return processarRanking(trimestreId, alunos, limite);
    }
    // Record auxiliar interno para facilitar o transporte de dados
    private record AlunoInfoUnico(Integer id, String nome, String nomeClasse, Integer classeId) {}

    private Map<Integer, String> carregarNomesClasses() {
        return classeRepository.findAll().stream()
                .collect(Collectors.toMap(Classe::getId, Classe::getNome));
    }

    private List<RankingAlunoDTO> processarRanking(Integer trimestreId, List<AlunoInfoUnico> alunos, Integer limite) {
        // 1. Busca todas as chamadas FECHADAS do trimestre
        List<Chamada> todasChamadasFechadas = chamadaRepository.findByTrimAndStatus(trimestreId, ChamadaStatus.Fechado);
        
        if (todasChamadasFechadas.isEmpty()) return new ArrayList<>();

        List<RankingAlunoDTO> ranking = new ArrayList<>();

        for (AlunoInfoUnico aluno : alunos) {
            // 2. Filtra apenas as chamadas que pertencem à CLASSE deste aluno específico
            List<Integer> idsChamadasClasse = todasChamadasFechadas.stream()
                    .filter(c -> c.getClasse().equals(aluno.classeId()))
                    .map(Chamada::getId)
                    .toList();

            if (idsChamadasClasse.isEmpty()) continue;

            // 3. Busca os registros deste aluno APENAS para as chamadas da classe dele
            List<RegistroChamada> registros = registroChamadaRepository.findByAluno(aluno.id()).stream()
                    .filter(r -> idsChamadasClasse.contains(r.getChamada()))
                    .toList();

            int totalPresencas = (int) registros.stream()
                    .filter(r -> r.getStatus() != null && r.getStatus() == 1)
                    .count();

            int biblias = registros.stream()
                    .mapToInt(r -> r.getBiblia() != null ? r.getBiblia() : 0)
                    .sum();

            int revistas = registros.stream()
                    .mapToInt(r -> r.getRevista() != null ? r.getRevista() : 0)
                    .sum();

            // CÁLCULO CORRETO: Percentual sobre as chamadas FECHADAS da CLASSE DELE
            int totalChamadasClasse = idsChamadasClasse.size();
            double percentualPresenca = (totalPresencas * 100.0) / totalChamadasClasse;

            int pontuacao = totalPresencas + biblias + revistas;

            ranking.add(new RankingAlunoDTO(
                    0, aluno.id(), aluno.nome(), aluno.nomeClasse(), 
                    pontuacao, totalPresencas, biblias, revistas, percentualPresenca
            ));
        }

        // 4. Ordenação e Posição
        ranking.sort(Comparator.comparing(RankingAlunoDTO::getPontuacao)
                .thenComparing(RankingAlunoDTO::getPercentualPresenca).reversed());

        for (int i = 0; i < ranking.size(); i++) {
            ranking.get(i).setPosicao(i + 1);
        }

        return (limite != null && limite > 0 && ranking.size() > limite) 
                ? ranking.subList(0, limite) : ranking;
    }

    public List<RankingAlunoDTO> rankingGeralUnificado(Integer trimestreId, Integer limite) {
    // 1. Puxa os dois rankings separadamente (reutilizando a lógica que já existe)
    // Passamos 'null' no limite aqui para pegar todos, e limitamos apenas na lista final unificada
    List<RankingAlunoDTO> listaCompleta = new ArrayList<>();
    listaCompleta.addAll(rankingDependentes(trimestreId, null));
    listaCompleta.addAll(rankingResponsaveis(trimestreId, null));

    listaCompleta.sort(Comparator.comparing(RankingAlunoDTO::getPontuacao)
            .thenComparing(RankingAlunoDTO::getPercentualPresenca).reversed());
    // 4. Recalcula a posição de cada um na lista nova (1º, 2º, 3º...)
    for (int i = 0; i < listaCompleta.size(); i++) {
        listaCompleta.get(i).setPosicao(i + 1);
    }


    return (limite != null && listaCompleta.size() > limite) ? listaCompleta.subList(0, limite) : listaCompleta;
    }



   
    public List<RankingAlunoDTO> gerarRankingAlunosPorFrequencia(Integer trimestreId, Integer limite) {
        return rankingGeralUnificado(trimestreId, limite);
    }

    public List<RankingClasseDTO> gerarRankingClassesPorFrequencia(Integer trimestreId, Integer limite) {
        return gerarRankingClasses(trimestreId, null, limite);
    }
}
