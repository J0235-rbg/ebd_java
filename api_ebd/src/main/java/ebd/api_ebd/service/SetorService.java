package ebd.api_ebd.service;

import java.util.List;

import org.springframework.stereotype.Service;

import ebd.api_ebd.domain.entity.Setor;
import ebd.api_ebd.repository.SetorRepository;
import jakarta.transaction.Transactional;

@Service
public class SetorService {

    private final SetorRepository setorRepository;

    public SetorService(SetorRepository setorRepository) {
        this.setorRepository = setorRepository;
    }

    @Transactional
    public Setor criar(Setor setor) {
        return setorRepository.save(setor);
    }

    @Transactional
    public Setor atualizar(Integer id, Setor dados) {
        Setor setor = setorRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Setor não encontrado"));
        
        setor.setNome(dados.getNome());
        setor.setIdIgreja(dados.getIdIgreja());
        
        return setorRepository.save(setor);
    }

    public List<Setor> listarPorIgreja(Integer igrejaId) {
        return setorRepository.findByIgrejaId(igrejaId);
    }

    public Setor buscarPorId(Integer id) {
        return setorRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Setor não encontrado"));
    }
}
