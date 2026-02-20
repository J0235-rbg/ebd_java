package ebd.api_ebd.dto.relatorio;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class RelatorioChamadaDTO {

    private Integer chamadaId;
    private LocalDate data;
    private Integer classeId;
    private String status;

    private Integer presentes;
    private Integer ausentes;
    private Integer visitantes;
    private BigDecimal oferta;
    private Integer biblias;
    private Integer revistas;

    private List<RelatorioAlunoChamadaDTO> alunos;

    public Integer getChamadaId() {
        return chamadaId;
    }

    public LocalDate getData() {
        return data;
    }

    public Integer getClasseId() {
        return classeId;
    }

    public String getStatus() {
        return status;
    }

    public Integer getPresentes() {
        return presentes;
    }

    public Integer getAusentes() {
        return ausentes;
    }

    public Integer getVisitantes() {
        return visitantes;
    }

    public Integer getBiblias() {
        return biblias;
    }

    public Integer getRevistas() {
        return revistas;
    }

    public BigDecimal getOferta(){
        return oferta;
    }

    public List<RelatorioAlunoChamadaDTO> getAlunos() {
        return alunos;
    }

    public RelatorioChamadaDTO() {}

    public RelatorioChamadaDTO(
        Integer chamadaId,
        LocalDate data,
        Integer classeId,
        String status,
        Integer presentes,
        Integer ausentes,
        Integer visitantes,
        Integer biblias,
        Integer revistas,
        BigDecimal oferta,
        List<RelatorioAlunoChamadaDTO> alunos
    ) {
        this.chamadaId = chamadaId;
        this.data = data;
        this.classeId = classeId;
        this.status = status;
        this.presentes = presentes;
        this.ausentes = ausentes;
        this.visitantes = visitantes;
        this.biblias = biblias;
        this.revistas = revistas;
        this.oferta = oferta;
        this.alunos = alunos;
    }

    // Setters
    public void setChamadaId(Integer chamadaId) {
        this.chamadaId = chamadaId;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public void setClasseId(Integer classeId) {
        this.classeId = classeId;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setPresentes(Integer presentes) {
        this.presentes = presentes;
    }

    public void setAusentes(Integer ausentes) {
        this.ausentes = ausentes;
    }

    public void setVisitantes(Integer visitantes) {
        this.visitantes = visitantes;
    }

    public void setBiblias(Integer biblias) {
        this.biblias = biblias;
    }

    public void setRevistas(Integer revistas) {
        this.revistas = revistas;
    }

    public void setOferta(BigDecimal oferta){
        this.oferta = oferta;
    }

    public void setAlunos(List<RelatorioAlunoChamadaDTO> alunos) {
        this.alunos = alunos;
    }
}
