package ebd.api_ebd.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import ebd.api_ebd.domain.entity.Chamada;
import ebd.api_ebd.domain.entity.Trim;
import ebd.api_ebd.domain.enums.ChamadaStatus;
import ebd.api_ebd.domain.enums.TrimestreStatus;
import ebd.api_ebd.repository.ChamadaRepository;
import ebd.api_ebd.repository.TrimestreRepository;
import jakarta.transaction.Transactional;

@Service
public class ChamadaService {
    // Serviço de Atendimento

    private final RegistroChamadaService registroChamadaService;
    private final ChamadaRepository chamadaRepository;
    private final TrimestreRepository trimestreRepository;

    public ChamadaService(
        RegistroChamadaService registroChamadaService,
        ChamadaRepository chamadaRepository,
        TrimestreRepository trimestreRepository
    ){
        this.registroChamadaService = registroChamadaService;
        this.chamadaRepository = chamadaRepository;
        this.trimestreRepository = trimestreRepository;
    }

    public List<Chamada> buscarChamadasDoDia(Integer igrejaId, Integer trimId){

        LocalDate hoje = LocalDate.now();

        List<Chamada> chamadas = 
            chamadaRepository
                .findByIgrejaAndTrimAndDataGreaterThanEqualAndStatusOrderByDataAsc(igrejaId, trimId, hoje, ChamadaStatus.Aberto);

        return chamadas.stream()
            .filter(c -> c.getData().getDayOfWeek() == DayOfWeek.SUNDAY)
            .toList();
    }

    @Transactional
    public Chamada abrirChamada(
        Integer classeId,
        LocalDate data,
        Integer igrejaId
    ){
        Trim trim = trimestreRepository
            .findByIgrejaAndStatus(igrejaId, TrimestreStatus.Aberto)
            .orElseThrow(() ->
                new IllegalStateException("Nenhum trimestre aberto")        
        );

        Chamada chamada = chamadaRepository 
            .findByClasseAndDataAndTrim(classeId, data, trim.getId())
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
    public void fecharChamada(Integer chamadaId) {
        Chamada chamada = chamadaRepository.findById(chamadaId)
            .orElseThrow();
        
        if(!"Aberto".equals(chamada.getStatus())){
            throw new IllegalStateException("Chamada não está aberta");
        }

        chamada.setStatus(ChamadaStatus.Fechado);
        chamadaRepository.save(chamada);
    }

    public boolean podeAbrir(Integer classeId, LocalDate data, Integer igrejaId){
        return trimestreRepository
            .findByIgrejaAndStatus(igrejaId, TrimestreStatus.Aberto)
            .filter( t-> 
                !data.isAfter(LocalDate.now()) && 
                chamadaRepository.existsByClasseIdAndDataAndTrimId(classeId, data, t.getId())
                
            ).isPresent();
    }

    @Transactional
    public void fecharChamada(Integer chamadaId, Integer responsavelId){
        registroChamadaService.consolidarDados(chamadaId, responsavelId);

        Chamada chamada = chamadaRepository.findById(chamadaId)
            .orElseThrow();

        chamada.setStatus(ChamadaStatus.Fechado);
        chamadaRepository.save(chamada);
    }
}
