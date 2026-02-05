package ebd.api_ebd.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ebd.api_ebd.domain.entity.Chamada;
import ebd.api_ebd.service.ChamadaService;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@RequestMapping("/ebd/chamadas")
public class ChamadaController {
    // Controlador de Atendimento

    private final ChamadaService chamadaService;

    public ChamadaController(ChamadaService chamadaService){
        this.chamadaService = chamadaService;
    }

    @GetMapping("/dia")
    public List<Chamada> chamadaDoDia(@RequestParam UUID igrejaId) {
        return chamadaService.buscarChamadasDoDia(igrejaId);
    }

    @PutMapping("/{id}/abrir")
    public Chamada abrir(@PathVariable UUID id) {
        
        return chamadaService.abrirChamada(null, null, null);
    }

    @PutMapping("/{id}/fechar")
    public void fechar(@PathVariable UUID id) {
        
    chamadaService.fecharChamada(id);    
    }
    
}
