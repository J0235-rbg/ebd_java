package ebd.api_ebd.domain.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "congregacao")
public class Congregacao {
    // Entidade Congregação

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nome;

    @Column(name = "id_setor", nullable = false)
    private Integer setor;

    @Column(name = "id_igreja", nullable = false)
    private Integer igreja;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getSetor() {
        return setor;
    }

    public void setSetor(Integer setor) {
        this.setor = setor;
    }

    public Integer getIgreja() {
        return igreja;
    }

    public void setIgreja(Integer igreja) {
        this.igreja = igreja;
    }
}
