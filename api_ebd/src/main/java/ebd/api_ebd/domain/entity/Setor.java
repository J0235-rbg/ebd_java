package ebd.api_ebd.domain.entity;

import java.util.UUID;

import jakarta.persistence.*;

@Entity
@Table(name = "setor")
public class Setor {
    // Entidade Setor

    @Id
    @GeneratedValue
    private UUID id;

    private String nome;

    @ManyToOne
    @JoinColumn(name = "id_igreja")
    private Igreja idIgreja;
}
