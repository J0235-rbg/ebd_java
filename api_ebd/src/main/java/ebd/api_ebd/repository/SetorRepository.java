package ebd.api_ebd.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ebd.api_ebd.domain.entity.Setor;

public interface SetorRepository extends JpaRepository<Setor, Integer> {
    
    @Query("SELECT s FROM Setor s WHERE s.idIgreja = :igrejaId")
    List<Setor> findByIgrejaId(@Param("igrejaId") Integer igrejaId);
}
