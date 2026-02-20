package ebd.api_ebd.dto.request;

public record RegistroChamadaDTO(
    Integer alunoId,
    int status,
    int biblia,
    int revista
) {
    
}
