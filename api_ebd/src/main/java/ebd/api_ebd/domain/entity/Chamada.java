package ebd.api_ebd.domain.entity;

import java.time.LocalDate;
import java.util.UUID;

import ebd.api_ebd.domain.enums.ChamadaStatus;
import jakarta.persistence.*;

@Entity
@Table(
    name = "chamada",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"id_classe", "data", "id_trim"})
    }
)
public class Chamada {
    // Entidade Atendimento

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    private Igreja igreja;

    @ManyToOne
    private Setor setor;

    @ManyToOne
    private Congregacao congregacao;

    @ManyToOne
    private Classe classe;

    @ManyToOne
    private Trim trim;

    private LocalDate data;

    @Enumerated(EnumType.STRING)
    private ChamadaStatus status;
}
