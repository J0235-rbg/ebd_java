package ebd.api_ebd.dto.relatorio;

import java.time.LocalDate;
import java.util.List;

public class RelatorioTrimestreDTO {
    
    private Integer trimestreId;
    private Integer ano;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private String status;
    private Integer totalChamadas;
    private Integer totalClasses;
    private Integer totalAlunos;
    private Integer mediaPresencas;
    private Integer totalAusentes;
    private Double percentualPresencaGeral;
    private List<RelatorioClasseDTO> classes;

    public RelatorioTrimestreDTO() {}

    // Getters e Setters
    public Integer getTrimestreId() {
        return trimestreId;
    }

    public void setTrimestreId(Integer trimestreId) {
        this.trimestreId = trimestreId;
    }

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDate getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDate dataFim) {
        this.dataFim = dataFim;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getTotalChamadas() {
        return totalChamadas;
    }

    public void setTotalChamadas(Integer totalChamadas) {
        this.totalChamadas = totalChamadas;
    }

    public Integer getTotalClasses() {
        return totalClasses;
    }

    public void setTotalClasses(Integer totalClasses) {
        this.totalClasses = totalClasses;
    }

    public Integer getTotalAlunos() {
        return totalAlunos;
    }

    public void setTotalAlunos(Integer totalAlunos) {
        this.totalAlunos = totalAlunos;
    }

    public Integer getMediaPresencas() {
        return mediaPresencas;
    }

    public void setMediaPresencas(Integer mediaPresencas) {
        this.mediaPresencas = mediaPresencas;
    }

    public Integer getTotalAusentes() {
        return totalAusentes;
    }

    public void setTotalAusentes(Integer totalAusentes) {
        this.totalAusentes = totalAusentes;
    }

    public Double getPercentualPresencaGeral() {
        return percentualPresencaGeral;
    }

    public void setPercentualPresencaGeral(Double percentualPresencaGeral) {
        this.percentualPresencaGeral = percentualPresencaGeral;
    }

    public List<RelatorioClasseDTO> getClasses() {
        return classes;
    }

    public void setClasses(List<RelatorioClasseDTO> classes) {
        this.classes = classes;
    }
}
