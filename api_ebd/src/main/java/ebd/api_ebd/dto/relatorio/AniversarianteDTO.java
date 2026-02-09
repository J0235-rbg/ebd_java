package ebd.api_ebd.dto.relatorio;

import java.time.LocalDate;

public class AniversarianteDTO {
    
    private Integer pessoaId;
    private String nome;
    private LocalDate dataNascimento;
    private Integer idade;
    private String telefone;
    private String congregacao;
    private String classe;
    private Integer diaAniversario;
    private Integer mesAniversario;

    public AniversarianteDTO() {}

    public AniversarianteDTO(Integer pessoaId, String nome, LocalDate dataNascimento, 
                            Integer idade, String telefone, String congregacao, 
                            String classe, Integer diaAniversario, Integer mesAniversario) {
        this.pessoaId = pessoaId;
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.idade = idade;
        this.telefone = telefone;
        this.congregacao = congregacao;
        this.classe = classe;
        this.diaAniversario = diaAniversario;
        this.mesAniversario = mesAniversario;
    }

    // Getters e Setters
    public Integer getPessoaId() {
        return pessoaId;
    }

    public void setPessoaId(Integer pessoaId) {
        this.pessoaId = pessoaId;
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

    public Integer getIdade() {
        return idade;
    }

    public void setIdade(Integer idade) {
        this.idade = idade;
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

    public Integer getDiaAniversario() {
        return diaAniversario;
    }

    public void setDiaAniversario(Integer diaAniversario) {
        this.diaAniversario = diaAniversario;
    }

    public Integer getMesAniversario() {
        return mesAniversario;
    }

    public void setMesAniversario(Integer mesAniversario) {
        this.mesAniversario = mesAniversario;
    }
}
