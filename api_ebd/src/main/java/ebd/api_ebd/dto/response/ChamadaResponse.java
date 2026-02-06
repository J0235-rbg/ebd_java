package ebd.api_ebd.dto.response;

import java.time.LocalDate;

import ebd.api_ebd.domain.enums.ChamadaStatus;

public record ChamadaResponse(
    Long id,
    LocalDate data,
    ChamadaStatus status,
    Long classeId
) {

   
    // Resposta de Atendimento
}
