package ebd.api_ebd.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ebd.api_ebd.domain.entity.Chamada;
import ebd.api_ebd.dto.request.FechaChamadaRequest;
import ebd.api_ebd.service.ChamadaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@RequestMapping("/ebd/chamadas")
@Tag(name = "Chamadas", description = "Gerenciamento de chamadas/presença da EBD")
public class ChamadaController {
    // Controlador de Atendimento

    private final ChamadaService chamadaService;

    public ChamadaController(ChamadaService chamadaService){
        this.chamadaService = chamadaService;
    }

    @GetMapping("/dia-ativos")
    @Operation(summary = "Buscar chamadas do dia", description = "Retorna todas as chamadas abertas do dia atual para uma igreja")
    public List<Chamada> chamadaDoDiaAtivos(@RequestParam Integer igrejaId, Integer trimId) {
        return chamadaService.buscarChamadasDoDiaAtivos(igrejaId, trimId);
    }

    @GetMapping("/dia")
    @Operation(summary = "Buscar chamadas do dia", description = "Retorna todas as chamadas abertas do dia atual para uma igreja")
    public List<Chamada> chamadaDoDia(@RequestParam Integer igrejaId, Integer trimId) {
        return chamadaService.buscarChamadasDoDia(igrejaId, trimId);
    }

    @GetMapping("/dia/classe")
    @Operation(summary = "Buscar chamadas do dia", description = "Retorna todas as chamadas abertas do dia atual para uma igreja")
    public List<Chamada> chamadaDoDiaClasses(@RequestParam Integer igrejaId, Integer trimId, Integer classeId) {
        return chamadaService.buscarChamadasDoDiaClasses(igrejaId, trimId, classeId);
    }

    @PutMapping("/{id}/abrir")
    @Operation(summary = "Abrir chamada", description = "Abre uma chamada para registro de presença")
    public Chamada abrir(@PathVariable Integer id) {
        
        return chamadaService.abrirChamada(null, null, null);
    }

    @PutMapping("/{id}/fechar")
    @Operation(summary = "Fechar chamada", description = "Fecha uma chamada e consolida os dados")
    public ResponseEntity<String> fechar(
        @PathVariable Integer id,
        @RequestBody FechaChamadaRequest request
    ) {
        try{
            chamadaService.fecharChamada(id, request);
            return ResponseEntity.ok("Chamada fechada com sucesso");
        } catch (Exception e){
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PutMapping("/fechar-dia")
    @Operation(summary = "Fechar todas as chamadas do dia", description = "Fecha todas as chamadas de um domingo específico")
    public ResponseEntity<String> fecharChamadasDoDia(
        @RequestParam LocalDate dataDomingo,
        @RequestParam Integer igrejaId,
        @RequestParam Integer trimId
    ) {
        try {
            int totalFechadas = chamadaService.fecharChamadasDoDia(dataDomingo, igrejaId, trimId);
            return ResponseEntity.ok(totalFechadas + " chamada(s) fechada(s) com sucesso");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PutMapping("/abrir-dia")
    @Operation(summary = "Abrir todas as chamadas do dia", description = "Abre todas as chamadas de um domingo específico")
    public ResponseEntity<String> abrirChamadasDoDia(
        @RequestParam LocalDate dataDomingo,
        @RequestParam Integer igrejaId,
        @RequestParam Integer trimId
    ) {
        try {
            int totalAbertas = chamadaService.abrirChamadasDoDia(dataDomingo, igrejaId, trimId);
            return ResponseEntity.ok(totalAbertas + " chamada(s) aberta(s) com sucesso");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
    
}
