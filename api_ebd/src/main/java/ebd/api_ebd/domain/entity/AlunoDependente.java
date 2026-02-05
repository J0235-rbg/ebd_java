package ebd.api_ebd.domain.entity;

import java.util.UUID;

import jakarta.persistence.*;

@Entity
@Table(name = "aluno_dependente")
public class AlunoDependente {
    // Entidade Dependente Estudante

    @Id
    @GeneratedValue
    private UUID id;

    private String nome;

    @Column(name = "id_responsavel", nullable = false)
    private UUID alunoResponsavel;

    @Column(name = "id_igreja", nullable = false)
    private UUID igreja;

    @Column(name = "id_setor", nullable = false)
    private UUID setor;

    @Column(name = "id_congregacao", nullable = false)
    private UUID congregacao;

    @Column(name = "id_classe", nullable = false)
    private UUID classe;

    private Integer status;

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

    public UUID getAlunoResponsavel() {
        return alunoResponsavel;
    }

    public void setAlunoResponsavel(UUID alunoResponsavel) {
        this.alunoResponsavel = alunoResponsavel;
    }

    public UUID getIgreja() {
        return igreja;
    }

    public void setIgreja(UUID igreja) {
        this.igreja = igreja;
    }

    public UUID getSetor() {
        return setor;
    }

    public void setSetor(UUID setor) {
        this.setor = setor;
    }

    public UUID getCongregacao() {
        return congregacao;
    }

    public void setCongregacao(UUID congregacao) {
        this.congregacao = congregacao;
    }

    public UUID getClasse() {
        return classe;
    }

    public void setClasse(UUID classe) {
        this.classe = classe;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
