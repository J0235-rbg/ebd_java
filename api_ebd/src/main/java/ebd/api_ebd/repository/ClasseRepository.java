package ebd.api_ebd.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ebd.api_ebd.domain.entity.Classe;

public interface ClasseRepository extends JpaRepository<Classe, UUID> {
    // Repositório de Turma

    List<Classe> findByIgrejaIdAndAtivoTrue(UUID igrejaId);

    List<Classe> findByIgrejaIdAndCongregacaoIdSetorIdAndAtivoTrue(
        UUID igrejaId,
        UUID congregacaoId,
        UUID setorId
    );

    @Query(
        """
                SELECT c
                FROM classe c
                WHERE c.id_igreja = :igrejaId
                AND c.ativo = true
                AND (
                    c.id_professor_01 = :pessoaId
                    OR c.id_professor_02 = :pessoaId
                    Or c.id_professor_03 = :pessoaId
                )
                """
    )
    List<Classe> findClasseDoProfessor(
        UUID igrejaId,
        UUID pessoaId
    );


}
