package ebd.api_ebd.service;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;

import ebd.api_ebd.domain.entity.Congregacao;
import ebd.api_ebd.domain.entity.Pessoa;
import ebd.api_ebd.dto.relatorio.AniversarianteDTO;
import ebd.api_ebd.repository.CongregacaoRepository;
import ebd.api_ebd.repository.PessoaRepository;

@Service
public class RelatorioAniversarianteService {

    private final PessoaRepository pessoaRepository;
    private final CongregacaoRepository congregacaoRepository;

    public RelatorioAniversarianteService(
            PessoaRepository pessoaRepository,
            CongregacaoRepository congregacaoRepository) {
        this.pessoaRepository = pessoaRepository;
        this.congregacaoRepository = congregacaoRepository;
    }

    public List<AniversarianteDTO> listarAniversariantesDoMes(Integer mes, Integer congregacaoId) {
        List<Pessoa> pessoas;
        
        if (congregacaoId != null) {
            pessoas = pessoaRepository.findByCongregacao(congregacaoId);
        } else {
            pessoas = pessoaRepository.findAllAtivos();
        }

        List<AniversarianteDTO> aniversariantes = new ArrayList<>();
        LocalDate hoje = LocalDate.now();

        for (Pessoa pessoa : pessoas) {
            if (pessoa.getDt_nascimento() != null) {
                int mesNascimento = pessoa.getDt_nascimento().getMonthValue();
                
                if (mesNascimento == mes) {
                    int idade = Period.between(pessoa.getDt_nascimento(), hoje).getYears();
                    
                    Congregacao congregacao = congregacaoRepository.findById(pessoa.getCongregacao())
                            .orElse(null);

                    AniversarianteDTO aniversariante = new AniversarianteDTO(
                            pessoa.getId(),
                            pessoa.getNome(),
                            pessoa.getDt_nascimento(),
                            idade,
                            pessoa.getTelefone(),
                            congregacao != null ? congregacao.getNome() : "N/A",
                            "N/A", // Classe será implementada quando houver relação aluno-classe
                            pessoa.getDt_nascimento().getDayOfMonth(),
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
        List<Pessoa> pessoas;
        
        if (congregacaoId != null) {
            pessoas = pessoaRepository.findByCongregacao(congregacaoId);
        } else {
            pessoas = pessoaRepository.findAllAtivos();
        }

        List<AniversarianteDTO> aniversariantes = new ArrayList<>();
        LocalDate hoje = LocalDate.now();

        for (Pessoa pessoa : pessoas) {
            if (pessoa.getDt_nascimento() != null) {
                LocalDate nascimento = pessoa.getDt_nascimento();
                
                if (nascimento.getDayOfMonth() == data.getDayOfMonth() &&
                    nascimento.getMonthValue() == data.getMonthValue()) {
                    
                    int idade = Period.between(nascimento, hoje).getYears();
                    
                    Congregacao congregacao = congregacaoRepository.findById(pessoa.getCongregacao())
                            .orElse(null);

                    AniversarianteDTO aniversariante = new AniversarianteDTO(
                            pessoa.getId(),
                            pessoa.getNome(),
                            nascimento,
                            idade,
                            pessoa.getTelefone(),
                            congregacao != null ? congregacao.getNome() : "N/A",
                            "N/A",
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
        List<Pessoa> pessoas;
        
        if (congregacaoId != null) {
            pessoas = pessoaRepository.findByCongregacao(congregacaoId);
        } else {
            pessoas = pessoaRepository.findAllAtivos();
        }

        List<AniversarianteDTO> aniversariantes = new ArrayList<>();
        LocalDate hoje = LocalDate.now();
        LocalDate dataLimite = hoje.plusDays(dias);

        for (Pessoa pessoa : pessoas) {
            if (pessoa.getDt_nascimento() != null) {
                LocalDate nascimento = pessoa.getDt_nascimento();
                LocalDate proximoAniversario = nascimento.withYear(hoje.getYear());
                
                // Se o aniversário já passou este ano, pegar do próximo ano
                if (proximoAniversario.isBefore(hoje)) {
                    proximoAniversario = proximoAniversario.plusYears(1);
                }
                
                if (!proximoAniversario.isAfter(dataLimite)) {
                    int idade = Period.between(nascimento, hoje).getYears();
                    
                    Congregacao congregacao = congregacaoRepository.findById(pessoa.getCongregacao())
                            .orElse(null);

                    AniversarianteDTO aniversariante = new AniversarianteDTO(
                            pessoa.getId(),
                            pessoa.getNome(),
                            nascimento,
                            idade,
                            pessoa.getTelefone(),
                            congregacao != null ? congregacao.getNome() : "N/A",
                            "N/A",
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
