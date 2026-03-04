package ebd.api_ebd.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ebd.api_ebd.domain.entity.Igreja;

@Repository
public interface IgrejaRepository extends JpaRepository<Igreja, Integer> {
    
    Optional<Igreja> findByChaveLogin(String chaveLogin);
    
    List<Igreja> findByAtiva(boolean ativa);
}
