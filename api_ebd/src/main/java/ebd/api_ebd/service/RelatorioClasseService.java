package ebd.api_ebd.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import ebd.api_ebd.domain.entity.Chamada;
import ebd.api_ebd.domain.entity.Classe;
import ebd.api_ebd.domain.entity.Congregacao;
import ebd.api_ebd.domain.entity.Pessoa;
import ebd.api_ebd.domain.entity.RegistroChamada;
import ebd.api_ebd.dto.relatorio.RelatorioClasseDTO;
import ebd.api_ebd.exception.NotFoundException;
import ebd.api_ebd.repository.ChamadaRepository;
import ebd.api_ebd.repository.ClasseRepository;
import ebd.api_ebd.repository.CongregacaoRepository;
import ebd.api_ebd.repository.PessoaRepository;
import ebd.api_ebd.repository.RegistroChamadaRepository;

@Service
public class RelatorioClasseService {

    private final ClasseRepository classeRepository;
    private final ChamadaRepository chamadaRepository;
    private final RegistroChamadaRepository registroChamadaRepository;
    private final CongregacaoRepository congregacaoRepository;
    private final PessoaRepository pessoaRepository;

    public RelatorioClasseService(
            ClasseRepository classeRepository,
            ChamadaRepository chamadaRepository,
            RegistroChamadaRepository registroChamadaRepository,
            CongregacaoRepository congregacaoRepository,
            PessoaRepository pessoaRepository) {
        this.classeRepository = classeRepository;
        this.chamadaRepository = chamadaRepository;
        this.registroChamadaRepository = registroChamadaRepository;
        this.congregacaoRepository = congregacaoRepository;
        this.pessoaRepository = pessoaRepository;
    }

    public RelatorioClasseDTO gerarRelatorioClasse(Integer classeId, Integer trimestreId) {
        Classe classe = classeRepository.findById(classeId)
                .orElseThrow(() -> new NotFoundException("Classe não encontrada"));

        List<Chamada> chamadas = chamadaRepository.findAll().stream()
                .filter(c -> c.getClasse().equals(classeId))
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
        int mediaFaltas = totalChamadas > 0 ? totalFaltas / totalChamadas : 0;
        
        double percentualPresenca = (totalPresencas + totalFaltas) > 0 
                ? (totalPresencas * 100.0) / (totalPresencas + totalFaltas) 
                : 0.0;

        // Buscar informações adicionais
        Congregacao congregacao = congregacaoRepository.findById(classe.getCongregacao())
                .orElse(null);

        Pessoa professor = classe.getProfessor1() != null 
                ? pessoaRepository.findById(classe.getProfessor1()).orElse(null)
                : null;

        RelatorioClasseDTO relatorio = new RelatorioClasseDTO();
        relatorio.setClasseId(classe.getId());
        relatorio.setNomeClasse(classe.getNome());
        relatorio.setCongregacao(congregacao != null ? congregacao.getNome() : "N/A");
        relatorio.setProfessorPrincipal(professor != null ? professor.getNome() : "N/A");
        relatorio.setTotalAlunos(0); // Será implementado quando houver tabela de matrícula
        relatorio.setTotalChamadas(totalChamadas);
        relatorio.setMediaPresencas(mediaPresencas);
        relatorio.setMediaFaltas(mediaFaltas);
        relatorio.setPercentualPresenca(percentualPresenca);
        relatorio.setTotalBiblias(totalBiblias);
        relatorio.setTotalRevistas(totalRevistas);

        return relatorio;
    }

    public List<RelatorioClasseDTO> listarRelatoriosClasses(Integer congregacaoId, Integer trimestreId) {
        List<Classe> classes;
        
        if (congregacaoId != null) {
            classes = classeRepository.findByCongregacao(congregacaoId);
        } else {
            classes = classeRepository.findAll();
        }

        List<RelatorioClasseDTO> relatorios = new ArrayList<>();
        
        for (Classe classe : classes) {
            RelatorioClasseDTO relatorio = gerarRelatorioClasse(classe.getId(), trimestreId);
            relatorios.add(relatorio);
        }

        return relatorios;
    }
}
