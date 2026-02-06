package ebd.api_ebd.domain.entity;

import jakarta.persistence.*;

@Entity
@Table(
    name = "registro_chamada",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"id_chamada", "id_aluno"})
    }
)
public class RegistroChamada {
    // Entidade Registro de Atendimento

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

  
    @Column(name = "id_chamada", nullable = false)
    private Integer chamada;

    public Integer getChamada() {
        return chamada;
    }

    public void setChamada(Integer chamada) {
        this.chamada = chamada;
    }

    @Column(name = "id_aluno", nullable = false)
    private Integer aluno;

    public Integer getaluno() {
        return aluno;
    }

    public void setaluno(Integer aluno) {
        this.aluno = aluno;
    }

    private Integer biblia;

    public Integer getBiblia() {
        return biblia;
    }

    public void setBiblia(Integer biblia) {
        this.biblia = biblia;
    }

    private Integer revista;

    public Integer getRevista() {
        return revista;
    }

    public void setRevista(Integer revista) {
        this.revista = revista;
    }

    private Integer status;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }



}
