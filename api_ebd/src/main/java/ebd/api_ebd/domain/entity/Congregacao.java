package ebd.api_ebd.domain.entity;

import java.util.UUID;

import jakarta.persistence.*;

@Entity
@Table(name = "congregacao")
public class Congregacao {
    // Entidade Congregação

    @Id
    @GeneratedValue
    private UUID id;

    private String nome;

    @ManyToOne
    @JoinColumn(name = "id_setor")
    private Setor setor;

    @ManyToOne
    @JoinColumn(name = "id_igreja")
    private Igreja igreja;
}
