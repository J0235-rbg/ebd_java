package ebd.api_ebd.dto.relatorio;

import java.math.BigDecimal;

public class RelatorioClasseChamadaDTO {
    private String nomeClasse;
    private Integer matriculados;
    private Integer ausentes;
    private Integer presentes;
    private Integer biblias;
    private Integer revistas;
    private Integer visitantes;
    private Integer totalPresenca;
    private BigDecimal oferta;

    public RelatorioClasseChamadaDTO() {}

    public RelatorioClasseChamadaDTO(
        String nomeClasse,
        Integer matriculados,
        Integer ausentes,
        Integer presentes,
        Integer biblias,
        Integer revistas,
        Integer visitantes,
        Integer totalPresenca,
        BigDecimal oferta
    ) {
        this.nomeClasse = nomeClasse;
        this.matriculados = matriculados;
        this.ausentes = ausentes;
        this.presentes = presentes;
        this.biblias = biblias;
        this.revistas = revistas;
        this.visitantes = visitantes;
        this.totalPresenca = totalPresenca;
        this.oferta = oferta;
    }

    public String getNomeClasse() {
        return nomeClasse;
    }

    public void setNomeClasse(String nomeClasse) {
        this.nomeClasse = nomeClasse;
    }

    public Integer getMatriculados() {
        return matriculados;
    }

    public void setMatriculados(Integer matriculados) {
        this.matriculados = matriculados;
    }

    public Integer getAusentes() {
        return ausentes;
    }

    public void setAusentes(Integer ausentes) {
        this.ausentes = ausentes;
    }

    public Integer getPresentes() {
        return presentes;
    }

    public void setPresentes(Integer presentes) {
        this.presentes = presentes;
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

    public Integer getVisitantes() {
        return visitantes;
    }

    public void setVisitantes(Integer visitantes) {
        this.visitantes = visitantes;
    }

    public Integer getTotalPresenca() {
        return totalPresenca;
    }

    public void setTotalPresenca(Integer totalPresenca) {
        this.totalPresenca = totalPresenca;
    }

    public BigDecimal getOferta() {
        return oferta;
    }

    public void setOferta(BigDecimal oferta) {
        this.oferta = oferta;
    }
}
