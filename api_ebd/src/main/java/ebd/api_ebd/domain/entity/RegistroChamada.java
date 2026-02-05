package ebd.api_ebd.domain.entity;

import java.util.UUID;

import jakarta.persistence.*;

@Entity
@Table(
    name = "registro_chamada",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"id_chamada"})
    }
)
public class RegistroChamada {
    // Entidade Registro de Atendimento

    @Id
    @GeneratedValue
    private UUID id;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "id_chamada", nullable = false)
    private Chamada chamada;

    public Chamada getChamada() {
        return chamada;
    }

    public void setChamada(Chamada chamada) {
        this.chamada = chamada;
    }

    private UUID id_aluno;

    public UUID getId_aluno() {
        return id_aluno;
    }

    public void setId_aluno(UUID alunoId) {
        this.id_aluno = alunoId;
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
