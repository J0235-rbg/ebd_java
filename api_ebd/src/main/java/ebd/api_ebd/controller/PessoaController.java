package ebd.api_ebd.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ebd.api_ebd.domain.entity.Pessoa;
import ebd.api_ebd.dto.request.AtualizarPessoaRequest;
import ebd.api_ebd.dto.request.CriarPessoaRequest;
import ebd.api_ebd.service.PessoaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/ebd/pessoas")
@Tag(name = "Pessoas", description = "Gerenciamento de pessoas, usuários e login")
public class PessoaController {

    private final PessoaService pessoaService;

    public PessoaController(PessoaService pessoaService) {
        this.pessoaService = pessoaService;
    }

    @PostMapping("/criar")
    @Operation(summary = "Criar pessoa", description = "Cria uma nova pessoa no sistema")
    public Pessoa criar(@RequestBody CriarPessoaRequest request) {
        Pessoa pessoa = new Pessoa();
        pessoa.setNome(request.getNome());
        pessoa.setTelefone(request.getTelefone());
        pessoa.setDt_nascimento(request.getDt_nascimento());
        pessoa.setEndereco(request.getEndereco());
        pessoa.setNumero(request.getNumero());
        pessoa.setBairro(request.getBairro());
        pessoa.setCargo(request.getCargo());
        pessoa.setCongregacao(request.getCongregacao());
        pessoa.setIgreja(request.getIgreja());
        pessoa.setLogin(request.getLogin());
        pessoa.setKeyApp(request.getKeyApp());
        pessoa.setAdmin(request.isAdmin());
        pessoa.setPodeRelatorio(request.isPodeRelatorio());
        pessoa.setAtivo(request.isAtivo());
        
        return pessoaService.criar(pessoa);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar pessoa", description = "Atualiza os dados de uma pessoa existente")
    public Pessoa atualizar(@PathVariable Integer id, @RequestBody AtualizarPessoaRequest request) {
        Pessoa pessoa = new Pessoa();
        pessoa.setNome(request.getNome());
        pessoa.setTelefone(request.getTelefone());
        pessoa.setDt_nascimento(request.getDt_nascimento());
        pessoa.setEndereco(request.getEndereco());
        pessoa.setNumero(request.getNumero());
        pessoa.setBairro(request.getBairro());
        pessoa.setCargo(request.getCargo());
        pessoa.setCongregacao(request.getCongregacao());
        pessoa.setIgreja(request.getIgreja());
        pessoa.setLogin(request.getLogin());
        pessoa.setAdmin(request.isAdmin());
        pessoa.setPodeRelatorio(request.isPodeRelatorio());
        pessoa.setAtivo(request.isAtivo());
        
        return pessoaService.atualizar(id, pessoa);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar pessoa por ID", description = "Retorna uma pessoa específica pelo ID")
    public Pessoa buscarPorId(@PathVariable Integer id) {
        return pessoaService.buscarPorId(id);
    }

    @GetMapping("/listar")
    @Operation(summary = "Listar todas as pessoas", description = "Lista todas as pessoas do sistema")
    public List<Pessoa> listarTodos() {
        return pessoaService.listarTodos();
    }

    @GetMapping("/ativos/listar")
    @Operation(summary = "Listar pessoas ativas", description = "Lista apenas as pessoas ativas do sistema")
    public List<Pessoa> listarAtivos() {
        return pessoaService.listarAtivos();
    }

    @GetMapping("/congregacao")
    @Operation(summary = "Listar pessoas por congregação", description = "Lista todas as pessoas de uma congregação específica")
    public List<Pessoa> listarPorCongregacao(@RequestParam Integer congregacaoId) {
        return pessoaService.listarPorCongregacao(congregacaoId);
    }

    @GetMapping("/cargo")
    @Operation(summary = "Listar pessoas por cargo", description = "Lista todas as pessoas que possuem um cargo específico")
    public List<Pessoa> listarPorCargo(@RequestParam Integer cargoId) {
        return pessoaService.listarPorCargo(cargoId);
    }

    @GetMapping("/igreja")
    @Operation(summary = "Listar pessoas por igreja", description = "Lista todas as pessoas de uma igreja específica")
    public List<Pessoa> listarPorIgreja(@RequestParam Integer igrejaId) {
        return pessoaService.listarPorIgreja(igrejaId);
    }

    @PostMapping("/login")
    @Operation(summary = "Autenticar usuário", description = "Realiza o login de um usuário usando login e keyApp")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        Pessoa pessoa =  pessoaService.autenticar(loginRequest.getLogin(), loginRequest.getKeyApp());

        if(pessoa != null){
            return ResponseEntity.ok("Bem vindo, "+ pessoa.getNome() +"! Login realizado");
        }else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login ou chave de acesso inválidos");
        }
    }

    @PutMapping("/{id}/cargo")
    @Operation(summary = "Atualizar cargo de uma pessoa", description = "Atualiza o cargo de uma pessoa específica")
    public Pessoa atualizarCargo(@PathVariable Integer id, @RequestBody CargoRequest cargoRequest) {
        return pessoaService.atualizarCargo(id, cargoRequest.getCargoId());
    }

    @GetMapping("/{id}/cargo")
    @Operation(summary = "Obter cargo de uma pessoa", description = "Obtém o ID do cargo de uma pessoa específica")
    public Integer obterCargo(@PathVariable Integer id) {
        return pessoaService.obterCargo(id);
    }

    @PutMapping("/{id}/ativar")
    @Operation(summary = "Ativar pessoa", description = "Ativa uma pessoa desativada")
    public Pessoa ativar(@PathVariable Integer id) {
        return pessoaService.ativar(id);
    }

    @PutMapping("/{id}/desativar")
    @Operation(summary = "Desativar pessoa", description = "Desativa uma pessoa")
    public Pessoa desativar(@PathVariable Integer id) {
        return pessoaService.desativar(id);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar pessoa", description = "Remove uma pessoa do sistema")
    public Pessoa deletar(@PathVariable Integer id) {
        return pessoaService.deletar(id);
    }

    // Classes auxiliares para requisições
    public static class LoginRequest {
        private String login;
        private String keyApp;

        public LoginRequest() {}

        public LoginRequest(String login, String keyApp) {
            this.login = login;
            this.keyApp = keyApp;
        }

        public String getLogin() {
            return login;
        }

        public void setLogin(String login) {
            this.login = login;
        }

        public String getKeyApp() {
            return keyApp;
        }

        public void setKeyApp(String keyApp) {
            this.keyApp = keyApp;
        }
    }

    public static class CargoRequest {
        private Integer cargoId;

        public CargoRequest() {}

        public CargoRequest(Integer cargoId) {
            this.cargoId = cargoId;
        }

        public Integer getCargoId() {
            return cargoId;
        }

        public void setCargoId(Integer cargoId) {
            this.cargoId = cargoId;
        }
    }
}

