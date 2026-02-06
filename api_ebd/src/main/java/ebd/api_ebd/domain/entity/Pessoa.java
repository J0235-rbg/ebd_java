package ebd.api_ebd.domain.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "pessoa")
public class Pessoa {
    // Entidade Pessoa

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    private String nome;
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    private String telefone;
    public String getTelefone() {
        return telefone;
    }
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
    private LocalDate dt_nascimento;

    public LocalDate getDt_nascimento() {
        return dt_nascimento;
    }
    public void setDt_nascimento(LocalDate dt_nascimento) {
        this.dt_nascimento = dt_nascimento;
    }
    private String endereco;
    private String numero;
    private String bairro;

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
    @Column(name = "id_cargo", nullable = false)
    private Integer cargo;

    @Column(name = "id_congregacao", nullable = false)
    private Integer congregacao;

    private String login;
    private String keyApp;

    private boolean admin;
    private boolean podeRelatorio;
    private boolean ativo;

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
    public String getLogin() {
        return login;
    }
    public void setLogin(String login) {
        this.login = login;
    }
    public String getKeyApp() {
        return keyApp;
    }
    public void setKeyApp(String keyApp) {
        this.keyApp = keyApp;
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
