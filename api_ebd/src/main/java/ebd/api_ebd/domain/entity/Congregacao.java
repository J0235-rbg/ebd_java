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

    public Setor getSetor() {
        return setor;
    }

    public void setSetor(Setor setor) {
        this.setor = setor;
    }

    public Igreja getIgreja() {
        return igreja;
    }

    public void setIgreja(Igreja igreja) {
        this.igreja = igreja;
    }
}
