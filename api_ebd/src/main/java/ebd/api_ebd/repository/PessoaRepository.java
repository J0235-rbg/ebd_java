package ebd.api_ebd.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ebd.api_ebd.domain.entity.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Integer> {
    
    @Query("SELECT p FROM Pessoa p WHERE p.login = :login")
    Optional<Pessoa> findByLogin(@Param("login") String login);
    
    @Query("SELECT p FROM Pessoa p WHERE p.congregacao = :congregacaoId")
    List<Pessoa> findByCongregacao(@Param("congregacaoId") Integer congregacaoId);
    
    @Query("SELECT p FROM Pessoa p WHERE p.cargo = :cargoId")
    List<Pessoa> findByCargo(@Param("cargoId") Integer cargoId);
    
    @Query("SELECT p FROM Pessoa p WHERE p.igreja = :igrejaId")
    List<Pessoa> findByIgreja(@Param("igrejaId") Integer igrejaId);
    
    @Query("SELECT p FROM Pessoa p WHERE p.ativo = true")
    List<Pessoa> findAllAtivos();
    
}
