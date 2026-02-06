package ebd.api_ebd.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ebd.api_ebd.domain.entity.ChamadaDadosAdicionais;

public interface DadosAdicionaisRepository extends JpaRepository<ChamadaDadosAdicionais, Integer> {

    @Query("SELECT d FROM ChamadaDadosAdicionais d WHERE d.chamada.id = :chamadaId")
    Optional<ChamadaDadosAdicionais> findByChamadaId(@Param("chamadaId") Integer chamadaId);

}
