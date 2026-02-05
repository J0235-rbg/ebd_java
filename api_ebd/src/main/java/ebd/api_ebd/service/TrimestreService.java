package ebd.api_ebd.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import ebd.api_ebd.domain.entity.Chamada;
import ebd.api_ebd.domain.entity.Classe;
import ebd.api_ebd.domain.entity.Trim;
import ebd.api_ebd.domain.enums.TrimestreStatus;
import ebd.api_ebd.repository.*;
import jakarta.transaction.Transactional;


@Service
public class TrimestreService {
    // Serviço de Período

    private final TrimestreRepository trimestreRepository;
    private final ClasseRepository classeRepository;
    private final ChamadaRepository chamadaRepository;

    public TrimestreService(
        TrimestreRepository trimestreRepository,
        ClasseRepository classeRepository,
        ChamadaRepository chamadaRepository
    ) {
        this.trimestreRepository = trimestreRepository;
        this.classeRepository = classeRepository;
        this.chamadaRepository = chamadaRepository;
    }

    @Transactional
    public Trim criarTrim(UUID igrejaId, int ano, int trimestre){

        if(trimestreRepository.existByIgrejaIdAndStatus(igrejaId, TrimestreStatus.Aberto)){
            throw new IllegalStateException("Já existe um trimestre aberto");
        }

        LocalDate primeiroDomingo = calcularPrimeiroDomingo(ano, trimestre);

        LocalDate datainicio = primeiroDomingo;
        LocalDate dataFim = primeiroDomingo.plusWeeks(12);

        Trim trim = new Trim();
        trim.setIgreja(igrejaId);
        trim.setAno(ano);
        trim.setDataInicio(datainicio);
        trim.setDataFim(dataFim);
        trim.setStatus(TrimestreStatus.Aberto);

        return trimestreRepository.save(trim);
    }

    private LocalDate calcularPrimeiroDomingo(int ano, int trimestre) {

        int mesInicio;
        switch (trimestre) {
            case 1 -> mesInicio = 1;
            case 2 -> mesInicio = 4;
            case 3 -> mesInicio = 7;
            case 4 -> mesInicio = 10;
            default -> throw new IllegalArgumentException("Trimestre inválido");
            }

        LocalDate data = LocalDate.of(ano, mesInicio, 1);

        while (data.getDayOfWeek() != DayOfWeek.SUNDAY){
            data = data.plusDays(1);
        }

        return data;
        
    }

    @Transactional
    public void gerarChamadas(UUID trimId){
        Trim trim = trimestreRepository.findById(trimId)
            .orElseThrow();
        
        List<Classe> classes = 
            classeRepository.findByIgrejaIdAndAtivoTrue(trim.getIgreja());
            
        List<LocalDate> domingos = (List<LocalDate>) trim.getDataInicio()
            .datesUntil(trim.getDataFim().plusDays(1))
            .filter(d -> d.getDayOfWeek() == DayOfWeek.SUNDAY)
            .toList();
        
            for(Classe classe: classes) {
                for(LocalDate data : domingos){
                    if(data.isBefore(LocalDate.now())) continue;

                    boolean existe =
                        chamadaRepository.exitsByClasseIdAndDataAndTrimId(
                            classe.getId(), data, trim.getId()
                        );
                    
                    if(!existe){
                        chamadaRepository.save(
                            Chamada.nova(classe, trim, data)
                        );
                    }
                }
            }
    }

    @Transactional
    public void fecharTrim(UUID trimId){
        Trim trim = trimestreRepository.findById(trimId)
        .orElseThrow();

        if("Fechado".equals(trim.getStatus())) {
            throw new IllegalStateException("Trimestre já está fechado");
        }

        trim.setStatus(TrimestreStatus.Fechado);
        trimestreRepository.save(trim);
    }
}
