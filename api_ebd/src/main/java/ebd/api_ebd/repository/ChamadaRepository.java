package ebd.api_ebd.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ebd.api_ebd.domain.entity.Chamada;
import ebd.api_ebd.domain.enums.ChamadaStatus;

public interface ChamadaRepository extends JpaRepository<Chamada, Integer> {
    // Repositório de Atendimento

    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM Chamada c WHERE c.classe = :classeId AND c.data = :data AND c.trim = :trimId")
    boolean existsByClasseIdAndDataAndTrimId(
        @org.springframework.data.repository.query.Param("classeId") Integer classeId,
        @org.springframework.data.repository.query.Param("data") LocalDate data,
        @org.springframework.data.repository.query.Param("trimId") Integer trimId
    );

    @Query("SELECT c FROM Chamada c WHERE c.classe = :classeId AND c.data = :data AND c.trim = :trimId")
    Optional<Chamada> findByClasseAndDataAndTrim(
        @org.springframework.data.repository.query.Param("classeId") Integer classeId,
        @org.springframework.data.repository.query.Param("data") LocalDate data,
        @org.springframework.data.repository.query.Param("trimId") Integer trimId
    );

    @Query("SELECT c FROM Chamada c WHERE c.classe = :classeId AND c.trim = :trimId ORDER BY c.data")
    List<Chamada> findByClasseAndTrimOrderByData(
        @org.springframework.data.repository.query.Param("classeId") Integer classeId,
        @org.springframework.data.repository.query.Param("trimId") Integer trimId
    );

    // @Query(
    //     """
    //             SELECT c
    //             FROM Chamada c
    //             WHERE c.igreja = :igrejaId
    //             AND c.trim = :trimId
    //             AND c.data = :data
    //             AND c.status = 'Aberto'
    //             """
    // )
    @Query("SELECT c FROM Chamada c WHERE c.classe = :classeId ORDER BY c.data")
    List<Chamada> findByClasse(@org.springframework.data.repository.query.Param("classeId") Integer classeId);

    @Query("SELECT c FROM Chamada c WHERE c.trim = :trimestreId ORDER BY c.data")
    List<Chamada> findByTrimestre(@org.springframework.data.repository.query.Param("trimestreId") Integer trimestreId);

    @Query("SELECT c FROM Chamada c WHERE c.classe = :classeId AND c.trim = :trimestreId ORDER BY c.data")
    List<Chamada> findByClasseAndTrimestre(
        @org.springframework.data.repository.query.Param("classeId") Integer classeId,
        @org.springframework.data.repository.query.Param("trimestreId") Integer trimestreId
    );

    List<Chamada> findByTrimAndStatus(Integer trimestreId, ChamadaStatus stauts);

    List<Chamada> findByIgrejaAndTrimAndDataGreaterThanEqualAndStatusOrderByDataAsc(
        Integer igreja,
        Integer trim,
        LocalDate data,
        ChamadaStatus status
    );
}
