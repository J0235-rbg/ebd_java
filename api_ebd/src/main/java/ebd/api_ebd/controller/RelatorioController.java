package ebd.api_ebd.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ebd.api_ebd.dto.relatorio.AniversarianteDTO;
import ebd.api_ebd.dto.relatorio.RankingAlunoDTO;
import ebd.api_ebd.dto.relatorio.RankingClasseDTO;
import ebd.api_ebd.dto.relatorio.RankingIdadeGrupoDTO;
import ebd.api_ebd.dto.relatorio.RelatorioAlunoDTO;
import ebd.api_ebd.dto.relatorio.RelatorioChamadaDTO;
import ebd.api_ebd.dto.relatorio.RelatorioClasseDTO;
import ebd.api_ebd.dto.relatorio.RelatorioTrimestreDTO;
import ebd.api_ebd.service.RelatorioAlunoService;
import ebd.api_ebd.service.RelatorioAniversarianteService;
import ebd.api_ebd.service.RelatorioChamadaService;
import ebd.api_ebd.service.RelatorioClasseService;
import ebd.api_ebd.service.RelatorioIdadeGrupoService;
import ebd.api_ebd.service.RelatorioRankingService;
import ebd.api_ebd.service.RelatorioTrimestreService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/ebd/relatorios")
@Tag(name = "Relatórios", description = "Geração de relatórios e rankings da EBD")
public class RelatorioController {

    private final RelatorioAlunoService relatorioAlunoService;
    private final RelatorioClasseService relatorioClasseService;
    private final RelatorioChamadaService relatorioChamadaService;
    private final RelatorioTrimestreService relatorioTrimestreService;
    private final RelatorioAniversarianteService relatorioAniversarianteService;
    private final RelatorioRankingService relatorioRankingService;
    private final RelatorioIdadeGrupoService relatorioIdadeGrupoService;

    public RelatorioController(
            RelatorioAlunoService relatorioAlunoService,
            RelatorioClasseService relatorioClasseService,
            RelatorioChamadaService relatorioChamadaService,
            RelatorioTrimestreService relatorioTrimestreService,
            RelatorioAniversarianteService relatorioAniversarianteService,
            RelatorioRankingService relatorioRankingService,
            RelatorioIdadeGrupoService relatorioIdadeGrupoService) {
        this.relatorioAlunoService = relatorioAlunoService;
        this.relatorioClasseService = relatorioClasseService;
        this.relatorioChamadaService = relatorioChamadaService;
        this.relatorioTrimestreService = relatorioTrimestreService;
        this.relatorioAniversarianteService = relatorioAniversarianteService;
        this.relatorioRankingService = relatorioRankingService;
        this.relatorioIdadeGrupoService = relatorioIdadeGrupoService;
    }

    // ===== RELATÓRIOS DE ALUNOS =====
    
    @GetMapping("/aluno/{alunoId}")
    @Operation(summary = "Relatório individual de aluno", description = "Gera relatório completo de um aluno específico")
    public RelatorioAlunoDTO relatorioAluno(
            @PathVariable Integer alunoId,
            @RequestParam(required = false) Integer trimestreId,
            @RequestParam Integer classeId) {
        return relatorioAlunoService.gerarRelatorioAluno(alunoId, trimestreId, classeId);
    }

    @GetMapping("/alunos")
    @Operation(summary = "Relatórios de todos os alunos", description = "Lista relatórios de todos os alunos")
    public List<RelatorioAlunoDTO> listarRelatoriosAlunos(
            @RequestParam(required = false) Integer congregacaoId,
            @RequestParam(required = false) Integer trimestreId,
            @RequestParam Integer classeId) {
        return relatorioAlunoService.listarRelatoriosAlunos(congregacaoId, trimestreId, classeId);
    }

    // ===== RELATÓRIOS DE CLASSES =====
    
