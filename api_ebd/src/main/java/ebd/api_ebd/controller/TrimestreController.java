package ebd.api_ebd.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ebd.api_ebd.domain.entity.Trim;
import ebd.api_ebd.dto.CriarTrimestreDTO;
import ebd.api_ebd.service.TrimestreService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/ebd/trimestres")
@Tag(name = "Trimestres", description = "Gerenciamento de trimestres/períodos letivos")
public class TrimestreController {
    // Controlador de Período

    private final TrimestreService trimestreService;

    public TrimestreController(TrimestreService trimestreService) {
        this.trimestreService = trimestreService;
    }

    @PostMapping("/auto")
    @Operation(summary = "Criar trimestre automaticamente", description = "Cria um novo trimestre e gera automaticamente as chamadas de presença")
    public ResponseEntity<?> criar(@RequestBody CriarTrimestreDTO dto) {
        
        Trim trim = trimestreService.criarTrim(
            dto.igrejaId(), 
            dto.ano(),
            dto.trimestre()
            );
        
            trimestreService.gerarChamadas(trim.getId());
        
        return ResponseEntity.ok(trim);
    }
    
    @PutMapping("/{id}/fechar")
    @Operation(summary = "Fechar trimestre", description = "Fecha um trimestre, impedindo novos registros")
    public ResponseEntity<?> fechar(@PathVariable Integer id) {
        
        trimestreService.fecharTrim(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar trimestre por ID", description = "Retorna um trimestre específico pelo ID")
    public ResponseEntity<Trim> buscarPorId(@PathVariable Integer id) {
        Trim trim = trimestreService.buscarPorId(id);
        return ResponseEntity.ok(trim);
    }

    @GetMapping("/listar/igreja/{igrejaId}")
    @Operation(summary = "Listar trimestres por igreja", description = "Lista todos os trimestres de uma igreja, ordenados por data de início (mais recentes primeiro)")
    public ResponseEntity<List<Trim>> listarPorIgreja(@PathVariable Integer igrejaId) {
        List<Trim> trimestres = trimestreService.listarPorIgreja(igrejaId);
        return ResponseEntity.ok(trimestres);
    }

    @GetMapping("/listar/igreja/{igrejaId}/ativos")
    @Operation(summary = "Listar trimestres ativos por igreja", description = "Lista apenas os trimestres ativos (status: Aberto) de uma igreja específica")
    public ResponseEntity<List<Trim>> listarAtivosPorIgreja(@PathVariable Integer igrejaId) {
        List<Trim> trimestres = trimestreService.listarAtivosPorIgreja(igrejaId);
        return ResponseEntity.ok(trimestres);
    }
}
