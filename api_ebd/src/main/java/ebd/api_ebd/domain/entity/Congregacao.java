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

    @Column(name = "id_setor", nullable = false)
    private UUID setor;

    @Column(name = "id_igreja", nullable = false)
    private UUID igreja;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public UUID getSetor() {
        return setor;
    }

    public void setSetor(UUID setor) {
        this.setor = setor;
    }

    public UUID getIgreja() {
        return igreja;
    }

    public void setIgreja(UUID igreja) {
        this.igreja = igreja;
    }
}
