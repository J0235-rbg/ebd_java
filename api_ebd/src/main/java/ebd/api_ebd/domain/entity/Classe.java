package ebd.api_ebd.domain.entity;

import java.util.UUID;

import ebd.api_ebd.domain.enums.IdadeGrupo;
import jakarta.persistence.*;

@Entity
@Table(name = "classe")
public class Classe {
    // Entidade Turma

    @Id
    @GeneratedValue
    private UUID id;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
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
    private UUID igreja;

    public UUID getIgreja() {
        return igreja;
    }

    public void setIgreja(UUID igreja) {
        this.igreja = igreja;
    }

   
    @Column(name = "id_setor", nullable = false)
    private UUID setor;

    public UUID getSetor() {
        return setor;
    }

    public void setSetor(UUID setor) {
        this.setor = setor;
    }

    @Column(name = "id_congregacao",nullable = false)
    private UUID congregacao;

    public UUID getCongregacao() {
        return congregacao;
    }

    public void setCongregacao(UUID congregacao) {
        this.congregacao = congregacao;
    }

    @Column(name = "id_professor_01")
    private UUID professor1;

    public UUID getProfessor1() {
        return professor1;
    }

    public void setProfessor1(UUID professor1) {
        this.professor1 = professor1;
    }

    @Column(name = "id_professor_02")
    private UUID professor2;

    public UUID getProfessor2() {
        return professor2;
    }

    public void setProfessor2(UUID professor2) {
        this.professor2 = professor2;
    }

    @Column(name = "id_professor_03")
    private UUID professor03;

    public UUID getProfessor03() {
        return professor03;
    }

    public void setProfessor03(UUID professor03) {
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
