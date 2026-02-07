package ebd.api_ebd.dto.response;

import ebd.api_ebd.domain.entity.RegistroChamada;

public record RegistroChamadaResponse(Integer chamadaId, Integer alunoId, Integer biblia, Integer revista, Integer status) {

    public RegistroChamadaResponse(RegistroChamada entity){
        this(
            entity.getChamada(), 
            entity.getaluno(), 
            entity.getBiblia(), 
            entity.getRevista(), 
            entity.getStatus());
        
    }
    
}
