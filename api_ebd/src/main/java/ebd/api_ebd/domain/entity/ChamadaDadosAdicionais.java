package ebd.api_ebd.domain.entity;

import java.math.*;

import jakarta.persistence.*;

@Entity
@Table(name = "dados_adicionais_chamada")
public class ChamadaDadosAdicionais {
    // Entidade Dados Extras de Atendimento

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_chamada", nullable = false, unique = true)
    private Chamada chamada;

    private BigDecimal oferta;
    private Integer visitantes;
    private Integer presentes;
    private Integer ausentes;
    private Integer matriculados;
    private Integer totalPresenca;
    private Integer biblias;
    private Integer revistas;

    @Column(name = "id_responsavel")
    private Integer responsavel;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Chamada getChamada() {
        return chamada;
    }

    public void setChamada(Chamada chamada) {
        this.chamada = chamada;
    }

    public BigDecimal getOferta() {
        return oferta;
    }

    public void setOferta(BigDecimal oferta) {
        this.oferta = oferta;
    }

    public Integer getVisitantes() {
        return visitantes;
    }

    public void setVisitantes(Integer visitantes) {
        this.visitantes = visitantes;
    }

    public Integer getPresentes() {
        return presentes;
    }

    public void setPresentes(Integer presentes) {
        this.presentes = presentes;
    }

    public Integer getAusentes() {
        return ausentes;
    }

    public void setAusentes(Integer ausentes) {
        this.ausentes = ausentes;
    }

    public Integer getMatriculados() {
        return matriculados;
    }

    public void setMatriculados(Integer matriculados) {
        this.matriculados = matriculados;
    }

    public Integer getTotalPresenca() {
        return totalPresenca;
    }

    public void setTotalPresenca(Integer totalPresenca) {
        this.totalPresenca = totalPresenca;
    }

    public Integer getBiblias() {
        return biblias;
    }

    public void setBiblias(Integer biblias) {
        this.biblias = biblias;
    }

    public Integer getRevistas() {
        return revistas;
    }

    public void setRevistas(Integer revistas) {
        this.revistas = revistas;
    }

    public Integer getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(Integer responsavel) {
        this.responsavel = responsavel;
    }
    
}
