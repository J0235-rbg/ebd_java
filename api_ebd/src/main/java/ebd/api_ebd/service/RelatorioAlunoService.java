package ebd.api_ebd.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
import ebd.api_ebd.dto.relatorio.RelatorioAlunoDTO;
import ebd.api_ebd.exception.NotFoundException;
import ebd.api_ebd.repository.AlunoDependenteRepository;
import ebd.api_ebd.repository.AlunoResponsavelRepository;
import ebd.api_ebd.repository.ChamadaRepository;
import ebd.api_ebd.repository.ClasseRepository;
import ebd.api_ebd.repository.CongregacaoRepository;
import ebd.api_ebd.repository.RegistroChamadaRepository;

@Service
public class RelatorioAlunoService {

    private final AlunoDependenteRepository alunoDependenteRepository;
    private final AlunoResponsavelRepository alunoResponsavelRepository;
    private final RegistroChamadaRepository registroChamadaRepository;
    private final CongregacaoRepository congregacaoRepository;
    private final ClasseRepository classeRepository;
    private final ChamadaRepository chamadaRepository;

    public RelatorioAlunoService(
            AlunoDependenteRepository alunoDependenteRepository,
            AlunoResponsavelRepository alunoResponsavelRepository,
            RegistroChamadaRepository registroChamadaRepository,
            CongregacaoRepository congregacaoRepository,
            ClasseRepository classeRepository,
            ChamadaRepository chamadaRepository) {
        this.alunoDependenteRepository = alunoDependenteRepository;
        this.alunoResponsavelRepository = alunoResponsavelRepository;
        this.registroChamadaRepository = registroChamadaRepository;
        this.congregacaoRepository = congregacaoRepository;
        this.classeRepository = classeRepository;
        this.chamadaRepository = chamadaRepository;
    }

    private AlunoInfo buscarAluno(Integer alunoId, Integer classeId) {
        if (classeId != null) {
            AlunoDependente dependente = alunoDependenteRepository.findByIdAndClasse(alunoId, classeId);
            if (dependente != null) {
                return new AlunoInfo(
                    dependente.getId(),
                    dependente.getNome(),
                    dependente.getCongregacao(),
                    dependente.getClasse(),
                    true
                );
            }

            AlunoResponsavel responsavel = alunoResponsavelRepository.findByIdAndClasse(alunoId, classeId);
            if (responsavel != null) {
                return new AlunoInfo(
                    responsavel.getId(),
                    responsavel.getNome(),
                    responsavel.getCongregacao(),
                    responsavel.getClasse(),
                    false
                );
            }

            return null;
        }

        // Tenta buscar como dependente primeiro
        AlunoDependente dependente = alunoDependenteRepository.findById(alunoId).orElse(null);
        if (dependente != null) {
            return new AlunoInfo(
                dependente.getId(),
                dependente.getNome(),
                dependente.getCongregacao(),
                dependente.getClasse(),
                true
            );
        }
        
        // Se não for dependente, busca como responsável
        AlunoResponsavel responsavel = alunoResponsavelRepository.findById(alunoId).orElse(null);
        if (responsavel != null) {
            return new AlunoInfo(
                responsavel.getId(),
                responsavel.getNome(),
                responsavel.getCongregacao(),
                responsavel.getClasse(),
                false
            );
        }
        
        return null;
    }

    private List<RegistroChamada> filtrarRegistrosPorChamada(List<RegistroChamada> registros,
            Integer classeId, Integer trimestreId) {
        if (classeId == null && trimestreId == null) {
            return registros;
        }

        List<Chamada> chamadas;
        if (classeId != null && trimestreId != null) {
            chamadas = chamadaRepository.findByClasseAndTrimestre(classeId, trimestreId);
        } else if (classeId != null) {
            chamadas = chamadaRepository.findByClasse(classeId);
        } else {
            chamadas = chamadaRepository.findByTrimestre(trimestreId);
        }

        Set<Integer> chamadasIds = new HashSet<>();
        for (Chamada chamada : chamadas) {
            chamadasIds.add(chamada.getId());
        }

        List<RegistroChamada> filtrados = new ArrayList<>();
        for (RegistroChamada registro : registros) {
            if (chamadasIds.contains(registro.getChamada())) {
                filtrados.add(registro);
            }
        }

        return filtrados;
    }

