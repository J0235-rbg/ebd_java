package ebd.api_ebd.dto.response;

public class LoginResponse {
    private String nome;
    private Integer igrejaId;
    private Integer pessoaId;
    private Integer cargo;
    private boolean admin;
    private boolean podeRelatorio;

    
	public LoginResponse(String nome, Integer igrejaId, Integer pessoaId, Integer cargo, boolean admin, boolean podeRelatorio) {
		this.nome = nome;
		this.igrejaId = igrejaId;
		this.pessoaId = pessoaId;
        this.cargo = cargo;
		this.admin = admin;
		this.podeRelatorio = podeRelatorio;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Integer getIgrejaId() {
		return igrejaId;
	}
	public void setIgrejaId(Integer igrejaId) {
		this.igrejaId = igrejaId;
	}
	public Integer getPessoaId() {
		return pessoaId;
	}
	public void setPessoaId(Integer pessoaId) {
		this.pessoaId = pessoaId;
	}
    public Integer getCargo(){
        return cargo;
    }
    public void serCargo(Integer cargo){
        this.cargo = cargo;
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
}
