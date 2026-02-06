package ebd.api_ebd.domain.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "aluno_dependente")
public class AlunoDependente {
    // Entidade Dependente Estudante

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nome;

    @Column(name = "id_responsavel", nullable = false)
    private Integer alunoResponsavel;

    @Column(name = "id_igreja", nullable = false)
    private Integer igreja;

    @Column(name = "id_setor", nullable = false)
    private Integer setor;

    @Column(name = "id_congregacao", nullable = false)
    private Integer congregacao;

    @Column(name = "id_classe", nullable = false)
    private Integer classe;

    private Integer status;

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

    public Integer getAlunoResponsavel() {
        return alunoResponsavel;
    }

    public void setAlunoResponsavel(Integer alunoResponsavel) {
        this.alunoResponsavel = alunoResponsavel;
    }

    public Integer getIgreja() {
        return igreja;
    }

    public void setIgreja(Integer igreja) {
        this.igreja = igreja;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
