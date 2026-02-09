package ebd.api_ebd.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ebd.api_ebd.domain.entity.Congregacao;
import ebd.api_ebd.service.CongregacaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/ebd/congregacoes")
@Tag(name = "Congregações", description = "Gerenciamento de congregações")
public class CongregacaoController {

    private final CongregacaoService congregacaoService;

    public CongregacaoController(CongregacaoService congregacaoService) {
        this.congregacaoService = congregacaoService;
    }

    @PostMapping("/criar")
    @Operation(summary = "Criar congregação", description = "Cria uma nova congregação")
    public Congregacao criar(@RequestBody Congregacao congregacao) {
        return congregacaoService.criar(congregacao);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar congregação", description = "Atualiza os dados de uma congregação existente")
    public Congregacao atualizar(@PathVariable Integer id, @RequestBody Congregacao congregacao) {
        return congregacaoService.atualizar(id, congregacao);
    }

    @GetMapping("/listar")
    @Operation(summary = "Listar congregações por igreja", description = "Lista todas as congregações de uma igreja")
    public List<Congregacao> listarPorIgreja(@RequestParam Integer igrejaId) {
        return congregacaoService.listarPorIgreja(igrejaId);
    }

    @GetMapping("/listar-por-setor")
    @Operation(summary = "Listar congregações por setor", description = "Lista todas as congregações de um setor")
    public List<Congregacao> listarPorSetor(@RequestParam Integer setorId) {
        return congregacaoService.listarPorSetor(setorId);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar congregação por ID", description = "Retorna uma congregação específica pelo ID")
    public Congregacao buscarPorId(@PathVariable Integer id) {
        return congregacaoService.buscarPorId(id);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Desativar classe", description = "Desativa uma classe")
    public Congregacao deletar(@PathVariable Integer id) {
        return congregacaoService.deletar(id);
    }
}
