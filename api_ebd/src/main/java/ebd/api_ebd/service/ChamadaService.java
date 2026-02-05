package ebd.api_ebd.service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import ebd.api_ebd.domain.entity.Chamada;
import ebd.api_ebd.domain.entity.Trim;
import ebd.api_ebd.domain.enums.ChamadaStatus;
import ebd.api_ebd.domain.enums.TrimestreStatus;
import ebd.api_ebd.repository.ChamadaRepository;
import ebd.api_ebd.repository.ClasseRepository;
import ebd.api_ebd.repository.TrimestreRepository;
import jakarta.transaction.Transactional;

@Service
public class ChamadaService {
    // Serviço de Atendimento

    private final RegistroChamadaService registroChamadaService;
    private final ChamadaRepository chamadaRepository;
    private final TrimestreRepository trimestreRepository;
    private final ClasseRepository classeRepository;

    public ChamadaService(
        RegistroChamadaService registroChamadaService,
        ChamadaRepository chamadaRepository,
        TrimestreRepository trimestreRepository,
        ClasseRepository classeRepository
    ){
        this.registroChamadaService = registroChamadaService;
        this.chamadaRepository = chamadaRepository;
        this.trimestreRepository = trimestreRepository;
        this.classeRepository = classeRepository;
    }

    public List<Chamada> buscarChamadasDoDia(UUID igrejaId){
        return chamadaRepository.findChamadasAbertasDoDia(igrejaId, LocalDate.now());
    }

    @Transactional
    public Chamada abrirChamada(
        UUID classeId,
        LocalDate data,
        UUID igrejaId
    ){
        Trim trim = trimestreRepository
            .findByIgrejaAndStatus(igrejaId, TrimestreStatus.Aberto)
            .orElseThrow(() ->
                new IllegalStateException("Nenhum trimestre aberto")        
        );

        Chamada chamada = chamadaRepository 
            .findByClasseIdAndDataAndTrimId(classeId, data, trim.getId())
            .orElseThrow(() -> 
                new IllegalStateException("Chamada não encontrada")
        );

        if(chamada.getData().isAfter(LocalDate.now())) {
            throw new IllegalStateException("Chamada futura não pode ser aberta");
        }

        chamada.setStatus(ChamadaStatus.Aberto);
        return chamadaRepository.save(chamada);
    }

    @Transactional
    public void fecharChamada(UUID chamadaId) {
        Chamada chamada = chamadaRepository.findById(chamadaId)
            .orElseThrow();
        
        if(!"Aberto".equals(chamada.getStatus())){
            throw new IllegalStateException("Chamada não está aberta");
        }

        chamada.setStatus(ChamadaStatus.Fechado);
        chamadaRepository.save(chamada);
    }

    public boolean podeAbrir(UUID classeId, LocalDate data, UUID igrejaId){
        return trimestreRepository
            .findByIgrejaAndStatus(igrejaId, TrimestreStatus.Aberto)
            .filter( t-> 
                !data.isAfter(LocalDate.now()) && 
                chamadaRepository.exitsByClasseIdAndDataAndTrimId(classeId, data, t.getId())
                
            ).isPresent();
    }

    @Transactional
    public void fecharChamada(UUID chamadaId, UUID responsavelId){
        registroChamadaService.consolidarDados(chamadaId, responsavelId);

        Chamada chamada = chamadaRepository.findById(chamadaId)
            .orElseThrow();

        chamada.setStatus(ChamadaStatus.Fechado);
        chamadaRepository.save(chamada);
    }
}
