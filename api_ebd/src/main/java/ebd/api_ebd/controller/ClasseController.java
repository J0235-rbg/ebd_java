package ebd.api_ebd.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ebd.api_ebd.domain.entity.Classe;
import ebd.api_ebd.service.ClasseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;




@RestController
@RequestMapping("/ebd/classes")
@Tag(name = "Classes", description = "Gerenciamento de classes/turmas da EBD")
public class ClasseController {
    // Controlador de Turma

    private final ClasseService classeService;

    public ClasseController(ClasseService classeService){
        this.classeService = classeService;
    }

    @PostMapping("/criar")
    @Operation(summary = "Criar classe", description = "Cria uma nova classe/turma")
    public Classe criar(@RequestBody Classe classe) {
        
        
        return classeService.criarClasse(classe);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar classe", description = "Atualiza os dados de uma classe existente")
    public Classe atualizar(
        @PathVariable Integer id,
        @RequestBody Classe classe) {
        return classeService.atualizarClasse(id, classe);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Desativar classe", description = "Desativa uma classe")
    public void desativar(@PathVariable Integer id) {
        classeService.desativarClasse(id);
    }

    @GetMapping("/listar")
    public List<Classe> listar(@RequestParam Integer igrejaId) {
        return classeService.listarAtivas(igrejaId);
    }

    @GetMapping("/professor/{pessoaId}")
    public List<Classe> classeDoProfessor(@RequestParam Integer igrejaId, @PathVariable Integer pessoaId) {
        return classeService.listarDoProfessor(igrejaId, pessoaId);
    }
    
    
    
}
