package ebd.api_ebd.dto.request;

import java.math.BigDecimal;

public record FechaChamadaRequest(
    Integer responsavelId,
    Integer visitantes,
    BigDecimal oferta
) {
    // Requisição para Fechar Atendimento
}
