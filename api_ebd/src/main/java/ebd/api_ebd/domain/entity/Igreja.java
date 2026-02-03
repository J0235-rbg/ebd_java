package ebd.api_ebd.domain.entity;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "igreja")
public class Igreja {
    // Entidade Igreja

    @Id
    @GeneratedValue
    private UUID id;

    private String nome;
    private String endereco;

    @Column(unique = true, nullable = false)
    private String chaveLogin;

    private boolean ativa;


}
