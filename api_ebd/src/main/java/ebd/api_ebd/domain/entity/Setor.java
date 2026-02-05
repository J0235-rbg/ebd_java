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

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    private String nome;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Column(name = "id_igreja", nullable = false)
    private UUID idIgreja;

    public UUID getIdIgreja() {
        return idIgreja;
    }

    public void setIdIgreja(UUID idIgreja) {
        this.idIgreja = idIgreja;
    }
}