    @GetMapping("/classe/{classeId}")
    @Operation(summary = "Relatório de classe", description = "Gera relatório completo de uma classe específica")
    public RelatorioClasseDTO relatorioClasse(
            @PathVariable Integer classeId,
            @RequestParam(required = false) Integer trimestreId) {
        return relatorioClasseService.gerarRelatorioClasse(classeId, trimestreId);
    }

    @GetMapping("/classes")
    @Operation(summary = "Relatórios de todas as classes", description = "Lista relatórios de todas as classes")
    public List<RelatorioClasseDTO> listarRelatoriosClasses(
            @RequestParam(required = false) Integer congregacaoId,
            @RequestParam(required = false) Integer trimestreId) {
        return relatorioClasseService.listarRelatoriosClasses(congregacaoId, trimestreId);
    }

    // ===== RELATÓRIOS DE CHAMADAS =====
    
    @GetMapping("/chamada/{chamadaId}")
    @Operation(summary = "Relatório de chamada", description = "Gera relatório detalhado de uma chamada específica")
    public RelatorioChamadaDTO relatorioChamada(@PathVariable Integer chamadaId) {
        return relatorioChamadaService.gerarRelatorioChamada(chamadaId);
    }

    @GetMapping("/chamadas")
    @Operation(summary = "Relatórios de chamadas", description = "Lista relatórios de chamadas por filtros")
    public List<RelatorioChamadaDTO> listarRelatorioChamadas(
            @RequestParam(required = false) Integer classeId,
            @RequestParam(required = false) Integer trimestreId) {
        return relatorioChamadaService.listarRelatorioChamadas(classeId, trimestreId);
    }

    // ===== RELATÓRIOS DE TRIMESTRES =====
    
    @GetMapping("/trimestre/{trimestreId}")
    @Operation(summary = "Relatório de trimestre", description = "Gera relatório completo de um trimestre específico")
    public RelatorioTrimestreDTO relatorioTrimestre(@PathVariable Integer trimestreId) {
        return relatorioTrimestreService.gerarRelatorioTrimestre(trimestreId);
    }

    @GetMapping("/trimestres")
    @Operation(summary = "Relatórios de trimestres", description = "Lista relatórios de trimestres por filtros")
    public List<RelatorioTrimestreDTO> listarRelatoriosTrimestres(
            @RequestParam(required = false) Integer igrejaId,
            @RequestParam(required = false) Integer ano) {
        return relatorioTrimestreService.listarRelatoriosTrimestres(igrejaId, ano);
    }

    // ===== RELATÓRIOS DE ANIVERSARIANTES =====
    
    @GetMapping("/aniversariantes/mes/{mes}")
    @Operation(summary = "Aniversariantes do mês", description = "Lista todos os aniversariantes de um mês específico")
    public List<AniversarianteDTO> aniversariantesDoMes(
            @PathVariable Integer mes,
            @RequestParam(required = false) Integer congregacaoId) {
        return relatorioAniversarianteService.listarAniversariantesDoMes(mes, congregacaoId);
    }

    @GetMapping("/aniversariantes/dia")
    @Operation(summary = "Aniversariantes do dia", description = "Lista aniversariantes de uma data específica")
    public List<AniversarianteDTO> aniversariantesDoDia(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data,
            @RequestParam(required = false) Integer congregacaoId) {
        return relatorioAniversarianteService.listarAniversariantesDoDia(data, congregacaoId);
    }

    @GetMapping("/aniversariantes/proximos")
    @Operation(summary = "Próximos aniversariantes", description = "Lista os próximos aniversariantes nos próximos X dias")
    public List<AniversarianteDTO> proximosAniversariantes(
            @RequestParam(defaultValue = "30") Integer dias,
            @RequestParam(required = false) Integer congregacaoId) {
        return relatorioAniversarianteService.listarProximosAniversariantes(dias, congregacaoId);
    }

    // ===== RANKINGS =====
    
