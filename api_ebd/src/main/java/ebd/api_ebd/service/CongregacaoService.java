package ebd.api_ebd.service;

import java.util.List;

import org.springframework.stereotype.Service;

import ebd.api_ebd.domain.entity.Congregacao;
import ebd.api_ebd.repository.CongregacaoRepository;
import jakarta.transaction.Transactional;

@Service
public class CongregacaoService {

    private final CongregacaoRepository congregacaoRepository;

    public CongregacaoService(CongregacaoRepository congregacaoRepository) {
        this.congregacaoRepository = congregacaoRepository;
    }

    @Transactional
    public Congregacao criar(Congregacao congregacao) {
        return congregacaoRepository.save(congregacao);
    }

    @Transactional
    public Congregacao atualizar(Integer id, Congregacao dados) {
        Congregacao congregacao = congregacaoRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Congregação não encontrada"));
        
        congregacao.setNome(dados.getNome());
        congregacao.setSetor(dados.getSetor());
        congregacao.setIgreja(dados.getIgreja());
        
        return congregacaoRepository.save(congregacao);
    }

    public List<Congregacao> listarPorIgreja(Integer igrejaId) {
        return congregacaoRepository.findByIgrejaId(igrejaId);
    }

    public List<Congregacao> listarPorSetor(Integer setorId) {
        return congregacaoRepository.findBySetorId(setorId);
    }

    public Congregacao buscarPorId(Integer id) {
        return congregacaoRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Congregação não encontrada"));
    }
}
