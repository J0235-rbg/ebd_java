package ebd.api_ebd.domain.entity;

import java.time.LocalDate;
import java.util.UUID;

import ebd.api_ebd.domain.enums.TrimestreStatus;
import jakarta.persistence.*;
@Entity
@Table(name = "trim")
public class Trim {
    // Entidade Período

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "id_igreja")
    private Igreja igreja;

    private Integer ano;
    private LocalDate dataInicio;
    private LocalDate dataFim;

    @Enumerated(EnumType.STRING)
    private TrimestreStatus status;
}
