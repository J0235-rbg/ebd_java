package ebd.api_ebd.dto.relatorio;

public class RankingAlunoDTO {
    
    private Integer posicao;
    private Integer alunoId;
    private String nome;
    private String classe;
    private Integer pontuacao;
    private Integer totalPresencas;
    private Integer biblias;
    private Integer revistas;
    private Double percentualPresenca;

    public RankingAlunoDTO() {}

    public RankingAlunoDTO(Integer posicao, Integer alunoId, String nome, String classe,
                          Integer pontuacao, Integer totalPresencas, Integer biblias,
                          Integer revistas, Double percentualPresenca) {
        this.posicao = posicao;
        this.alunoId = alunoId;
        this.nome = nome;
        this.classe = classe;
        this.pontuacao = pontuacao;
        this.totalPresencas = totalPresencas;
        this.biblias = biblias;
        this.revistas = revistas;
        this.percentualPresenca = percentualPresenca;
    }

    // Getters e Setters
    public Integer getPosicao() {
        return posicao;
    }

    public void setPosicao(Integer posicao) {
        this.posicao = posicao;
    }

    public Integer getAlunoId() {
        return alunoId;
    }

    public void setAlunoId(Integer alunoId) {
        this.alunoId = alunoId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getClasse() {
        return classe;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    public Integer getPontuacao() {
        return pontuacao;
    }

    public void setPontuacao(Integer pontuacao) {
        this.pontuacao = pontuacao;
    }

    public Integer getTotalPresencas() {
        return totalPresencas;
    }

    public void setTotalPresencas(Integer totalPresencas) {
        this.totalPresencas = totalPresencas;
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

    public Double getPercentualPresenca() {
        return percentualPresenca;
    }

    public void setPercentualPresenca(Double percentualPresenca) {
        this.percentualPresenca = percentualPresenca;
    }
}
