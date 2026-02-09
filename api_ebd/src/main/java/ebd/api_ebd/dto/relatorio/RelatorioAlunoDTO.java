package ebd.api_ebd.dto.relatorio;

import java.time.LocalDate;

public class RelatorioAlunoDTO {
    
    private Integer alunoId;
    private String nome;
    private LocalDate dataNascimento;
    private String telefone;
    private String congregacao;
    private String classe;
    private Integer totalPresencas;
    private Integer totalFaltas;
    private Integer totalChamadas;
    private Double percentualPresenca;
    private Integer biblias;
    private Integer revistas;

    public RelatorioAlunoDTO() {}

    public RelatorioAlunoDTO(Integer alunoId, String nome, LocalDate dataNascimento, 
                            String telefone, String congregacao, String classe,
                            Integer totalPresencas, Integer totalFaltas, Integer totalChamadas,
                            Double percentualPresenca, Integer biblias, Integer revistas) {
        this.alunoId = alunoId;
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.telefone = telefone;
        this.congregacao = congregacao;
        this.classe = classe;
        this.totalPresencas = totalPresencas;
        this.totalFaltas = totalFaltas;
        this.totalChamadas = totalChamadas;
        this.percentualPresenca = percentualPresenca;
        this.biblias = biblias;
        this.revistas = revistas;
    }

    // Getters e Setters
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

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCongregacao() {
        return congregacao;
    }

    public void setCongregacao(String congregacao) {
        this.congregacao = congregacao;
    }

    public String getClasse() {
        return classe;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    public Integer getTotalPresencas() {
        return totalPresencas;
    }

    public void setTotalPresencas(Integer totalPresencas) {
        this.totalPresencas = totalPresencas;
    }

    public Integer getTotalFaltas() {
        return totalFaltas;
    }

    public void setTotalFaltas(Integer totalFaltas) {
        this.totalFaltas = totalFaltas;
    }

    public Integer getTotalChamadas() {
        return totalChamadas;
    }

    public void setTotalChamadas(Integer totalChamadas) {
        this.totalChamadas = totalChamadas;
    }

    public Double getPercentualPresenca() {
        return percentualPresenca;
    }

    public void setPercentualPresenca(Double percentualPresenca) {
        this.percentualPresenca = percentualPresenca;
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
}
