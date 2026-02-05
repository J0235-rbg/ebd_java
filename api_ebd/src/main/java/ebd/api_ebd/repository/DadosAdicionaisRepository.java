package ebd.api_ebd.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import ebd.api_ebd.domain.entity.Chamada;
import ebd.api_ebd.domain.entity.ChamadaDadosAdicionais;

public interface DadosAdicionaisRepository extends JpaRepository<ChamadaDadosAdicionais, UUID> {

    public Optional<ChamadaDadosAdicionais> findByChamada(UUID chamada);

}
