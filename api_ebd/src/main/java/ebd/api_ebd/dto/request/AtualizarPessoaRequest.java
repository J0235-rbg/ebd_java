package ebd.api_ebd.dto.request;

import java.time.LocalDate;

public class AtualizarPessoaRequest {
    
    private String nome;
    private String telefone;
    private LocalDate dt_nascimento;
    private String endereco;
    private String numero;
    private String bairro;
    private Integer cargo;
    private Integer congregacao;
    private Integer igreja;
    private String login;
    private boolean admin;
    private boolean podeRelatorio;
    private boolean ativo;

    public AtualizarPessoaRequest() {}

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public LocalDate getDt_nascimento() {
        return dt_nascimento;
    }

    public void setDt_nascimento(LocalDate dt_nascimento) {
        this.dt_nascimento = dt_nascimento;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public Integer getCargo() {
        return cargo;
    }

    public void setCargo(Integer cargo) {
        this.cargo = cargo;
    }

    public Integer getCongregacao() {
        return congregacao;
    }

    public void setCongregacao(Integer congregacao) {
        this.congregacao = congregacao;
    }

    public Integer getIgreja() {
        return igreja;
    }

    public void setIgreja(Integer igreja) {
        this.igreja = igreja;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public boolean isPodeRelatorio() {
        return podeRelatorio;
    }

    public void setPodeRelatorio(boolean podeRelatorio) {
        this.podeRelatorio = podeRelatorio;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
}
