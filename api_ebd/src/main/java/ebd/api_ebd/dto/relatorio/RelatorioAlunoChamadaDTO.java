package ebd.api_ebd.dto.relatorio;

public class RelatorioAlunoChamadaDTO{

    
    private Integer alunoId;
    private String nome;
    private Integer status;
    private Integer biblia;
    private Integer revista;

    public Integer getAlunoId() {
        return alunoId;
    }

    public String getNome() {
        return nome;
    }

    public Integer getStatus() {
        return status;
    }

    public Integer getBiblia() {
        return biblia;
    }

    public Integer getRevista() {
        return revista;
    }

    public RelatorioAlunoChamadaDTO(
        Integer alunoId,
        String nome,
        Integer status,
        Integer biblia,
        Integer revista
    ) {
        this.alunoId = alunoId;
        this.nome = nome;
        this.status = status;
        this.biblia = biblia;
        this.revista = revista;
    }
}
