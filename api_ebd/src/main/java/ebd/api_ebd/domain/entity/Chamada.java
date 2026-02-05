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

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @ManyToOne
    private Igreja igreja;

    public Igreja getIgreja() {
        return igreja;
    }

    public void setIgreja(Igreja igreja) {
        this.igreja = igreja;
    }

    @ManyToOne
    private Setor setor;

    public Setor getSetor() {
        return setor;
    }

    public void setSetor(Setor setor) {
        this.setor = setor;
    }

    @ManyToOne
    private Congregacao congregacao;

    public Congregacao getCongregacao() {
        return congregacao;
    }

    public void setCongregacao(Congregacao congregacao) {
        this.congregacao = congregacao;
    }

    @ManyToOne
    private Classe classe;

    public Classe getClasse() {
        return classe;
    }

    public void setClasse(Classe classe) {
        this.classe = classe;
    }

    @ManyToOne
    private Trim trim;

    public Trim getTrim() {
        return trim;
    }

    public void setTrim(Trim trim) {
        this.trim = trim;
    }

    private LocalDate data;

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    @Enumerated(EnumType.STRING)
    private ChamadaStatus status;

    public ChamadaStatus getStatus() {
        return status;
    }

    public void setStatus(ChamadaStatus status) {
        this.status = status;
    }

    
}
