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

import ebd.api_ebd.domain.entity.AlunoDependente;
import ebd.api_ebd.service.AlunoDependenteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/ebd/alunos/dependentes")
@Tag(name = "Alunos Dependentes", description = "Gerenciamento de alunos dependentes (menores de idade)")
public class AlunoDependenteController {

    private final AlunoDependenteService alunoDependenteService;

    public AlunoDependenteController(AlunoDependenteService alunoDependenteService) {
        this.alunoDependenteService = alunoDependenteService;
    }

    @PostMapping("/criar")
    @Operation(summary = "Criar aluno dependente", description = "Cria um novo aluno dependente no sistema")
    public ResponseEntity<AlunoDependente> criar(@RequestBody AlunoDependente aluno) {
        AlunoDependente novoAluno = alunoDependenteService.criar(aluno);
        return ResponseEntity.ok(novoAluno);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar aluno dependente por ID", description = "Retorna um aluno dependente específico pelo ID")
    public ResponseEntity<AlunoDependente> buscarPorId(@PathVariable Integer id) {
        AlunoDependente aluno = alunoDependenteService.buscarPorId(id);
        return ResponseEntity.ok(aluno);
    }

    @GetMapping("/{alunoId}/classe/{classeId}")
    @Operation(summary = "Buscar aluno dependente por ID e Classe", description = "Retorna um aluno dependente específico pelo ID, validando se pertence à classe")
    public ResponseEntity<AlunoDependente> buscarPorIdEClasse(
            @PathVariable Integer alunoId,
            @PathVariable Integer classeId) {
        AlunoDependente aluno = alunoDependenteService.buscarPorIdEClasse(alunoId, classeId);
        return ResponseEntity.ok(aluno);
    }

    @GetMapping("/classe/{classeId}")
    @Operation(summary = "Listar alunos dependentes por classe", description = "Lista todos os alunos dependentes de uma classe específica")
    public ResponseEntity<List<AlunoDependente>> listarPorClasse(@PathVariable Integer classeId) {
        List<AlunoDependente> alunos = alunoDependenteService.listarPorClasse(classeId);
        return ResponseEntity.ok(alunos);
    }

    @GetMapping("/congregacao/{congregacaoId}")
    @Operation(summary = "Listar alunos dependentes por congregação", description = "Lista todos os alunos dependentes de uma congregação específica")
    public ResponseEntity<List<AlunoDependente>> listarPorCongregacao(@PathVariable Integer congregacaoId) {
        List<AlunoDependente> alunos = alunoDependenteService.listarPorCongregacao(congregacaoId);
        return ResponseEntity.ok(alunos);
    }

    @GetMapping("/igreja/{igrejaId}")
    @Operation(summary = "Listar alunos dependentes por igreja", description = "Lista todos os alunos dependentes de uma igreja específica")
    public ResponseEntity<List<AlunoDependente>> listarPorIgreja(@PathVariable Integer igrejaId) {
        List<AlunoDependente> alunos = alunoDependenteService.listarPorIgreja(igrejaId);
        return ResponseEntity.ok(alunos);
    }

    @GetMapping("/listar")
    @Operation(summary = "Listar todos os alunos dependentes", description = "Lista todos os alunos dependentes cadastrados no sistema")
    public ResponseEntity<List<AlunoDependente>> listarTodos() {
        List<AlunoDependente> alunos = alunoDependenteService.listarTodos();
        return ResponseEntity.ok(alunos);
    }

    @GetMapping("/listar/ativos")
    @Operation(summary = "Listar alunos dependentes ativos", description = "Lista apenas os alunos dependentes ativos do sistema")
    public ResponseEntity<List<AlunoDependente>> listarAtivos() {
        List<AlunoDependente> alunos = alunoDependenteService.listarAtivos();
        return ResponseEntity.ok(alunos);
    }

    @GetMapping("/classe/{classeId}/ativos")
    @Operation(summary = "Listar alunos dependentes ativos por classe", description = "Lista apenas os alunos dependentes ATIVOS de uma classe específica, ordenados por nome")
    public ResponseEntity<List<AlunoDependente>> listarAtivosPorClasse(@PathVariable Integer classeId) {
        List<AlunoDependente> alunos = alunoDependenteService.listarAtivosPorClasse(classeId);
        return ResponseEntity.ok(alunos);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar aluno dependente", description = "Atualiza os dados de um aluno dependente existente")
    public ResponseEntity<AlunoDependente> atualizar(
            @PathVariable Integer id,
            @RequestBody AlunoDependente aluno) {
        AlunoDependente alunoAtualizado = alunoDependenteService.atualizar(id, aluno);
        return ResponseEntity.ok(alunoAtualizado);
    }

    @PutMapping("/{id}/ativar")
    @Operation(summary = "Ativar aluno dependente", description = "Ativa um aluno dependente desativado")
    public ResponseEntity<AlunoDependente> ativar(@PathVariable Integer id) {
        AlunoDependente aluno = alunoDependenteService.ativar(id);
        return ResponseEntity.ok(aluno);
    }

    @PutMapping("/{id}/desativar")
    @Operation(summary = "Desativar aluno dependente", description = "Desativa um aluno dependente")
    public ResponseEntity<AlunoDependente> desativar(@PathVariable Integer id) {
        AlunoDependente aluno = alunoDependenteService.desativar(id);
        return ResponseEntity.ok(aluno);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar aluno dependente", description = "Remove um aluno dependente do sistema")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        alunoDependenteService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
