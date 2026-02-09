package ebd.api_ebd.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ebd.api_ebd.domain.entity.AlunoDependente;

public interface AlunoDependenteRepository extends JpaRepository<AlunoDependente, Integer> {
    
    @Query("SELECT a FROM AlunoDependente a WHERE a.classe = :classeId")
    List<AlunoDependente> findByClasse(@Param("classeId") Integer classeId);
    
    @Query("SELECT a FROM AlunoDependente a WHERE a.congregacao = :congregacaoId")
    List<AlunoDependente> findByCongregacao(@Param("congregacaoId") Integer congregacaoId);
    
    @Query("SELECT a FROM AlunoDependente a WHERE a.igreja = :igrejaId")
    List<AlunoDependente> findByIgreja(@Param("igrejaId") Integer igrejaId);
    
    @Query("SELECT a FROM AlunoDependente a WHERE a.status = 1")
    List<AlunoDependente> findAllAtivos();
}
