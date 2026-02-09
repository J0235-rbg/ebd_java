package ebd.api_ebd.dto.relatorio;

public class AlunoInfo {
    
    private Integer id;
    private String nome;
    private Integer congregacao;
    private Integer classe;
    private boolean isDependente;

    public AlunoInfo(Integer id, String nome, Integer congregacao, Integer classe, boolean isDependente) {
        this.id = id;
        this.nome = nome;
        this.congregacao = congregacao;
        this.classe = classe;
        this.isDependente = isDependente;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getCongregacao() {
        return congregacao;
    }

    public void setCongregacao(Integer congregacao) {
        this.congregacao = congregacao;
    }

    public Integer getClasse() {
        return classe;
    }

    public void setClasse(Integer classe) {
        this.classe = classe;
    }

    public boolean isDependente() {
        return isDependente;
    }

    public void setDependente(boolean dependente) {
        isDependente = dependente;
    }
}