    private Integer inferirClasseId(List<RegistroChamada> registros) {
        if (registros.isEmpty()) {
            return null;
        }

        Map<Integer, Integer> registrosPorChamada = new HashMap<>();
        Set<Integer> chamadasIds = new HashSet<>();
        for (RegistroChamada registro : registros) {
            int chamadaId = registro.getChamada();
            chamadasIds.add(chamadaId);
            registrosPorChamada.put(chamadaId, registrosPorChamada.getOrDefault(chamadaId, 0) + 1);
        }

        Map<Integer, Integer> registrosPorClasse = new HashMap<>();
        for (Chamada chamada : chamadaRepository.findAllById(chamadasIds)) {
            int qtd = registrosPorChamada.getOrDefault(chamada.getId(), 0);
            registrosPorClasse.put(
                chamada.getClasse(),
                registrosPorClasse.getOrDefault(chamada.getClasse(), 0) + qtd
            );
        }

        Integer classeId = null;
        int maxRegistros = -1;
        for (Map.Entry<Integer, Integer> entry : registrosPorClasse.entrySet()) {
            if (entry.getValue() > maxRegistros) {
                maxRegistros = entry.getValue();
                classeId = entry.getKey();
            }
        }

        return classeId;
    }

    public RelatorioAlunoDTO gerarRelatorioAluno(Integer alunoId, Integer trimestreId, Integer classeId) {
        if (classeId == null) {
            throw new NotFoundException("Classe do aluno é obrigatória");
        }

        Integer classeIdEfetiva = classeId;

        AlunoInfo aluno = buscarAluno(alunoId, classeIdEfetiva);
        if (aluno == null) {
            throw new NotFoundException("Aluno não encontrado na classe informada");
        }

        List<Chamada> chamadas;
        if (trimestreId != null) {
            chamadas = chamadaRepository.findByClasseAndTrimestre(classeIdEfetiva, trimestreId);
        } else {
            chamadas = chamadaRepository.findByClasse(classeIdEfetiva);
        }

        if (chamadas.isEmpty()) {
            throw new NotFoundException("Nenhuma chamada encontrada para a classe informada");
        }

        List<Integer> chamadasIds = new ArrayList<>();
        for (Chamada chamada : chamadas) {
            chamadasIds.add(chamada.getId());
        }

        List<RegistroChamada> registros = registroChamadaRepository.findByAlunoAndChamadas(alunoId, chamadasIds);

        if (registros.isEmpty()) {
            throw new NotFoundException("Aluno sem registros na classe informada");
        }

        int totalPresencas = (int) registros.stream()
            .filter(r -> r.getStatus() != null && r.getStatus() == 1) // 1 = presente
            .count();

        int totalFaltas = (int) registros.stream()
            .filter(r -> r.getStatus() != null && r.getStatus() == 0) // 2 = ausente
            .count();

        int totalChamadas = registros.size();
        
        double percentualPresenca = totalChamadas > 0 
                ? (totalPresencas * 100.0) / totalChamadas 
                : 0.0;

        int biblias = registros.stream()
                .mapToInt(r -> r.getBiblia() != null ? r.getBiblia() : 0)
                .sum();

        int revistas = registros.stream()
                .mapToInt(r -> r.getRevista() != null ? r.getRevista() : 0)
                .sum();

        // Buscar informações adicionais
        Congregacao congregacao = congregacaoRepository.findById(aluno.getCongregacao())
                .orElse(null);
        
        Classe classe = classeRepository.findById(classeIdEfetiva)
                .orElse(null);

        // Buscar data de nascimento do aluno correto
        java.time.LocalDate dataNascimento = null;
        if (aluno.isDependente()) {
            AlunoDependente dependente = alunoDependenteRepository.findByIdAndClasse(aluno.getId(), classeIdEfetiva);
            if (dependente != null) {
                dataNascimento = dependente.getDt_nascimento();
            }
        } else {
            AlunoResponsavel responsavel = alunoResponsavelRepository.findByIdAndClasse(aluno.getId(), classeIdEfetiva);
            if (responsavel != null) {
                dataNascimento = responsavel.getDt_nascimento();
            }
        }

        RelatorioAlunoDTO relatorio = new RelatorioAlunoDTO();
        relatorio.setAlunoId(aluno.getId());
        relatorio.setNome(aluno.getNome());
        relatorio.setDataNascimento(dataNascimento);
        relatorio.setTelefone(null);
        relatorio.setCongregacao(congregacao != null ? congregacao.getNome() : "N/A");
        relatorio.setClasse(classe != null ? classe.getNome() : "N/A");
        relatorio.setTotalPresencas(totalPresencas);
        relatorio.setTotalFaltas(totalFaltas);
        relatorio.setTotalChamadas(totalChamadas);
        relatorio.setPercentualPresenca(percentualPresenca);
        relatorio.setBiblias(biblias);
        relatorio.setRevistas(revistas);

        return relatorio;
    }

