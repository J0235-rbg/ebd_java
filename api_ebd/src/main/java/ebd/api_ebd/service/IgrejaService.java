package ebd.api_ebd.service;

import java.util.List;

import org.springframework.stereotype.Service;

import ebd.api_ebd.domain.entity.Igreja;
import ebd.api_ebd.exception.NotFoundException;
import ebd.api_ebd.repository.IgrejaRepository;

@Service
public class IgrejaService {

    private final IgrejaRepository igrejaRepository;

    public IgrejaService(IgrejaRepository igrejaRepository) {
        this.igrejaRepository = igrejaRepository;
    }

    public Igreja buscarPorId(Integer id) {
        return igrejaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Igreja não encontrada"));
    }

    public String buscarChaveLogin(Integer igrejaId) {
        Igreja igreja = buscarPorId(igrejaId);
        return igreja.getChaveLogin();
    }

    public boolean validarChaveLogin(String chaveLogin) {
        return igrejaRepository.findByChaveLogin(chaveLogin).isPresent();
    }

    public Igreja buscarPorChaveLogin(String chaveLogin) {
        return igrejaRepository.findByChaveLogin(chaveLogin)
                .orElseThrow(() -> new NotFoundException("Igreja não encontrada para a chave fornecida"));
    }

    public List<Igreja> listarTodas() {
        return igrejaRepository.findAll();
    }

    public List<Igreja> listarAtivas() {
        return igrejaRepository.findByAtiva(true);
    }

    public Igreja criar(Igreja igreja) {
        return igrejaRepository.save(igreja);
    }

    public Igreja atualizar(Integer id, Igreja igreja) {
        Igreja igrejaExistente = buscarPorId(id);
        igrejaExistente.setNome(igreja.getNome());
        igrejaExistente.setEndereco(igreja.getEndereco());
        igrejaExistente.setChaveLogin(igreja.getChaveLogin());
        igrejaExistente.setAtiva(igreja.isAtiva());
        return igrejaRepository.save(igrejaExistente);
    }

    public Igreja ativar(Integer id) {
        Igreja igreja = buscarPorId(id);
        igreja.setAtiva(true);
        return igrejaRepository.save(igreja);
    }

    public Igreja desativar(Integer id) {
        Igreja igreja = buscarPorId(id);
        igreja.setAtiva(false);
        return igrejaRepository.save(igreja);
    }
}
