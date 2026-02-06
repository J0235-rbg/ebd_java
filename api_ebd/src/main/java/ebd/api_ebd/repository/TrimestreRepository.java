package ebd.api_ebd.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ebd.api_ebd.domain.entity.Trim;
import ebd.api_ebd.domain.enums.TrimestreStatus;

public interface TrimestreRepository extends JpaRepository<Trim, Integer> {

    @Query("SELECT CASE WHEN COUNT(t) > 0 THEN true ELSE false END FROM Trim t WHERE t.igreja = :igrejaId AND t.status = :status")
    boolean existsByIgrejaAndStatus(@Param("igrejaId") Integer igrejaId, @Param("status") TrimestreStatus status);

    @Query("SELECT t FROM Trim t WHERE t.igreja = :igrejaId AND t.status = :status")
    Optional<Trim> findByIgrejaAndStatus(@Param("igrejaId") Integer igrejaId, @Param("status") TrimestreStatus status);

    @Query("SELECT t FROM Trim t WHERE t.igreja = :igrejaId ORDER BY t.dataInicio DESC")
    List<Trim> findByIgrejaOrderByDataInicioDesc(@Param("igrejaId") Integer igrejaId);
} 
