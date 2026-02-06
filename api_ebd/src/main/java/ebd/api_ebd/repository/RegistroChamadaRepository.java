package ebd.api_ebd.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ebd.api_ebd.domain.entity.Chamada;
import ebd.api_ebd.domain.entity.RegistroChamada;

public interface RegistroChamadaRepository extends JpaRepository<RegistroChamada, Integer> {
    // Repositório de Registro de Atendimento

    @Query("SELECT COUNT(r) > 0 FROM RegistroChamada r WHERE r.chamada = :chamadaId AND r.aluno = :alunoId")
    boolean existsByChamadaIdAndAlunoId(
        @Param("chamadaId") Integer chamadaId,
        @Param("alunoId") Integer alunoId
    );

    @Query("SELECT r FROM RegistroChamada r WHERE r.chamada = :chamadaId")
    List<RegistroChamada> findByChamadaId(@Param("chamadaId") Integer chamadaId);

    @Query("""
            SELECT COUNT(r)
            FROM RegistroChamada r
            WHERE r.chamada = :chamadaId
            AND r.status = :status
            """)
            long countByStatus(
                @Param("chamadaId") Integer chamadaId,
                @Param("status") int status
            );
}