    @GetMapping("/ranking/alunos")
    @Operation(summary = "Ranking de alunos", description = "Gera ranking de alunos por pontuação")
    public List<RankingAlunoDTO> rankingAlunos(
            @RequestParam(required = false) Integer trimestreId,
            @RequestParam(required = false) Integer classeId,
            @RequestParam(required = false) Integer congregacaoId,
            @RequestParam(required = false) Integer limite) {
        return relatorioRankingService.gerarRankingAlunos(trimestreId, classeId, congregacaoId, limite);
    }

    @GetMapping("/ranking/alunos/geral")
    @Operation(summary = "Ranking geral de alunos", description = "Ranking unificado de dependentes e responsáveis")
    public List<RankingAlunoDTO> rankingAlunosGeral(
            @RequestParam(required = false) Integer trimestreId,
            @RequestParam(required = false) Integer limite) {
        return relatorioRankingService.rankingGeralUnificado(trimestreId, limite);
    }

    @GetMapping("/ranking/alunos/dependentes")
    @Operation(summary = "Ranking de dependentes", description = "Ranking de alunos dependentes")
    public List<RankingAlunoDTO> rankingDependentes(
            @RequestParam(required = false) Integer trimestreId,
            @RequestParam(required = false) Integer limite) {
        return relatorioRankingService.rankingDependentes(trimestreId, limite);
    }

    @GetMapping("/ranking/alunos/responsaveis")
    @Operation(summary = "Ranking de responsáveis", description = "Ranking de alunos responsáveis")
    public List<RankingAlunoDTO> rankingResponsaveis(
            @RequestParam(required = false) Integer trimestreId,
            @RequestParam(required = false) Integer limite) {
        return relatorioRankingService.rankingResponsaveis(trimestreId, limite);
    }

    @GetMapping("/ranking/classes")
    @Operation(summary = "Ranking de classes", description = "Gera ranking de classes por pontuação")
    public List<RankingClasseDTO> rankingClasses(
            @RequestParam(required = false) Integer trimestreId,
            @RequestParam(required = false) Integer congregacaoId,
            @RequestParam(required = false) Integer limite) {
        return relatorioRankingService.gerarRankingClasses(trimestreId, congregacaoId, limite);
    }

    @GetMapping("/ranking/alunos/frequencia")
    @Operation(summary = "Ranking de alunos por frequência", description = "Ranking baseado em frequência de presença")
    public List<RankingAlunoDTO> rankingAlunosPorFrequencia(
            @RequestParam(required = false) Integer trimestreId,
            @RequestParam(defaultValue = "10") Integer limite) {
        return relatorioRankingService.gerarRankingAlunosPorFrequencia(trimestreId, limite);
    }

    @GetMapping("/ranking/classes/frequencia")
    @Operation(summary = "Ranking de classes por frequência", description = "Ranking baseado em frequência média de presença")
    public List<RankingClasseDTO> rankingClassesPorFrequencia(
            @RequestParam(required = false) Integer trimestreId,
            @RequestParam(defaultValue = "10") Integer limite) {
        return relatorioRankingService.gerarRankingClassesPorFrequencia(trimestreId, limite);
    }

    @GetMapping("/ranking/idade-grupos/trimestral")
    @Operation(summary = "Ranking de alunos pela idade trimestral", description = "Ranking de alunos de acordo com a faixa etaria")
    public ResponseEntity<List<RankingIdadeGrupoDTO>> getRankingPorGrupoTrimestral(@RequestParam Integer trimestreId){
        return ResponseEntity.ok(relatorioIdadeGrupoService.gerarRankingPorIdadeGrupo(trimestreId));
    }

    @GetMapping("/ranking/idade-grupos/anual/{ano}")
    @Operation(summary = "Ranking de alunos pela idade anual", description = "Ranking de alunos de acordo com a faixa etaria")
    public List<RankingIdadeGrupoDTO> getRankingPorGrupoAnual(@PathVariable int ano) {
        return relatorioIdadeGrupoService.gerarRankingAnualPorIdadeGrupo(ano);
    }
}

