package ebd.api_ebd.dto.relatorio;

public class RankingClasseDTO {
    
    private Integer posicao;
    private Integer classeId;
    private String nomeClasse;
    private String congregacao;
    private Integer pontuacao;
    private Integer totalAlunos;
    private Integer mediaPresencas;
    private Integer totalAusentes;
    private Double percentualPresenca;
    private Integer totalBiblias;
    private Integer totalRevistas;

    public RankingClasseDTO() {}

    // Getters e Setters
    public Integer getPosicao() {
        return posicao;
    }

    public void setPosicao(Integer posicao) {
        this.posicao = posicao;
    }

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

    public Integer getPontuacao() {
        return pontuacao;
    }

    public void setPontuacao(Integer pontuacao) {
        this.pontuacao = pontuacao;
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
}
