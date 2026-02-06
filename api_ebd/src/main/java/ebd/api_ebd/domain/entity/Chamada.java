package ebd.api_ebd.domain.entity;

import java.time.LocalDate;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "id_igreja", nullable = false)
    private Integer igreja;

    public Integer getIgreja() {
        return igreja;
    }

    public void setIgreja(Integer igreja) {
        this.igreja = igreja;
    }

    @Column(name = "id_setor", nullable = false)
    private Integer setor;

    public Integer getSetor() {
        return setor;
    }

    public void setSetor(Integer setor) {
        this.setor = setor;
    }

    @Column(name = "id_congregacao", nullable = false)
    private Integer congregacao;

    public Integer getCongregacao() {
        return congregacao;
    }

    public void setCongregacao(Integer congregacao) {
        this.congregacao = congregacao;
    }

    @Column(name = "id_classe", nullable = false)
    private Integer classe;

    public Integer getClasse() {
        return classe;
    }

    public void setClasse(Integer classe) {
        this.classe = classe;
    }

    @Column(name = "id_trim", nullable = false)
    private Integer trim;

    public Integer getTrim() {
        return trim;
    }

    public void setTrim(Integer trim) {
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

    
    public static Chamada nova(
        Integer igrejaId,
        Integer setorId,
        Integer congregacaoId,
        Integer classeId,
        Integer trimId,
        LocalDate data
    ) {

        Chamada c = new Chamada();
        c.setIgreja(igrejaId);
        c.setSetor(setorId);
        c.setCongregacao(congregacaoId);
        c.setClasse(classeId);
        c.setTrim(trimId);
        c.setData(data);
        c.setStatus(ChamadaStatus.Aberto);

        return c;
    }
}
