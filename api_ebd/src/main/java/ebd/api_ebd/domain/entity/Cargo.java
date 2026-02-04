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
}
