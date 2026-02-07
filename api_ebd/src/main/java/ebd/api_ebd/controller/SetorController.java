package ebd.api_ebd.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ebd.api_ebd.domain.entity.Setor;
import ebd.api_ebd.service.SetorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/ebd/setores")
@Tag(name = "Setores", description = "Gerenciamento de setores da igreja")
public class SetorController {

    private final SetorService setorService;

    public SetorController(SetorService setorService) {
        this.setorService = setorService;
    }

    @PostMapping("/criar")
    @Operation(summary = "Criar setor", description = "Cria um novo setor")
    public Setor criar(@RequestBody Setor setor) {
        return setorService.criar(setor);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar setor", description = "Atualiza os dados de um setor existente")
    public Setor atualizar(@PathVariable Integer id, @RequestBody Setor setor) {
        return setorService.atualizar(id, setor);
    }

    @GetMapping("/listar")
    @Operation(summary = "Listar setores por igreja", description = "Lista todos os setores de uma igreja")
    public List<Setor> listarPorIgreja(@RequestParam Integer igrejaId) {
        return setorService.listarPorIgreja(igrejaId);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar setor por ID", description = "Retorna um setor específico pelo ID")
    public Setor buscarPorId(@PathVariable Integer id) {
        return setorService.buscarPorId(id);
    }
}
