package ebd.api_ebd.dto.request;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RegistraPresencaRequest {
    @JsonProperty("chamadaId")
    private Integer chamadaId;
    
    @JsonProperty("registros")
    private List<RegistroChamadaDTO> registros;

    public RegistraPresencaRequest() {}

   public RegistraPresencaRequest(Integer chamadaId,  List<RegistroChamadaDTO> registros) {
        this.chamadaId = chamadaId;
        this.registros = registros;
    }

    // Getters e Setters padrão
    public Integer getChamadaId() { return chamadaId; }
    public void setChamadaId(Integer chamadaId) { this.chamadaId = chamadaId; }
    public List<RegistroChamadaDTO> getRegistros() { return registros; }
    public void setRegistros(List<RegistroChamadaDTO> registros) { this.registros = registros; }
}
