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

    @ManyToOne
    @JoinColumn(name = "id_responsavel")
    private AlunoResponsavel alunoResponsavel;

    @ManyToOne
    @JoinColumn(name = "id_igreja")
    private Igreja igreja;

    @ManyToOne
    @JoinColumn(name = "id_setor")
    private Setor setor;

    @ManyToOne
    @JoinColumn(name = "id_congregacao")
    private Congregacao congregacao;

    @ManyToOne
    @JoinColumn(name = "id_classe")
    private Classe classe;

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

    public AlunoResponsavel getAlunoResponsavel() {
        return alunoResponsavel;
    }

    public void setAlunoResponsavel(AlunoResponsavel alunoResponsavel) {
        this.alunoResponsavel = alunoResponsavel;
    }

    public Igreja getIgreja() {
        return igreja;
    }

    public void setIgreja(Igreja igreja) {
        this.igreja = igreja;
    }

    public Setor getSetor() {
        return setor;
    }

    public void setSetor(Setor setor) {
        this.setor = setor;
    }

    public Congregacao getCongregacao() {
        return congregacao;
    }

    public void setCongregacao(Congregacao congregacao) {
        this.congregacao = congregacao;
    }

    public Classe getClasse() {
        return classe;
    }

    public void setClasse(Classe classe) {
        this.classe = classe;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
