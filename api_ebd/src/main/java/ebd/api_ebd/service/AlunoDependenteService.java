package ebd.api_ebd.service;

import java.util.List;

import org.springframework.stereotype.Service;

import ebd.api_ebd.domain.entity.AlunoDependente;
import ebd.api_ebd.exception.NotFoundException;
import ebd.api_ebd.repository.AlunoDependenteRepository;

@Service
public class AlunoDependenteService {

    private final AlunoDependenteRepository alunoDependenteRepository;

    public AlunoDependenteService(AlunoDependenteRepository alunoDependenteRepository) {
        this.alunoDependenteRepository = alunoDependenteRepository;
    }

    public AlunoDependente buscarPorId(Integer id) {
        return alunoDependenteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Aluno dependente não encontrado"));
    }

    public AlunoDependente buscarPorIdEClasse(Integer alunoId, Integer classeId) {
        AlunoDependente aluno = alunoDependenteRepository.findByIdAndClasse(alunoId, classeId);
        if (aluno == null) {
            throw new NotFoundException("Aluno dependente não encontrado para a classe especificada");
        }
        return aluno;
    }

    public List<AlunoDependente> listarPorClasse(Integer classeId) {
        return alunoDependenteRepository.findByClasse(classeId);
    }

    public List<AlunoDependente> listarPorCongregacao(Integer congregacaoId) {
        return alunoDependenteRepository.findByCongregacao(congregacaoId);
    }

    public List<AlunoDependente> listarPorIgreja(Integer igrejaId) {
        return alunoDependenteRepository.findByIgreja(igrejaId);
    }

    public List<AlunoDependente> listarAtivos() {
        return alunoDependenteRepository.findAllAtivos();
    }

    public List<AlunoDependente> listarAtivosPorClasse(Integer classeId) {
        return alunoDependenteRepository.findByClasseAndAtivos(classeId);
    }

    public List<AlunoDependente> listarTodos() {
        return alunoDependenteRepository.findAll();
    }

    public AlunoDependente criar(AlunoDependente aluno) {
        if (aluno.getStatus() == null) {
            aluno.setStatus(1); // Padrão: ativo
        }
        return alunoDependenteRepository.save(aluno);
    }

    public AlunoDependente atualizar(Integer id, AlunoDependente aluno) {
        AlunoDependente alunoExistente = buscarPorId(id);
        alunoExistente.setNome(aluno.getNome());
        alunoExistente.setAlunoResponsavel(aluno.getAlunoResponsavel());
        alunoExistente.setIgreja(aluno.getIgreja());
        alunoExistente.setSetor(aluno.getSetor());
        alunoExistente.setCongregacao(aluno.getCongregacao());
        alunoExistente.setClasse(aluno.getClasse());
        alunoExistente.setStatus(aluno.getStatus());
        alunoExistente.setDt_nascimento(aluno.getDt_nascimento());
        return alunoDependenteRepository.save(alunoExistente);
    }

    public AlunoDependente ativar(Integer id) {
        AlunoDependente aluno = buscarPorId(id);
        aluno.setStatus(1);
        return alunoDependenteRepository.save(aluno);
    }

    public AlunoDependente desativar(Integer id) {
        AlunoDependente aluno = buscarPorId(id);
        aluno.setStatus(0);
        return alunoDependenteRepository.save(aluno);
    }

    public void deletar(Integer id) {
        if (!alunoDependenteRepository.existsById(id)) {
            throw new NotFoundException("Aluno dependente não encontrado");
        }
        alunoDependenteRepository.deleteById(id);
    }
}