    public List<RelatorioAlunoDTO> listarRelatoriosAlunos(Integer congregacaoId, Integer trimestreId, Integer classeId) {
        List<Chamada> chamadasTrimestre = chamadaRepository.findByTrimAndStatus(trimestreId, ChamadaStatus.Fechado);
        List<Integer> idsChamadas = chamadasTrimestre.stream().map(Chamada::getId).toList();

        if(idsChamadas.isEmpty()) return new ArrayList<>();

        List<RegistroChamada> todosRegistros = registroChamadaRepository.findByChamadaIn(idsChamadas);
        Map<Integer, List<RegistroChamada>> registrosPorAluno = todosRegistros.stream()
            .collect(Collectors.groupingBy(RegistroChamada::getaluno));
        Map<Integer, String> mapaClasses = carregarNomesClasses();
        Map<Integer, String> mapaCongregacoes = carregarNomesCongregacoes();
        List<AlunoCompletoInfo> alunos = buscarAlunosComDetalhes(congregacaoId, classeId);

        List<RelatorioAlunoDTO> relatorios = new ArrayList<>();
        
       for (AlunoCompletoInfo aluno : alunos) {
        List<RegistroChamada> regsAluno = registrosPorAluno.getOrDefault(aluno.id(), new ArrayList<>());

        // Cálculos
        int presencas = (int) regsAluno.stream().filter(r -> r.getStatus() != null && r.getStatus() == 1).count();
        int faltas = (int) regsAluno.stream().filter(r -> r.getStatus() != null && r.getStatus() == 0).count();
        int biblias = regsAluno.stream().mapToInt(r -> r.getBiblia() != null ? r.getBiblia() : 0).sum();
        int revistas = regsAluno.stream().mapToInt(r -> r.getRevista() != null ? r.getRevista() : 0).sum();

        // Total de chamadas que a CLASSE dele teve no trimestre
        int totalAulasClasse = (int) chamadasTrimestre.stream()
                .filter(c -> c.getClasse().equals(aluno.classeId()))
                .count();

        double percentual = totalAulasClasse > 0 ? (presencas * 100.0) / totalAulasClasse : 0.0;

        // 5. Chamada do construtor com os 12 parâmetros na ordem correta
        relatorios.add(new RelatorioAlunoDTO(
            aluno.id(),                                 // alunoId
            aluno.nome(),                               // nome
            aluno.dataNascimento(),                     // dataNascimento
            aluno.telefone(),                           // telefone
            mapaCongregacoes.getOrDefault(aluno.congregacaoId(), "N/A"), // congregacao
            mapaClasses.getOrDefault(aluno.classeId(), "N/A"),          // classe
            presencas,                                  // totalPresencas
            faltas,                                     // totalFaltas
            totalAulasClasse,                           // totalChamadas
            percentual,                                 // percentualPresenca
            biblias,                                    // biblias
            revistas                                    // revistas
        ));
        }

        return relatorios;
    }

    private List<AlunoCompletoInfo> buscarAlunosComDetalhes(Integer congregacaoId, Integer classeId) {
    List<AlunoCompletoInfo> lista = new ArrayList<>();

    alunoDependenteRepository.findAll().stream()
        .filter(d -> d.getStatus() == 1)
        .filter(d -> congregacaoId == null || d.getCongregacao().equals(congregacaoId))
        .filter(d -> classeId == null || d.getClasse().equals(classeId))
        .forEach(d -> lista.add(new AlunoCompletoInfo(d.getId(), d.getNome(), d.getDt_nascimento(), "Dependente", d.getClasse(), d.getCongregacao())));

    alunoResponsavelRepository.findAll().stream()
        .filter(r -> r.getStatus() == 1)
        .filter(r -> congregacaoId == null || r.getCongregacao().equals(congregacaoId))
        .filter(r -> classeId == null || r.getClasse().equals(classeId))
        .forEach(r -> lista.add(new AlunoCompletoInfo(r.getId(), r.getNome(), r.getDt_nascimento(), "Responsável", r.getClasse(), r.getCongregacao())));

    return lista;
}

// Record auxiliar para o transporte interno
private record AlunoCompletoInfo(Integer id, String nome, LocalDate dataNascimento, String telefone, Integer classeId, Integer congregacaoId) {}

    private Map<Integer, String> carregarNomesCongregacoes() {
        return congregacaoRepository.findAll().stream()
                .collect(Collectors.toMap(Congregacao::getId, Congregacao::getNome, (a, b) -> a));
    }
    private Map<Integer, String> carregarNomesClasses() {
        return classeRepository.findAll().stream()
                .collect(Collectors.toMap(Classe::getId, Classe::getNome));
    }
}
