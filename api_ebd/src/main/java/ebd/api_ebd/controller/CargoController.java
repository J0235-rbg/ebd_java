package ebd.api_ebd.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ebd.api_ebd.domain.entity.Cargo;
import ebd.api_ebd.service.CargoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/ebd/cargos")
@Tag(name = "Cargos", description = "Gerenciamento de cargos de pessoas")
public class CargoController {
    // Controlador de Cargos

    private final CargoService cargoService;

    public CargoController(CargoService cargoService) {
        this.cargoService = cargoService;
    }

    @PostMapping("/criar")
    @Operation(summary = "Criar cargo", description = "Cria um novo cargo no sistema")
    public ResponseEntity<Cargo> criar(@RequestBody Cargo cargo) {
        Cargo cargoCriado = cargoService.criar(cargo);
        return ResponseEntity.ok(cargoCriado);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar cargo", description = "Atualiza os dados de um cargo existente")
    public ResponseEntity<Cargo> atualizar(@PathVariable Integer id, @RequestBody Cargo cargo) {
        Cargo cargoAtualizado = cargoService.atualizar(id, cargo);
        return ResponseEntity.ok(cargoAtualizado);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar cargo por ID", description = "Retorna um cargo específico pelo ID")
    public ResponseEntity<Cargo> buscarPorId(@PathVariable Integer id) {
        Cargo cargo = cargoService.buscarPorId(id);
        return ResponseEntity.ok(cargo);
    }

    @GetMapping("/listar")
    @Operation(summary = "Listar todos os cargos", description = "Lista todos os cargos cadastrados no sistema")
    public ResponseEntity<List<Cargo>> listarTodos() {
        List<Cargo> cargos = cargoService.listarTodos();
        return ResponseEntity.ok(cargos);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar cargo", description = "Remove um cargo do sistema")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        cargoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
