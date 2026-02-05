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

    @Column(name = "id_igreja", nullable = false)
    private UUID igreja;

    public UUID getIgreja() {
        return igreja;
    }

    public void setIgreja(UUID igreja) {
        this.igreja = igreja;
    }

    @Column(name = "id_setor", nullable = false)
    private UUID setor;

    public UUID getSetor() {
        return setor;
    }

    public void setSetor(UUID setor) {
        this.setor = setor;
    }

    @Column(name = "id_congregacao", nullable = false)
    private UUID congregacao;

    public UUID getCongregacao() {
        return congregacao;
    }

    public void setCongregacao(UUID congregacao) {
        this.congregacao = congregacao;
    }

    @Column(name = "id_classe", nullable = false)
    private UUID classe;

    public UUID getClasse() {
        return classe;
    }

    public void setClasse(UUID classe) {
        this.classe = classe;
    }

    @Column(name = "id_trim", nullable = false)
    private UUID trim;

    public UUID getTrim() {
        return trim;
    }

    public void setTrim(UUID trim) {
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

    
    public static Chamada nova(Classe classe, Trim trim, LocalDate data) {

        Chamada c = new Chamada();
        c.setIgreja(classe.getIgreja());
        c.setSetor(classe.getSetor());
        c.setCongregacao(classe.getCongregacao());
        c.setClasse(classe.getId());
        c.setTrim(trim.getId());
        c.setData(data);
        c.setStatus(ChamadaStatus.Aberto);

        return c;
    }
}
