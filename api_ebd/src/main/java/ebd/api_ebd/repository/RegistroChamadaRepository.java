package ebd.api_ebd.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ebd.api_ebd.domain.entity.Chamada;
import ebd.api_ebd.domain.entity.RegistroChamada;

public interface RegistroChamadaRepository extends JpaRepository<RegistroChamada, UUID> {
    // Repositório de Registro de Atendimento

    
    boolean existsByChamadaIdAndAlunoId(
        @Param("chamadaId") UUID chamadaId,
        @Param("alunoId") UUID id_aluno
    );

    @Query("SELECT r FROM RegistroChamada r WHERE r.chamada.id = :chamadaId")
    List<RegistroChamada> findByChamada_Id(@Param("chamadaId") UUID chamadaId);

    @Query("""
            SELECT COUNT(r)
            FROM RegistroChamada r
            WHERE r.chamada.id = :chamadaId
            AND r.status = :status
            """)
            long countByStatus(
                @Param("chamadaId") UUID chamadaId,
                @Param("status") int status
            );
}
