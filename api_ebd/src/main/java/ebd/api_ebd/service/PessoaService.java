package ebd.api_ebd.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import ebd.api_ebd.domain.entity.Pessoa;
import ebd.api_ebd.exception.NotFoundException;
import ebd.api_ebd.repository.PessoaRepository;
import jakarta.transaction.Transactional;

@Service
public class PessoaService {

    private final PessoaRepository pessoaRepository;

    public PessoaService(PessoaRepository pessoaRepository) {
        this.pessoaRepository = pessoaRepository;
    }

    @Transactional
    public Pessoa criar(Pessoa pessoa) {
        // Gera uma keyApp única para o usuário
        if (pessoa.getKeyApp() == null || pessoa.getKeyApp().isEmpty()) {
            pessoa.setKeyApp(UUID.randomUUID().toString());
        }
        
        // Define como ativo por padrão
        pessoa.setAtivo(true);
        
        return pessoaRepository.save(pessoa);
    }

    @Transactional
    public Pessoa atualizar(Integer id, Pessoa dados) {
        Pessoa pessoa = pessoaRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Pessoa não encontrada"));
        
        pessoa.setNome(dados.getNome());
        pessoa.setTelefone(dados.getTelefone());
        pessoa.setDt_nascimento(dados.getDt_nascimento());
        pessoa.setEndereco(dados.getEndereco());
        pessoa.setNumero(dados.getNumero());
        pessoa.setBairro(dados.getBairro());
        pessoa.setCargo(dados.getCargo());
        pessoa.setCongregacao(dados.getCongregacao());
        pessoa.setIgreja(dados.getIgreja());
        pessoa.setAtivo(dados.isAtivo());
        pessoa.setPodeRelatorio(dados.isPodeRelatorio());
        pessoa.setAdmin(dados.isAdmin());
        
        // Atualiza login se fornecido
        if (dados.getLogin() != null && !dados.getLogin().isEmpty()) {
            pessoa.setLogin(dados.getLogin());
        }
        
        return pessoaRepository.save(pessoa);
    }

    public Pessoa buscarPorId(Integer id) {
        return pessoaRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Pessoa não encontrada"));
    }

    public Pessoa buscarPorLogin(String login) {
        return pessoaRepository.findByLogin(login)
            .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
    }

    public List<Pessoa> listarTodos() {
        return pessoaRepository.findAll();
    }

    public List<Pessoa> listarAtivos() {
        return pessoaRepository.findAllAtivos();
    }

    public List<Pessoa> listarPorCongregacao(Integer congregacaoId) {
        return pessoaRepository.findByCongregacao(congregacaoId);
    }

    public List<Pessoa> listarPorCargo(Integer cargoId) {
        return pessoaRepository.findByCargo(cargoId);
    }

    public List<Pessoa> listarPorIgreja(Integer igrejaId) {
        return pessoaRepository.findByIgreja(igrejaId);
    }

    @Transactional
    public Pessoa autenticar(String login, String keyApp) {
        Pessoa pessoa = buscarPorLogin(login);
        
        if (!pessoa.getKeyApp().equals(keyApp)) {
            throw new NotFoundException("Credenciais inválidas");
        }
        
        if (!pessoa.isAtivo()) {
            throw new NotFoundException("Usuário inativo");
        }
        
        return pessoa;
    }

    @Transactional
    public Pessoa atualizarCargo(Integer pessoaId, Integer cargoId) {
        Pessoa pessoa = pessoaRepository.findById(pessoaId)
            .orElseThrow(() -> new NotFoundException("Pessoa não encontrada"));
        
        pessoa.setCargo(cargoId);
        return pessoaRepository.save(pessoa);
    }

    public Integer obterCargo(Integer pessoaId) {
        Pessoa pessoa = buscarPorId(pessoaId);
        return pessoa.getCargo();
    }

    @Transactional
    public Pessoa deletar(Integer id) {
        Pessoa pessoa = pessoaRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Pessoa não encontrada"));
        
        pessoaRepository.delete(pessoa);
        return pessoa;
    }

    @Transactional
    public Pessoa desativar(Integer id) {
        Pessoa pessoa = pessoaRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Pessoa não encontrada"));
        
        pessoa.setAtivo(false);
        return pessoaRepository.save(pessoa);
    }

    @Transactional
    public Pessoa ativar(Integer id) {
        Pessoa pessoa = pessoaRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Pessoa não encontrada"));
        
        pessoa.setAtivo(true);
        return pessoaRepository.save(pessoa);
    }
}

