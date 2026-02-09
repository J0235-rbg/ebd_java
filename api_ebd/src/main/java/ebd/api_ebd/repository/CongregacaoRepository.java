package ebd.api_ebd.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;

import ebd.api_ebd.domain.entity.Congregacao;

public interface CongregacaoRepository extends JpaRepository<Congregacao, Integer> {
    
    @Query("SELECT c FROM Congregacao c WHERE c.igreja = :igrejaId")
    List<Congregacao> findByIgrejaId(@Param("igrejaId") Integer igrejaId);
    
    @Query("SELECT c FROM Congregacao c WHERE c.setor = :setorId")
    List<Congregacao> findBySetorId(@Param("setorId") Integer setorId);

}
