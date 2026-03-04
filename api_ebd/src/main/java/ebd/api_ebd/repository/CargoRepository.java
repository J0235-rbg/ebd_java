package ebd.api_ebd.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ebd.api_ebd.domain.entity.Cargo;

public interface CargoRepository extends JpaRepository<Cargo, Integer> {
    // Repositório de Cargo
}
