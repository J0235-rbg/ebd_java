package ebd.api_ebd.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ebd.api_ebd.domain.entity.AlunoResponsavel;

public interface AlunoResponsavelRepository extends JpaRepository<AlunoResponsavel, Integer> {
    
    @Query("SELECT a FROM AlunoResponsavel a WHERE a.classe = :classeId")
    List<AlunoResponsavel> findByClasse(@Param("classeId") Integer classeId);

    @Query("SELECT a FROM AlunoResponsavel a WHERE a.id = :alunoId AND a.classe = :classeId")
    AlunoResponsavel findByIdAndClasse(@Param("alunoId") Integer alunoId, @Param("classeId") Integer classeId);
    
    @Query("SELECT a FROM AlunoResponsavel a WHERE a.congregacao = :congregacaoId")
    List<AlunoResponsavel> findByCongregacao(@Param("congregacaoId") Integer congregacaoId);
    
    @Query("SELECT a FROM AlunoResponsavel a WHERE a.igreja = :igrejaId")
    List<AlunoResponsavel> findByIgreja(@Param("igrejaId") Integer igrejaId);
    
    @Query("SELECT a FROM AlunoResponsavel a WHERE a.status = 1")
    List<AlunoResponsavel> findAllAtivos();
}
