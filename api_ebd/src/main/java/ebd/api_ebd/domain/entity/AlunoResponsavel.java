package ebd.api_ebd.domain.entity;

import java.util.UUID;

import jakarta.persistence.*;

@Entity
@Table(name = "aluno_responsavel")
public class AlunoResponsavel {
    // Entidade Responsável Estudante

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "id_igreja", nullable = false)
    private UUID igreja;

    private String nome;

    @Column(name = "id_setor", nullable = false)
    private UUID setor;

    @Column(name = "id_congregacao", nullable = false)
    private UUID congregacao;

    @Column(name = "id_classe", nullable = false)
    private UUID classe;

    private Integer sexo;

    private String email;
    private String observacao;

    private Integer status;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getIgreja() {
        return igreja;
    }

    public void setIgreja(UUID igreja) {
        this.igreja = igreja;
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

    public Integer getSexo() {
        return sexo;
    }

    public void setSexo(Integer sexo) {
        this.sexo = sexo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
