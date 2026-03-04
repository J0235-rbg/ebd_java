package ebd.api_ebd.dto.request;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ChamadaDadosAdicionaisDTO {
    @JsonProperty("chamadaId")
    private Integer chamadaId;

    @JsonProperty("visitantes")
    private Integer visitantes;
    @JsonProperty("oferta")
    private BigDecimal oferta;
    @JsonProperty("professorId")
    private Integer professorId;
    @JsonProperty("auxiliaresIds")
    private List<Integer> auxiliaresIds;


    
	public ChamadaDadosAdicionaisDTO(){}

	public ChamadaDadosAdicionaisDTO(Integer chamadaId, Integer visitantes, BigDecimal oferta, Integer professorId, List<Integer> auxiliaresIds) {
		this.chamadaId = chamadaId;
		this.visitantes = visitantes;
		this.oferta = oferta;
		this.professorId = professorId;
        this.auxiliaresIds = auxiliaresIds;
	}

	public Integer getChamadaId() {
		return chamadaId;
	}
	public void setChamadaId(Integer chamadaId) {
		this.chamadaId = chamadaId;
	}
	public Integer getVisitantes() {
		return visitantes;
	}
	public void setVisitantes(Integer visitantes) {
		this.visitantes = visitantes;
	}
	public BigDecimal getOferta() {
		return oferta;
	}
	public void setOferta(BigDecimal oferta) {
		this.oferta = oferta;
	}
	public Integer getProfessorId() {
		return professorId;
	}
	public void setProfessorId(Integer professorId) {
		this.professorId = professorId;
	}

    public List<Integer> getAuxiliaresIds() {
		return auxiliaresIds;
	}

	public void setAuxiliaresIds(List<Integer> auxiliaresIds) {
		this.auxiliaresIds = auxiliaresIds;
	}
}
