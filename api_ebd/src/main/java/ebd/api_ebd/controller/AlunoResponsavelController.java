package ebd.api_ebd.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ebd.api_ebd.domain.entity.AlunoResponsavel;
import ebd.api_ebd.service.AlunoResponsavelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/ebd/alunos/responsaveis")
@Tag(name = "Alunos Responsáveis", description = "Gerenciamento de alunos responsáveis (adultos)")
public class AlunoResponsavelController {

    private final AlunoResponsavelService alunoResponsavelService;

    public AlunoResponsavelController(AlunoResponsavelService alunoResponsavelService) {
        this.alunoResponsavelService = alunoResponsavelService;
    }

    @PostMapping("/criar")
    @Operation(summary = "Criar aluno responsável", description = "Cria um novo aluno responsável no sistema")
    public ResponseEntity<AlunoResponsavel> criar(@RequestBody AlunoResponsavel aluno) {
        AlunoResponsavel novoAluno = alunoResponsavelService.criar(aluno);
        return ResponseEntity.ok(novoAluno);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar aluno responsável por ID", description = "Retorna um aluno responsável específico pelo ID")
    public ResponseEntity<AlunoResponsavel> buscarPorId(@PathVariable Integer id) {
        AlunoResponsavel aluno = alunoResponsavelService.buscarPorId(id);
        return ResponseEntity.ok(aluno);
    }

    @GetMapping("/{alunoId}/classe/{classeId}")
    @Operation(summary = "Buscar aluno responsável por ID e Classe", description = "Retorna um aluno responsável específico pelo ID, validando se pertence à classe")
    public ResponseEntity<AlunoResponsavel> buscarPorIdEClasse(
            @PathVariable Integer alunoId,
            @PathVariable Integer classeId) {
        AlunoResponsavel aluno = alunoResponsavelService.buscarPorIdEClasse(alunoId, classeId);
        return ResponseEntity.ok(aluno);
    }

    @GetMapping("/classe/{classeId}")
    @Operation(summary = "Listar alunos responsáveis por classe", description = "Lista todos os alunos responsáveis de uma classe específica")
    public ResponseEntity<List<AlunoResponsavel>> listarPorClasse(@PathVariable Integer classeId) {
        List<AlunoResponsavel> alunos = alunoResponsavelService.listarPorClasse(classeId);
        return ResponseEntity.ok(alunos);
    }

    @GetMapping("/congregacao/{congregacaoId}")
    @Operation(summary = "Listar alunos responsáveis por congregação", description = "Lista todos os alunos responsáveis de uma congregação específica")
    public ResponseEntity<List<AlunoResponsavel>> listarPorCongregacao(@PathVariable Integer congregacaoId) {
        List<AlunoResponsavel> alunos = alunoResponsavelService.listarPorCongregacao(congregacaoId);
        return ResponseEntity.ok(alunos);
    }

    @GetMapping("/igreja/{igrejaId}")
    @Operation(summary = "Listar alunos responsáveis por igreja", description = "Lista todos os alunos responsáveis de uma igreja específica")
    public ResponseEntity<List<AlunoResponsavel>> listarPorIgreja(@PathVariable Integer igrejaId) {
        List<AlunoResponsavel> alunos = alunoResponsavelService.listarPorIgreja(igrejaId);
        return ResponseEntity.ok(alunos);
    }

    @GetMapping("/listar")
    @Operation(summary = "Listar todos os alunos responsáveis", description = "Lista todos os alunos responsáveis cadastrados no sistema")
    public ResponseEntity<List<AlunoResponsavel>> listarTodos() {
        List<AlunoResponsavel> alunos = alunoResponsavelService.listarTodos();
        return ResponseEntity.ok(alunos);
    }

    @GetMapping("/listar/ativos")
    @Operation(summary = "Listar alunos responsáveis ativos", description = "Lista apenas os alunos responsáveis ativos do sistema")
    public ResponseEntity<List<AlunoResponsavel>> listarAtivos() {
        List<AlunoResponsavel> alunos = alunoResponsavelService.listarAtivos();
        return ResponseEntity.ok(alunos);
    }

    @GetMapping("/classe/{classeId}/ativos")
    @Operation(summary = "Listar alunos responsáveis ativos por classe", description = "Lista apenas os alunos responsáveis ATIVOS de uma classe específica, ordenados por nome")
    public ResponseEntity<List<AlunoResponsavel>> listarAtivosPorClasse(@PathVariable Integer classeId) {
        List<AlunoResponsavel> alunos = alunoResponsavelService.listarAtivosPorClasse(classeId);
        return ResponseEntity.ok(alunos);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar aluno responsável", description = "Atualiza os dados de um aluno responsável existente")
    public ResponseEntity<AlunoResponsavel> atualizar(
            @PathVariable Integer id,
            @RequestBody AlunoResponsavel aluno) {
        AlunoResponsavel alunoAtualizado = alunoResponsavelService.atualizar(id, aluno);
        return ResponseEntity.ok(alunoAtualizado);
    }

    @PutMapping("/{id}/ativar")
    @Operation(summary = "Ativar aluno responsável", description = "Ativa um aluno responsável desativado")
    public ResponseEntity<AlunoResponsavel> ativar(@PathVariable Integer id) {
        AlunoResponsavel aluno = alunoResponsavelService.ativar(id);
        return ResponseEntity.ok(aluno);
    }

    @PutMapping("/{id}/desativar")
    @Operation(summary = "Desativar aluno responsável", description = "Desativa um aluno responsável")
    public ResponseEntity<AlunoResponsavel> desativar(@PathVariable Integer id) {
        AlunoResponsavel aluno = alunoResponsavelService.desativar(id);
        return ResponseEntity.ok(aluno);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar aluno responsável", description = "Remove um aluno responsável do sistema")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        alunoResponsavelService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
