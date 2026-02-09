package ebd.api_ebd.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import ebd.api_ebd.domain.entity.Chamada;
import ebd.api_ebd.domain.entity.Trim;
import ebd.api_ebd.dto.relatorio.RelatorioClasseDTO;
import ebd.api_ebd.dto.relatorio.RelatorioTrimestreDTO;
import ebd.api_ebd.exception.NotFoundException;
import ebd.api_ebd.repository.ChamadaRepository;
import ebd.api_ebd.repository.TrimestreRepository;

@Service
public class RelatorioTrimestreService {

    private final TrimestreRepository trimestreRepository;
    private final ChamadaRepository chamadaRepository;
    private final RelatorioClasseService relatorioClasseService;

    public RelatorioTrimestreService(
            TrimestreRepository trimestreRepository,
            ChamadaRepository chamadaRepository,
            RelatorioClasseService relatorioClasseService) {
        this.trimestreRepository = trimestreRepository;
        this.chamadaRepository = chamadaRepository;
        this.relatorioClasseService = relatorioClasseService;
    }

    public RelatorioTrimestreDTO gerarRelatorioTrimestre(Integer trimestreId) {
        Trim trimestre = trimestreRepository.findById(trimestreId)
                .orElseThrow(() -> new NotFoundException("Trimestre não encontrado"));

        List<Chamada> chamadas = chamadaRepository.findByTrimestre(trimestreId);
        
        // Buscar todas as classes que tiveram chamadas neste trimestre
        List<Integer> classesIds = chamadas.stream()
                .map(Chamada::getClasse)
                .distinct()
                .toList();

        List<RelatorioClasseDTO> relatoriosClasses = new ArrayList<>();
        int totalPresencas = 0;
        int totalAlunos = 0;

        for (Integer classeId : classesIds) {
            RelatorioClasseDTO relatorioClasse = relatorioClasseService.gerarRelatorioClasse(classeId, trimestreId);
            relatoriosClasses.add(relatorioClasse);
            totalPresencas += relatorioClasse.getMediaPresencas();
            totalAlunos += relatorioClasse.getTotalAlunos();
        }

        int mediaPresencas = !relatoriosClasses.isEmpty() 
                ? totalPresencas / relatoriosClasses.size() 
                : 0;

        double percentualPresencaGeral = !relatoriosClasses.isEmpty()
                ? relatoriosClasses.stream()
                    .mapToDouble(RelatorioClasseDTO::getPercentualPresenca)
                    .average()
                    .orElse(0.0)
                : 0.0;

        RelatorioTrimestreDTO relatorio = new RelatorioTrimestreDTO();
        relatorio.setTrimestreId(trimestre.getId());
        relatorio.setAno(trimestre.getAno());
        relatorio.setDataInicio(trimestre.getDataInicio());
        relatorio.setDataFim(trimestre.getDataFim());
        relatorio.setStatus(trimestre.getStatus() != null ? trimestre.getStatus().toString() : "N/A");
        relatorio.setTotalChamadas(chamadas.size());
        relatorio.setTotalClasses(classesIds.size());
        relatorio.setTotalAlunos(totalAlunos);
        relatorio.setMediaPresencas(mediaPresencas);
        relatorio.setPercentualPresencaGeral(percentualPresencaGeral);
        relatorio.setClasses(relatoriosClasses);

        return relatorio;
    }

    public List<RelatorioTrimestreDTO> listarRelatoriosTrimestres(Integer igrejaId, Integer ano) {
        List<Trim> trimestres;
        
        if (igrejaId != null && ano != null) {
            trimestres = trimestreRepository.findByIgrejaIdAndAno(igrejaId, ano);
        } else if (igrejaId != null) {
            trimestres = trimestreRepository.findByIgrejaId(igrejaId);
        } else if (ano != null) {
            trimestres = trimestreRepository.findByAno(ano);
        } else {
            trimestres = trimestreRepository.findAll();
        }

        List<RelatorioTrimestreDTO> relatorios = new ArrayList<>();
        
        for (Trim trimestre : trimestres) {
            RelatorioTrimestreDTO relatorio = gerarRelatorioTrimestre(trimestre.getId());
            relatorios.add(relatorio);
        }

        return relatorios;
    }
}
