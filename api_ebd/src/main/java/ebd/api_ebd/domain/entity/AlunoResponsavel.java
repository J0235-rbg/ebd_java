package ebd.api_ebd.domain.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "aluno_responsavel")
public class AlunoResponsavel {
    // Entidade Responsável Estudante

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "id_igreja", nullable = false)
    private Integer igreja;

    private String nome;

    @Column(name = "id_setor", nullable = false)
    private Integer setor;

    @Column(name = "id_congregacao", nullable = false)
    private Integer congregacao;

    @Column(name = "id_classe", nullable = false)
    private Integer classe;

    private Integer sexo;

    private String email;
    private String observacao;

    private Integer status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIgreja() {
        return igreja;
    }

    public void setIgreja(Integer igreja) {
        this.igreja = igreja;
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

    public Integer getCongregacao() {
        return congregacao;
    }

    public void setCongregacao(Integer congregacao) {
        this.congregacao = congregacao;
    }

    public Integer getClasse() {
        return classe;
    }

    public void setClasse(Integer classe) {
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
