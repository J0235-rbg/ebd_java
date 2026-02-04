package ebd.api_ebd.domain.entity;

import java.util.UUID;

import jakarta.persistence.*;

@Entity
@Table(
    name = "registro_chamada",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"id_chamada"})
    }
)
public class RegistroChamada {
    // Entidade Registro de Atendimento

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    private Chamada chamada;

    private Integer id_aluno;

    private Integer biblia;

    private Integer revista;

    private Integer status;



}
