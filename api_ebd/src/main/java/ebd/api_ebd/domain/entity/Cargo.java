package ebd.api_ebd.domain.entity;

import jakarta.persistence.*;
import java.util.*;

@Entity
@Table(name = "cargo")
public class Cargo {
    
    @Id
    @GeneratedValue
    private UUID id;

    private String nome;

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
}
