package ebd.api_ebd.domain.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.*;

@Entity
@Table(name = "pessoa")
public class Pessoa {
    // Entidade Pessoa

    @Id
    @GeneratedValue
    private UUID id;

    private String nome;
    private String telefone;
    private LocalDate dt_nascimento;

    private String endereco;
    private String numero;
    private String bairro;

    @ManyToOne
    @JoinColumn(name = "id_cargo")
    private Cargo cargo;

    @ManyToOne
    @JoinColumn(name = "id_congregacao")
    private Congregacao congregacao;

    private String login;
    private String keyApp;

    private boolean admin;
    private boolean podeRelatorio;
    private boolean ativo;

}
