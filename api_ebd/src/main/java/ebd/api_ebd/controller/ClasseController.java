package ebd.api_ebd.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ebd.api_ebd.domain.entity.Classe;
import ebd.api_ebd.service.ClasseService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;




@RestController
@RequestMapping("/ebd/classes")
public class ClasseController {
    // Controlador de Turma

    private final ClasseService classeService;

    public ClasseController(ClasseService classeService){
        this.classeService = classeService;
    }

    @PostMapping("/criar")
    public Classe criar(@RequestBody Classe classe) {
        
        
        return classeService.criarClasse(classe);
    }

    @PutMapping("/{id}")
    public Classe atualizar(
        @PathVariable UUID id,
        @RequestBody Classe classe) {
        return classeService.atualizarClasse(id, classe);
    }

    @DeleteMapping("/{id}")
    public void desativar(@PathVariable UUID id) {
        classeService.desativarClasse(id);
    }

    @GetMapping("/listar")
    public List<Classe> listar(@RequestParam UUID igrejaId) {
        return classeService.listarAtivas(igrejaId);
    }

    @GetMapping("/professor/{pessoaId}")
    public List<Classe> classeDoProfessor(@RequestParam UUID igrejaId, @PathVariable UUID pessoaId) {
        return classeService.listarDoProfessor(igrejaId, pessoaId);
    }
    
    
    
}
