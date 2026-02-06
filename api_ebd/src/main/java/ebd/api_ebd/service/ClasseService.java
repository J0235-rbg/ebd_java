package ebd.api_ebd.service;

import java.util.List;

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
    public Classe atualizarClasse(Integer id, Classe dados){
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
    public void desativarClasse(Integer id){
        Classe classe = classeRepository.findById(id)
            .orElseThrow();
        classe.setAtivo(false);
        classeRepository.save(classe);
    }

    public List<Classe> listarAtivas(Integer igrejaId) {
        return classeRepository.findByIgrejaIdAndAtivoTrue(igrejaId);
    }

    public List<Classe> listarPorCongregacaoSetor(
        Integer igrejaId,
        Integer congregacaoId,
        Integer setorId
    ) {
        return classeRepository
            .findByIgrejaIdAndCongregacaoIdAndSetorIdAndAtivoTrue(igrejaId, congregacaoId, setorId);
    }

    public List<Classe> listarDoProfessor(
        Integer igrejaId,
        Integer pessoaId
    ) {
        return classeRepository.findClasseDoProfessor(igrejaId, pessoaId);
    }
}
