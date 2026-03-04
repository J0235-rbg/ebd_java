package ebd.api_ebd.service;

import java.util.List;

import org.springframework.stereotype.Service;

import ebd.api_ebd.domain.entity.AlunoResponsavel;
import ebd.api_ebd.exception.NotFoundException;
import ebd.api_ebd.repository.AlunoResponsavelRepository;

@Service
public class AlunoResponsavelService {

    private final AlunoResponsavelRepository alunoResponsavelRepository;

    public AlunoResponsavelService(AlunoResponsavelRepository alunoResponsavelRepository) {
        this.alunoResponsavelRepository = alunoResponsavelRepository;
    }

    public AlunoResponsavel buscarPorId(Integer id) {
        return alunoResponsavelRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Aluno responsável não encontrado"));
    }

    public AlunoResponsavel buscarPorIdEClasse(Integer alunoId, Integer classeId) {
        AlunoResponsavel aluno = alunoResponsavelRepository.findByIdAndClasse(alunoId, classeId);
        if (aluno == null) {
            throw new NotFoundException("Aluno responsável não encontrado para a classe especificada");
        }
        return aluno;
    }

    public List<AlunoResponsavel> listarPorClasse(Integer classeId) {
        return alunoResponsavelRepository.findByClasse(classeId);
    }

    public List<AlunoResponsavel> listarPorCongregacao(Integer congregacaoId) {
        return alunoResponsavelRepository.findByCongregacao(congregacaoId);
    }

    public List<AlunoResponsavel> listarPorIgreja(Integer igrejaId) {
        return alunoResponsavelRepository.findByIgreja(igrejaId);
    }

    public List<AlunoResponsavel> listarAtivos() {
        return alunoResponsavelRepository.findAllAtivos();
    }

    public List<AlunoResponsavel> listarAtivosPorClasse(Integer classeId) {
        return alunoResponsavelRepository.findByClasseAndAtivos(classeId);
    }

    public List<AlunoResponsavel> listarTodos() {
        return alunoResponsavelRepository.findAll();
    }

    public AlunoResponsavel criar(AlunoResponsavel aluno) {
        if (aluno.getStatus() == null) {
            aluno.setStatus(1); // Padrão: ativo
        }
        return alunoResponsavelRepository.save(aluno);
    }

    public AlunoResponsavel atualizar(Integer id, AlunoResponsavel aluno) {
        AlunoResponsavel alunoExistente = buscarPorId(id);
        alunoExistente.setNome(aluno.getNome());
        alunoExistente.setIgreja(aluno.getIgreja());
        alunoExistente.setSetor(aluno.getSetor());
        alunoExistente.setCongregacao(aluno.getCongregacao());
        alunoExistente.setClasse(aluno.getClasse());
        alunoExistente.setSexo(aluno.getSexo());
        alunoExistente.setEmail(aluno.getEmail());
        alunoExistente.setObservacao(aluno.getObservacao());
        alunoExistente.setStatus(aluno.getStatus());
        alunoExistente.setDt_nascimento(aluno.getDt_nascimento());
        return alunoResponsavelRepository.save(alunoExistente);
    }

    public AlunoResponsavel ativar(Integer id) {
        AlunoResponsavel aluno = buscarPorId(id);
        aluno.setStatus(1);
        return alunoResponsavelRepository.save(aluno);
    }

    public AlunoResponsavel desativar(Integer id) {
        AlunoResponsavel aluno = buscarPorId(id);
        aluno.setStatus(0);
        return alunoResponsavelRepository.save(aluno);
    }

    public void deletar(Integer id) {
        if (!alunoResponsavelRepository.existsById(id)) {
            throw new NotFoundException("Aluno responsável não encontrado");
        }
        alunoResponsavelRepository.deleteById(id);
    }
}
