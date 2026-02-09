package ebd.api_ebd.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import ebd.api_ebd.domain.entity.AlunoDependente;
import ebd.api_ebd.domain.entity.AlunoResponsavel;
import ebd.api_ebd.domain.entity.Classe;
import ebd.api_ebd.domain.entity.Congregacao;
import ebd.api_ebd.domain.entity.RegistroChamada;
import ebd.api_ebd.dto.relatorio.AlunoInfo;
import ebd.api_ebd.dto.relatorio.RelatorioAlunoDTO;
import ebd.api_ebd.exception.NotFoundException;
import ebd.api_ebd.repository.AlunoDependenteRepository;
import ebd.api_ebd.repository.AlunoResponsavelRepository;
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

    public RelatorioAlunoService(
            AlunoDependenteRepository alunoDependenteRepository,
            AlunoResponsavelRepository alunoResponsavelRepository,
            RegistroChamadaRepository registroChamadaRepository,
            CongregacaoRepository congregacaoRepository,
            ClasseRepository classeRepository) {
        this.alunoDependenteRepository = alunoDependenteRepository;
        this.alunoResponsavelRepository = alunoResponsavelRepository;
        this.registroChamadaRepository = registroChamadaRepository;
        this.congregacaoRepository = congregacaoRepository;
        this.classeRepository = classeRepository;
    }

    private AlunoInfo buscarAluno(Integer alunoId) {
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

    public RelatorioAlunoDTO gerarRelatorioAluno(Integer alunoId, Integer trimestreId) {
        AlunoInfo aluno = buscarAluno(alunoId);
        if (aluno == null) {
            throw new NotFoundException("Aluno não encontrado");
        }

        List<RegistroChamada> registros = registroChamadaRepository.findAll().stream()
                .filter(r -> r.getaluno().equals(alunoId))
                .toList();

        int totalPresencas = (int) registros.stream()
                .filter(r -> r.getStatus() == 1) // 1 = presente
                .count();

        int totalFaltas = (int) registros.stream()
                .filter(r -> r.getStatus() == 0) // 0 = ausente
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
        
        Classe classe = classeRepository.findById(aluno.getClasse())
                .orElse(null);

        RelatorioAlunoDTO relatorio = new RelatorioAlunoDTO();
        relatorio.setAlunoId(aluno.getId());
        relatorio.setNome(aluno.getNome());
        relatorio.setDataNascimento(null); // Alunos não têm data de nascimento nessas tabelas
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

    public List<RelatorioAlunoDTO> listarRelatoriosAlunos(Integer congregacaoId, Integer trimestreId) {
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

        List<RelatorioAlunoDTO> relatorios = new ArrayList<>();
        
        for (AlunoInfo aluno : alunos) {
            RelatorioAlunoDTO relatorio = gerarRelatorioAluno(aluno.getId(), trimestreId);
            relatorios.add(relatorio);
        }

        return relatorios;
    }
}
