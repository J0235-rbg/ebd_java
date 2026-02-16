package ebd.api_ebd.dto.relatorio;

import java.util.List;

public class RelatorioClasseDTO {
    
    private Integer classeId;
    private String nomeClasse;
    private String congregacao;
    private String professorPrincipal;
    private Integer totalAlunos;
    private Integer totalChamadas;
    private Integer mediaPresencas;
    private Integer mediaFaltas;
    private Integer totalAusentes;
    private Double percentualPresenca;
    private Integer totalBiblias;
    private Integer totalRevistas;
    private List<RelatorioAlunoDTO> alunos;

    public RelatorioClasseDTO() {}

    // Getters e Setters
    public Integer getClasseId() {
        return classeId;
    }

    public void setClasseId(Integer classeId) {
        this.classeId = classeId;
    }

    public String getNomeClasse() {
        return nomeClasse;
    }

    public void setNomeClasse(String nomeClasse) {
        this.nomeClasse = nomeClasse;
    }

    public String getCongregacao() {
        return congregacao;
    }

    public void setCongregacao(String congregacao) {
        this.congregacao = congregacao;
    }

    public String getProfessorPrincipal() {
        return professorPrincipal;
    }

    public void setProfessorPrincipal(String professorPrincipal) {
        this.professorPrincipal = professorPrincipal;
    }

    public Integer getTotalAlunos() {
        return totalAlunos;
    }

    public void setTotalAlunos(Integer totalAlunos) {
        this.totalAlunos = totalAlunos;
    }

    public Integer getTotalChamadas() {
        return totalChamadas;
    }

    public void setTotalChamadas(Integer totalChamadas) {
        this.totalChamadas = totalChamadas;
    }

    public Integer getMediaPresencas() {
        return mediaPresencas;
    }

    public void setMediaPresencas(Integer mediaPresencas) {
        this.mediaPresencas = mediaPresencas;
    }

    public Integer getMediaFaltas() {
        return mediaFaltas;
    }

    public void setMediaFaltas(Integer mediaFaltas) {
        this.mediaFaltas = mediaFaltas;
    }

    public Integer getTotalAusentes() {
        return totalAusentes;
    }

    public void setTotalAusentes(Integer totalAusentes) {
        this.totalAusentes = totalAusentes;
    }

    public Double getPercentualPresenca() {
        return percentualPresenca;
    }

    public void setPercentualPresenca(Double percentualPresenca) {
        this.percentualPresenca = percentualPresenca;
    }

    public Integer getTotalBiblias() {
        return totalBiblias;
    }

    public void setTotalBiblias(Integer totalBiblias) {
        this.totalBiblias = totalBiblias;
    }

    public Integer getTotalRevistas() {
        return totalRevistas;
    }

    public void setTotalRevistas(Integer totalRevistas) {
        this.totalRevistas = totalRevistas;
    }

    public List<RelatorioAlunoDTO> getAlunos() {
        return alunos;
    }

    public void setAlunos(List<RelatorioAlunoDTO> alunos) {
        this.alunos = alunos;
    }
}
