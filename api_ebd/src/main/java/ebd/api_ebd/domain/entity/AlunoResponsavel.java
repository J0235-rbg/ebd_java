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

    @ManyToOne
    @JoinColumn(name = "id_igreja")
    private Igreja igreja;

    private String nome;

    @ManyToOne
    @JoinColumn(name = "id_setor")
    private Setor setor;

    @ManyToOne
    @JoinColumn(name = "id_congregacao")
    private Congregacao congregacao;

    @ManyToOne
    @JoinColumn(name = "id_classe")
    private Classe classe;

    private Integer sexo;

    private String email;
    private String observacao;

    private Integer status;
}
