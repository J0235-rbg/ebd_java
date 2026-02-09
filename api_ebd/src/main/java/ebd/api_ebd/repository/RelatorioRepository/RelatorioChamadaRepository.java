package ebd.api_ebd.repository.RelatorioRepository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ebd.api_ebd.domain.entity.RegistroChamada;

public interface RelatorioChamadaRepository extends JpaRepository<RegistroChamada, Integer> {
    // Serviço de Relatório

    List<RegistroChamada> findByChamada(Integer chamadaId);
}
