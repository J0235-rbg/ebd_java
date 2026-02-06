package ebd.api_ebd.domain.entity;

import ebd.api_ebd.domain.enums.IdadeGrupo;
import jakarta.persistence.*;

@Entity
@Table(name = "classe")
public class Classe {
    // Entidade Turma

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
    private Integer igreja;

    public Integer getIgreja() {
        return igreja;
    }

    public void setIgreja(Integer igreja) {
        this.igreja = igreja;
    }

   
    @Column(name = "id_setor", nullable = false)
    private Integer setor;

    public Integer getSetor() {
        return setor;
    }

    public void setSetor(Integer setor) {
        this.setor = setor;
    }

    @Column(name = "id_congregacao",nullable = false)
    private Integer congregacao;

    public Integer getCongregacao() {
        return congregacao;
    }

    public void setCongregacao(Integer congregacao) {
        this.congregacao = congregacao;
    }

    @Column(name = "id_professor_01")
    private Integer professor1;

    public Integer getProfessor1() {
        return professor1;
    }

    public void setProfessor1(Integer professor1) {
        this.professor1 = professor1;
    }

    @Column(name = "id_professor_02")
    private Integer professor2;

    public Integer getProfessor2() {
        return professor2;
    }

    public void setProfessor2(Integer professor2) {
        this.professor2 = professor2;
    }

    @Column(name = "id_professor_03")
    private Integer professor03;

    public Integer getProfessor03() {
        return professor03;
    }

    public void setProfessor03(Integer professor03) {
        this.professor03 = professor03;
    }

    @Enumerated(EnumType.STRING)
    private IdadeGrupo faixIdadeGrupo;

    public IdadeGrupo getFaixIdadeGrupo() {
        return faixIdadeGrupo;
    }

    public void setFaixIdadeGrupo(IdadeGrupo faixIdadeGrupo) {
        this.faixIdadeGrupo = faixIdadeGrupo;
    }

    private boolean ativo;

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
}
