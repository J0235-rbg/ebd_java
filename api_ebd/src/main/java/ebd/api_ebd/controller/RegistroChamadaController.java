package ebd.api_ebd.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ebd.api_ebd.domain.entity.RegistroChamada;
import ebd.api_ebd.dto.response.RegistroChamadaResponse;
import ebd.api_ebd.service.RegistroChamadaService;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/ebd/registro-chamada")
@Tag(name = "Registro Chamada", description = "Gerenciamento de chamadas/presença da EBD")
public class RegistroChamadaController {

    private final RegistroChamadaService service;

    public RegistroChamadaController(RegistroChamadaService service) {
        this.service = service;
    }


    @GetMapping("/{chamadaId}")
    public List<RegistroChamadaResponse> listar(
        @PathVariable Integer chamadaId
    ) {
        List<RegistroChamada> response = service.listarPorChamada(chamadaId);
        return response.stream()
            .map(res -> new RegistroChamadaResponse(
                res.getChamada(),
                res.getaluno(),
                res.getBiblia(),
                res.getRevista(),
                res.getStatus()
            ))
            .toList();
        
    }

    
    @PostMapping("/{id}/atualizar")
    public RegistroChamada registrar(
        @RequestParam Integer chamadaId,
        @RequestParam Integer alunoId,
        @RequestParam Integer status,
        @RequestParam(required = false, defaultValue = "0") Integer biblia,
        @RequestParam(required = false, defaultValue = "0") Integer revista
    ) {
        return service.registrarOuAtualizar(
            chamadaId, alunoId, status, biblia, revista
        );
    }
}
