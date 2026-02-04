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
}
