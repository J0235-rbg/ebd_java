package ebd.api_ebd.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ebd.api_ebd.domain.entity.Classe;

public interface ClasseRepository extends JpaRepository<Classe, Integer> {
    // Repositório de Turma

    @Query("SELECT c FROM Classe c WHERE c.igreja = :igrejaId AND c.ativo = true")
    List<Classe> findByIgrejaIdAndAtivoTrue(@Param("igrejaId") Integer igrejaId);

    @Query("SELECT c FROM Classe c WHERE c.igreja = :igrejaId AND c.congregacao = :congregacaoId AND c.setor = :setorId AND c.ativo = true")
    List<Classe> findByIgrejaIdAndCongregacaoIdAndSetorIdAndAtivoTrue(
        @Param("igrejaId") Integer igrejaId,
        @Param("congregacaoId") Integer congregacaoId,
        @Param("setorId") Integer setorId
    );

    @Query(
        """
                SELECT c
                FROM Classe c
                WHERE c.igreja = :igrejaId
                AND c.ativo = true
                AND (
                    c.professor1 = :pessoaId
                    OR c.professor2 = :pessoaId
                    OR c.professor03 = :pessoaId
                )
                """
    )
    List<Classe> findClasseDoProfessor(
        Integer igrejaId,
        Integer pessoaId
    );

    @Query("SELECT c FROM Classe c WHERE c.congregacao = :congregacaoId")
    List<Classe> findByCongregacao(@Param("congregacaoId") Integer congregacaoId);

}
