package ebd.api_ebd.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        ChamadaDadosAdicionais dadosAdicionais = dadosAdicionaisRepository.findByChamadaId(chamadaId).orElse(null);

        Map<Integer, String> mapaNomes = buscarMapaNomesCertoParaClasse(registros, chamada.getClasse());

    int presentes = (int) registros.stream().filter(r -> r.getStatus() != null && r.getStatus() == 1).count();
    int ausentes = (int) registros.stream().filter(r -> r.getStatus() != null && r.getStatus() == 0).count();
    int visitantes = (dadosAdicionais != null) ? dadosAdicionais.getVisitantes() : 0;
    BigDecimal oferta = (dadosAdicionais != null) ? dadosAdicionais.getOferta() : BigDecimal.ZERO; 
    int biblias = registros.stream().mapToInt(r -> r.getBiblia() != null ? r.getBiblia() : 0).sum();
    int revistas = registros.stream().mapToInt(r -> r.getRevista() != null ? r.getRevista() : 0).sum();

    List<RelatorioAlunoChamadaDTO> alunos = registros.stream().map(reg -> {
        return new RelatorioAlunoChamadaDTO(
            reg.getaluno(), 
            mapaNomes.getOrDefault(reg.getaluno(), "Aluno não encontrado"), 
            reg.getStatus(), 
            reg.getBiblia(), 
            reg.getRevista()
        );
    }).toList();

    // Montagem do DTO (usando os seus setters)
    RelatorioChamadaDTO relatorio = new RelatorioChamadaDTO();
    relatorio.setChamadaId(chamada.getId());
    relatorio.setData(chamada.getData());
    relatorio.setClasseId(chamada.getClasse());
    relatorio.setStatus(chamada.getStatus() != null ? chamada.getStatus().toString() : "N/A");
    relatorio.setPresentes(presentes);
    relatorio.setAusentes(ausentes);
    relatorio.setVisitantes(visitantes);
    relatorio.setOferta(oferta);
    relatorio.setBiblias(biblias);
    relatorio.setRevistas(revistas);
    relatorio.setAlunos(alunos);

    return relatorio;
    }

    private Map<Integer, String> buscarMapaNomesParaChamada(List<RegistroChamada> registros) {
        List<Integer> idsAlunos = registros.stream().map(RegistroChamada::getaluno).toList();
        Map<Integer, String> nomes = new HashMap<>();

        alunoDependenteRepository.findAllById(idsAlunos)
            .forEach(d -> nomes.put(d.getId(), d.getNome()));
        
        alunoResponsavelRepository.findAllById(idsAlunos)
            .forEach(r -> nomes.put(r.getId(), r.getNome()));
        
        return nomes;
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

    private Map<Integer, String> buscarMapaNomesCertoParaClasse(List<RegistroChamada> registros, Integer classeId) {
    List<Integer> idsAlunos = registros.stream().map(RegistroChamada::getaluno).toList();
    Map<Integer, String> nomes = new HashMap<>();

    // Busca apenas dependentes que pertencem a esta classe
    alunoDependenteRepository.findAllById(idsAlunos).stream()
        .filter(d -> d.getClasse().equals(classeId))
        .forEach(d -> nomes.put(d.getId(), d.getNome()));
    
    // Busca apenas responsáveis que pertencem a esta classe
    alunoResponsavelRepository.findAllById(idsAlunos).stream()
        .filter(r -> r.getClasse().equals(classeId))
        .forEach(r -> nomes.put(r.getId(), r.getNome()));

    // Caso algum aluno tenha sido transferido de classe mas o registro antigo ficou:
    // Fazemos uma busca de segurança para IDs que ainda não foram encontrados no mapa
    for (Integer id : idsAlunos) {
        if (!nomes.containsKey(id)) {
            alunoDependenteRepository.findById(id).ifPresent(d -> nomes.put(d.getId(), d.getNome()));
            if (!nomes.containsKey(id)) {
                alunoResponsavelRepository.findById(id).ifPresent(r -> nomes.put(r.getId(), r.getNome()));
            }
        }
    }

    return nomes;
    }
}
