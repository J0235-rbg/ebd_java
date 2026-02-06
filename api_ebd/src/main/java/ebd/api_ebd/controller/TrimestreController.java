package ebd.api_ebd.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ebd.api_ebd.domain.entity.Trim;
import ebd.api_ebd.dto.CriarTrimestreDTO;
import ebd.api_ebd.service.TrimestreService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@RequestMapping("/ebd/trimestres")
public class TrimestreController {
    // Controlador de Período

    private final TrimestreService trimestreService;

    public TrimestreController(TrimestreService trimestreService) {
        this.trimestreService = trimestreService;
    }

    @PostMapping("/auto")
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
    public ResponseEntity<?> fechar(@PathVariable Integer id) {
        
        trimestreService.fecharTrim(id);
        return ResponseEntity.ok().build();
    }
}
