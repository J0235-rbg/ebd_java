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

    private String endereco;

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    @Column(unique = true, nullable = false)
    private String chaveLogin;

    public String getChaveLogin() {
        return chaveLogin;
    }

    public void setChaveLogin(String chaveLogin) {
        this.chaveLogin = chaveLogin;
    }

    private boolean ativa;

    public boolean isAtiva() {
        return ativa;
    }

    public void setAtiva(boolean ativa) {
        this.ativa = ativa;
    }


}
