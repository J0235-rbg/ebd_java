package ebd.api_ebd.service;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;

import ebd.api_ebd.domain.entity.AlunoDependente;
import ebd.api_ebd.domain.entity.AlunoResponsavel;
import ebd.api_ebd.domain.entity.Classe;
import ebd.api_ebd.domain.entity.Congregacao;
import ebd.api_ebd.dto.relatorio.AlunoInfo;
import ebd.api_ebd.dto.relatorio.AniversarianteDTO;
import ebd.api_ebd.repository.AlunoDependenteRepository;
import ebd.api_ebd.repository.AlunoResponsavelRepository;
import ebd.api_ebd.repository.ClasseRepository;
import ebd.api_ebd.repository.CongregacaoRepository;

@Service
public class RelatorioAniversarianteService {

    private final AlunoDependenteRepository alunoDependenteRepository;
    private final AlunoResponsavelRepository alunoResponsavelRepository;
    private final CongregacaoRepository congregacaoRepository;
    private final ClasseRepository classeRepository;

    public RelatorioAniversarianteService(
            AlunoDependenteRepository alunoDependenteRepository,
            AlunoResponsavelRepository alunoResponsavelRepository,
            CongregacaoRepository congregacaoRepository,
            ClasseRepository classeRepository) {
        this.alunoDependenteRepository = alunoDependenteRepository;
        this.alunoResponsavelRepository = alunoResponsavelRepository;
        this.congregacaoRepository = congregacaoRepository;
        this.classeRepository = classeRepository;
    }

    public List<AniversarianteDTO> listarAniversariantesDoMes(Integer mes, Integer congregacaoId) {
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

        List<AniversarianteDTO> aniversariantes = new ArrayList<>();
        LocalDate hoje = LocalDate.now();

        for (AlunoInfo aluno : alunos) {
            // Buscar data de nascimento
            LocalDate dtNascimento = null;
            if (aluno.isDependente()) {
                AlunoDependente dep = alunoDependenteRepository.findById(aluno.getId()).orElse(null);
                if (dep != null) dtNascimento = dep.getDt_nascimento();
            } else {
                AlunoResponsavel resp = alunoResponsavelRepository.findById(aluno.getId()).orElse(null);
                if (resp != null) dtNascimento = resp.getDt_nascimento();
            }
            
            if (dtNascimento != null) {
                int mesNascimento = dtNascimento.getMonthValue();
                
                if (mesNascimento == mes) {
                    int idade = Period.between(dtNascimento, hoje).getYears();
                    
                    Congregacao congregacao = congregacaoRepository.findById(aluno.getCongregacao())
                            .orElse(null);
                    
                    Classe classe = classeRepository.findById(aluno.getClasse())
                            .orElse(null);

                    AniversarianteDTO aniversariante = new AniversarianteDTO(
                            aluno.getId(),
                            aluno.getNome(),
                            dtNascimento,
                            idade,
                            null, // Alunos não têm telefone direto
                            congregacao != null ? congregacao.getNome() : "N/A",
                            classe != null ? classe.getNome() : "N/A",
                            dtNascimento.getDayOfMonth(),
                            mesNascimento
                    );
                    
                    aniversariantes.add(aniversariante);
                }
            }
        }

        // Ordenar por dia do mês
        aniversariantes.sort(Comparator.comparing(AniversarianteDTO::getDiaAniversario));

        return aniversariantes;
    }

    public List<AniversarianteDTO> listarAniversariantesDoDia(LocalDate data, Integer congregacaoId) {
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

        List<AniversarianteDTO> aniversariantes = new ArrayList<>();
        LocalDate hoje = LocalDate.now();

        for (AlunoInfo aluno : alunos) {
            // Buscar data de nascimento
            LocalDate nascimento = null;
            if (aluno.isDependente()) {
                AlunoDependente dep = alunoDependenteRepository.findById(aluno.getId()).orElse(null);
                if (dep != null) nascimento = dep.getDt_nascimento();
            } else {
                AlunoResponsavel resp = alunoResponsavelRepository.findById(aluno.getId()).orElse(null);
                if (resp != null) nascimento = resp.getDt_nascimento();
            }
            
            if (nascimento != null) {
                if (nascimento.getDayOfMonth() == data.getDayOfMonth() &&
                    nascimento.getMonthValue() == data.getMonthValue()) {
                    
                    int idade = Period.between(nascimento, hoje).getYears();
                    
                    Congregacao congregacao = congregacaoRepository.findById(aluno.getCongregacao())
                            .orElse(null);
                    
                    Classe classe = classeRepository.findById(aluno.getClasse())
                            .orElse(null);

                    AniversarianteDTO aniversariante = new AniversarianteDTO(
                            aluno.getId(),
                            aluno.getNome(),
                            nascimento,
                            idade,
                            null,
                            congregacao != null ? congregacao.getNome() : "N/A",
                            classe != null ? classe.getNome() : "N/A",
                            nascimento.getDayOfMonth(),
                            nascimento.getMonthValue()
                    );
                    
                    aniversariantes.add(aniversariante);
                }
            }
        }

        return aniversariantes;
    }

    public List<AniversarianteDTO> listarProximosAniversariantes(Integer dias, Integer congregacaoId) {
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

        List<AniversarianteDTO> aniversariantes = new ArrayList<>();
        LocalDate hoje = LocalDate.now();
        LocalDate dataLimite = hoje.plusDays(dias);

        for (AlunoInfo aluno : alunos) {
            // Buscar data de nascimento
            LocalDate nascimento = null;
            if (aluno.isDependente()) {
                AlunoDependente dep = alunoDependenteRepository.findById(aluno.getId()).orElse(null);
                if (dep != null) nascimento = dep.getDt_nascimento();
            } else {
                AlunoResponsavel resp = alunoResponsavelRepository.findById(aluno.getId()).orElse(null);
                if (resp != null) nascimento = resp.getDt_nascimento();
            }
            
            if (nascimento != null) {
                LocalDate proximoAniversario = nascimento.withYear(hoje.getYear());
                
                // Se o aniversário já passou este ano, pegar do próximo ano
                if (proximoAniversario.isBefore(hoje)) {
                    proximoAniversario = proximoAniversario.plusYears(1);
                }
                
                if (!proximoAniversario.isAfter(dataLimite)) {
                    int idade = Period.between(nascimento, hoje).getYears();
                    
                    Congregacao congregacao = congregacaoRepository.findById(aluno.getCongregacao())
                            .orElse(null);
                    
                    Classe classe = classeRepository.findById(aluno.getClasse())
                            .orElse(null);

                    AniversarianteDTO aniversariante = new AniversarianteDTO(
                            aluno.getId(),
                            aluno.getNome(),
                            nascimento,
                            idade,
                            null,
                            congregacao != null ? congregacao.getNome() : "N/A",
                            classe != null ? classe.getNome() : "N/A",
                            nascimento.getDayOfMonth(),
                            nascimento.getMonthValue()
                    );
                    
                    aniversariantes.add(aniversariante);
                }
            }
        }

        // Ordenar por data do próximo aniversário
        aniversariantes.sort((a, b) -> {
            LocalDate dataA = LocalDate.of(hoje.getYear(), a.getMesAniversario(), a.getDiaAniversario());
            LocalDate dataB = LocalDate.of(hoje.getYear(), b.getMesAniversario(), b.getDiaAniversario());
            
            if (dataA.isBefore(hoje)) dataA = dataA.plusYears(1);
            if (dataB.isBefore(hoje)) dataB = dataB.plusYears(1);
            
            return dataA.compareTo(dataB);
        });

        return aniversariantes;
    }
}
