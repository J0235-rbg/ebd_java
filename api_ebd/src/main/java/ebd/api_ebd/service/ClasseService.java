package ebd.api_ebd.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import ebd.api_ebd.domain.entity.Classe;
import ebd.api_ebd.repository.ClasseRepository;
import jakarta.transaction.Transactional;

@Service
public class ClasseService {
    // Serviço de Turma

    private final ClasseRepository classeRepository;

    public ClasseService(ClasseRepository classeRepository){
        this.classeRepository = classeRepository;
    }

    @Transactional
    public Classe criarClasse(Classe classe){
        classe.setAtivo(true);
        return classeRepository.save(classe);
    }

    @Transactional
    public Classe atualizarClasse(UUID id, Classe dados){
        Classe classe = classeRepository.findById(id)
            .orElseThrow(() ->
            new IllegalArgumentException("Classe não encontrada")
        );

        classe.setNome(dados.getNome());
        classe.setFaixIdadeGrupo(dados.getFaixIdadeGrupo());
        classe.setProfessor1(dados.getProfessor1());
        classe.setProfessor2(dados.getProfessor2());
        classe.setProfessor03(dados.getProfessor03());

        return classeRepository.save(classe);
    }

    @Transactional
    public void desativarClasse(UUID id){
        Classe classe = classeRepository.findById(id)
            .orElseThrow();
        classe.setAtivo(false);
        classeRepository.save(classe);
    }

    public List<Classe> listarAtivas(UUID igrejaId) {
        return classeRepository.findByIgrejaIdAndAtivoTrue(igrejaId);
    }

    public List<Classe> listarPorCongregacaoSetor(
        UUID igrejaId,
        UUID congregacaoId,
        UUID setorId
    ) {
        return classeRepository
            .findByIgrejaIdAndCongregacaoIdSetorIdAndAtivoTrue(igrejaId, congregacaoId, setorId);
    }

    public List<Classe> listarDoProfessor(
        UUID igrejaId,
        UUID pessoaId
    ) {
        return classeRepository.findClasseDoProfessor(igrejaId, pessoaId);
    }
}
