package ebd.api_ebd.domain.entity;

import java.math.*;
import java.util.UUID;

import jakarta.persistence.*;

@Entity
@Table(name = "dados_adicionais_chamada")
public class ChamadaDadosAdicionais {
    // Entidade Dados Extras de Atendimento

    @Id
    @GeneratedValue
    private UUID id;

    @OneToOne
    private Chamada chamada;

    private BigDecimal oferta;
    private Integer visitantes;
    private Integer presentes;
    private Integer ausentes;
    private Integer matriculados;
    private Integer totalPresenca;
    private Integer biblias;
    private Integer revistas;

    @ManyToOne
    private Pessoa responsavel;
    
}
