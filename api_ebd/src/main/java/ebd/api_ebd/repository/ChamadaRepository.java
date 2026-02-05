package ebd.api_ebd.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ebd.api_ebd.domain.entity.Chamada;

public interface ChamadaRepository extends JpaRepository<Chamada, UUID> {
    // Repositório de Atendimento

    boolean exitsByClasseIdAndDataAndTrimId(
        UUID classeId,
        LocalDate data,
        UUID trimId
    );

    Optional<Chamada> findByClasseIdAndDataAndTrimId(
        UUID classeId,
        LocalDate data,
        UUID trimId
    );

    List<Chamada> findByClasseIdAndTrimIdOrderByData(
        UUID classeId,
        UUID trimId
    );

    @Query(
        """
                SELECT c
                FROM chamada c
                WHERE c.id_igreja = :igrejaId
                AND c.data = :data
                AND c.status = 'Aberta'
                """
    )
    List<Chamada> findChamadasAbertasDoDia(
        UUID igrejaId,
        LocalDate data
    );
}
