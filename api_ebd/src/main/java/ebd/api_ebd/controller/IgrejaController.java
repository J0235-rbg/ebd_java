package ebd.api_ebd.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ebd.api_ebd.domain.entity.Igreja;
import ebd.api_ebd.service.IgrejaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/ebd/igrejas")
@Tag(name = "Igrejas", description = "Gerenciamento de igrejas e validação de chave de acesso")
public class IgrejaController {

    private final IgrejaService igrejaService;

    public IgrejaController(IgrejaService igrejaService) {
        this.igrejaService = igrejaService;
    }

    @GetMapping("/{id}/chave-login")
    @Operation(summary = "Buscar chave de login da igreja", description = "Retorna a chave de login/validação de uma igreja específica")
    public ResponseEntity<String> buscarChaveLogin(@PathVariable Integer id) {
        String chaveLogin = igrejaService.buscarChaveLogin(id);
        return ResponseEntity.ok(chaveLogin);
    }

    @GetMapping("/validar-chave")
    @Operation(summary = "Validar chave de login", description = "Valida se uma chave de login existe no sistema")
    public ResponseEntity<Boolean> validarChaveLogin(@RequestParam String chaveLogin) {
        boolean valida = igrejaService.validarChaveLogin(chaveLogin);
        return ResponseEntity.ok(valida);
    }

    @GetMapping("/buscar-por-chave")
    @Operation(summary = "Buscar igreja por chave", description = "Busca uma igreja pela chave de login")
    public ResponseEntity<Igreja> buscarPorChaveLogin(@RequestParam String chaveLogin) {
        Igreja igreja = igrejaService.buscarPorChaveLogin(chaveLogin);
        return ResponseEntity.ok(igreja);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar igreja por ID", description = "Retorna uma igreja específica pelo ID")
    public ResponseEntity<Igreja> buscarPorId(@PathVariable Integer id) {
        Igreja igreja = igrejaService.buscarPorId(id);
        return ResponseEntity.ok(igreja);
    }

    @GetMapping("/listar")
    @Operation(summary = "Listar todas as igrejas", description = "Lista todas as igrejas cadastradas no sistema")
    public ResponseEntity<List<Igreja>> listarTodas() {
        List<Igreja> igrejas = igrejaService.listarTodas();
        return ResponseEntity.ok(igrejas);
    }

    @GetMapping("/listar/ativas")
    @Operation(summary = "Listar igrejas ativas", description = "Lista apenas as igrejas ativas do sistema")
    public ResponseEntity<List<Igreja>> listarAtivas() {
        List<Igreja> igrejas = igrejaService.listarAtivas();
        return ResponseEntity.ok(igrejas);
    }

    @PostMapping("/criar")
    @Operation(summary = "Criar igreja", description = "Cria uma nova igreja no sistema")
    public ResponseEntity<Igreja> criar(@RequestBody Igreja igreja) {
        Igreja novaIgreja = igrejaService.criar(igreja);
        return ResponseEntity.ok(novaIgreja);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar igreja", description = "Atualiza os dados de uma igreja existente")
    public ResponseEntity<Igreja> atualizar(@PathVariable Integer id, @RequestBody Igreja igreja) {
        Igreja igrejaAtualizada = igrejaService.atualizar(id, igreja);
        return ResponseEntity.ok(igrejaAtualizada);
    }

    @PutMapping("/{id}/ativar")
    @Operation(summary = "Ativar igreja", description = "Ativa uma igreja desativada")
    public ResponseEntity<Igreja> ativar(@PathVariable Integer id) {
        Igreja igreja = igrejaService.ativar(id);
        return ResponseEntity.ok(igreja);
    }

    @PutMapping("/{id}/desativar")
    @Operation(summary = "Desativar igreja", description = "Desativa uma igreja")
    public ResponseEntity<Igreja> desativar(@PathVariable Integer id) {
        Igreja igreja = igrejaService.desativar(id);
        return ResponseEntity.ok(igreja);
    }
}
