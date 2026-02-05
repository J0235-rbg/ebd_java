package ebd.api_ebd.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import ebd.api_ebd.domain.entity.Trim;
import ebd.api_ebd.domain.enums.TrimestreStatus;

public interface TrimestreRepository extends JpaRepository<Trim, UUID> {


    boolean existByIgrejaIdAndStatus(UUID igrejaId, TrimestreStatus status);

    Optional<Trim> findByIgrejaAndStatus(UUID igrejaid, TrimestreStatus status);

    List<Trim> findByIgrejaOrderByDataInicioDesc(UUID igrejaId);

} 
