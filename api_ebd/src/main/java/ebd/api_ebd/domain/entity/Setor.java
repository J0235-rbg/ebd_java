package ebd.api_ebd.domain.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "setor")
public class Setor {
    // Entidade Setor

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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
    private Integer idIgreja;

    public Integer getIdIgreja() {
        return idIgreja;
    }

    public void setIdIgreja(Integer idIgreja) {
        this.idIgreja = idIgreja;
    }
}
