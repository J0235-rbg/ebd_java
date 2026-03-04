package ebd.api_ebd.controller;

import java.math.BigDecimal;

import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ebd.api_ebd.domain.entity.ChamadaDadosAdicionais;
import ebd.api_ebd.dto.request.ChamadaDadosAdicionaisDTO;
import ebd.api_ebd.service.DadosAdicionaisService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/ebd/dados-adicionais")
@Tag(name = "Dados Adicionais", description = "Gerenciamento de dados adicionais de chamadas (visitantes, oferta, etc)")
public class DadosAdicionaisController {

    private final DadosAdicionaisService dadosAdicionaisService;

    public DadosAdicionaisController(DadosAdicionaisService dadosAdicionaisService) {
        this.dadosAdicionaisService = dadosAdicionaisService;
    }

    @GetMapping("/chamada/{chamadaId}")
    @Operation(summary = "Buscar dados adicionais por chamada", description = "Retorna todos os dados adicionais associados a uma chamada específica")
    public ResponseEntity<ChamadaDadosAdicionais> buscarPorChamadaId(@PathVariable Integer chamadaId) {
        ChamadaDadosAdicionais dados = dadosAdicionaisService.buscarPorChamadaId(chamadaId);
        return ResponseEntity.ok(dados);
    }

    @PutMapping("/chamada/{chamadaId}")
    @Operation(summary = "Atualizar visitantes e oferta", description = "Atualiza APENAS os campos de visitantes e oferta usando o ID da chamada como parâmetro. Os outros dados são consolidados automaticamente ao fechar o lote de registro de chamada.")
    public ResponseEntity<ChamadaDadosAdicionaisDTO> atualizarVisitantesEOferta(
           @PathVariable("chamadaId") Integer chamadaId,
        @org.springframework.web.bind.annotation.RequestBody ChamadaDadosAdicionaisDTO dto) { // Use apenas esta!
    
        dto.setChamadaId(chamadaId);
    
    // Log para conferir no console se agora chega
        System.out.println("DEBUG: Visitantes: " + dto.getVisitantes());
        System.out.println("DEBUG: Oferta: " + dto.getOferta());

        ChamadaDadosAdicionaisDTO dadosAtualizados = dadosAdicionaisService.registrarDadosAdicionais(dto);
        return ResponseEntity.ok(dadosAtualizados);
    }

    @PutMapping("/chamada/{chamadaId}/teste")
    public ResponseEntity<String> teste(@RequestBody String body) {
        System.out.println("JSON BRUTO: " + body);
        return ResponseEntity.ok("Recebido");
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar dados adicionais por ID", description = "Retorna os dados adicionais específicos pelo ID")
    public ResponseEntity<ChamadaDadosAdicionais> buscarPorId(@PathVariable Integer id) {
        ChamadaDadosAdicionais dados = dadosAdicionaisService.buscarPorId(id);
        return ResponseEntity.ok(dados);
    }
}
