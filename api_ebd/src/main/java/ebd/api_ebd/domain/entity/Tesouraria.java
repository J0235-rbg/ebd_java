package ebd.api_ebd.domain.entity;

import java.math.BigDecimal;
import java.util.UUID;

import jakarta.persistence.*;

@Entity
@Table(name = "tesouraria")
public class Tesouraria {
    // Entidade Oferta

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    private Chamada chamada;

    @ManyToOne
    private Classe classe;

    @ManyToOne
    private Trim trim;

    private BigDecimal valor;
}
