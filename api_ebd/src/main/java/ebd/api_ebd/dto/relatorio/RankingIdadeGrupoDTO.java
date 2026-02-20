package ebd.api_ebd.dto.relatorio;

public class RankingIdadeGrupoDTO {
    private String grupo; // Criancas, Jovens, Adultos
    private int posicao;
    private int totalAlunos;
    private int totalPresencas;
    private int totalAusentes;
    private int totalBiblias;
    private int totalRevistas;
    private int chamadasRealizadas;
    private double percentualPresenca;
    private int pontuacao;

    // Construtor, Getters e Setters
    public RankingIdadeGrupoDTO(
        String grupo, 
        int totalAlunos, 
        int totalPresencas, 
        int totalAusentes,
        int totalBiblias, 
        int totalRevistas,
        int chamadasRealizadas, 
        double percentualPresenca, 
        int pontuacao) {
        this.grupo = grupo;
        this.totalAlunos = totalAlunos;
        this.totalPresencas = totalPresencas;
        this.totalAusentes = totalAusentes;
        this.totalBiblias = totalBiblias;
        this.totalRevistas = totalRevistas;
        this.chamadasRealizadas = chamadasRealizadas;
        this.percentualPresenca = percentualPresenca;
        this.pontuacao = pontuacao;
    }
    
    // Getters e Setters...
    public void setPosicao(int posicao) { this.posicao = posicao; }
    public int getPosicao() {return posicao;}
    public int getPontuacao() { return pontuacao; }
    public double getPercentualPresenca() { return percentualPresenca; }
    
    public String getGrupo() {return grupo;}

    public void setGrupo(String grupo) {this.grupo = grupo;}

    public int getTotalAlunos() {return totalAlunos;}

    public void setTotalAlunos(int totalAlunos) {this.totalAlunos = totalAlunos;}

    public int getTotalPresencas() {return totalPresencas;}

    public void setTotalPresencas(int totalPresencas) {this.totalPresencas = totalPresencas;}

    public int getTotalAusentes() {return totalAusentes;}
    public void setTotalAusentes(int totalAusentes) {this.totalAusentes = totalAusentes;}

    public int getTotalBiblias() {return totalBiblias;}
    public void setTotalBiblias(int totalBiblias) {this.totalBiblias = totalBiblias;}
    public int getTotalRevistas() { return totalRevistas;}
    public void setTotalRevistas(int totalRevistas) {this.totalRevistas = totalRevistas;}
    public int getChamadasRealizadas(){return chamadasRealizadas;}
    public void setChamadasRealizadas(int chamadasRealizadas){ this.chamadasRealizadas = chamadasRealizadas; }

}
