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

    private String nome;

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
    @JoinColumn(name = "id_professor_01")
    private Pessoa professor1;

    @ManyToOne
    @JoinColumn(name = "id_professor_02")
    private Pessoa professor2;

    @ManyToOne
    @JoinColumn(name = "id_professor_03")
    private Pessoa professor03;

    @Enumerated(EnumType.STRING)
    private IdadeGrupo faixIdadeGrupo;

    private boolean ativo;
}
