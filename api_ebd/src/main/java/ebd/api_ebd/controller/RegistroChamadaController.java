package ebd.api_ebd.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import ebd.api_ebd.domain.entity.RegistroChamada;
import ebd.api_ebd.dto.request.RegistraPresencaRequest;
import ebd.api_ebd.dto.request.RegistroChamadaDTO;
import ebd.api_ebd.dto.response.RegistroChamadaResponse;
import ebd.api_ebd.service.RegistroChamadaService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.Operation;
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

    
    @PostMapping("/atualizar")
    public ResponseEntity<String> registrar(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
        content = @io.swagger.v3.oas.annotations.media.Content(
            mediaType = "application/json",
            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = RegistraPresencaRequest.class)
        )
    )
    @org.springframework.web.bind.annotation.RequestBody RegistraPresencaRequest request
    ) {
        System.out.println("Dados brutos: "+ request.getRegistros());
        try {   
            System.out.println("Id convertido: " + request.getChamadaId());
            service.registrarLote(request.getChamadaId(), request.getRegistros());
            return ResponseEntity.ok("Alunos registrados com sucesso");
        } catch(IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Erro ao processar chamada " + e.getMessage());
        }
    }

    @PostMapping(value = "/teste", consumes = "application/json")
public ResponseEntity<String> testar(@RequestBody Map<String, Object> payload) {
    System.out.println("Payload recebido: " + payload);
    
    if (payload == null) {
        return ResponseEntity.badRequest().body("O corpo ainda está vindo nulo!");
    }
    
    return ResponseEntity.ok("Dados chegaram: " + payload.toString());
}
    
}
