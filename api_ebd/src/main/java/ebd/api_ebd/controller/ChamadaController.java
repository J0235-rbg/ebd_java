package ebd.api_ebd.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ebd.api_ebd.domain.entity.Chamada;
import ebd.api_ebd.dto.response.ChamadaResponse;
import ebd.api_ebd.service.ChamadaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @GetMapping("/dia")
    @Operation(summary = "Buscar chamadas do dia", description = "Retorna todas as chamadas abertas do dia atual para uma igreja")
    public List<Chamada> chamadaDoDia(@RequestParam Integer igrejaId, Integer trimId) {
        return chamadaService.buscarChamadasDoDia(igrejaId, trimId);
    }

    @PutMapping("/{id}/abrir")
    @Operation(summary = "Abrir chamada", description = "Abre uma chamada para registro de presença")
    public Chamada abrir(@PathVariable Integer id) {
        
        return chamadaService.abrirChamada(null, null, null);
    }

    @PutMapping("/{id}/fechar")
    @Operation(summary = "Fechar chamada", description = "Fecha uma chamada e consolida os dados")
    public void fechar(@PathVariable Integer id) {
        
    chamadaService.fecharChamada(id);    
    }
    
}
