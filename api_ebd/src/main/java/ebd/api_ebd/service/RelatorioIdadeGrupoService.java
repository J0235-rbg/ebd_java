package ebd.api_ebd.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import ebd.api_ebd.domain.entity.Chamada;
import ebd.api_ebd.domain.entity.Classe;
import ebd.api_ebd.domain.entity.RegistroChamada;
import ebd.api_ebd.domain.enums.ChamadaStatus;
import ebd.api_ebd.domain.enums.IdadeGrupo;
import ebd.api_ebd.dto.relatorio.RankingIdadeGrupoDTO;
import ebd.api_ebd.repository.AlunoDependenteRepository;
import ebd.api_ebd.repository.AlunoResponsavelRepository;
import ebd.api_ebd.repository.ChamadaRepository;
import ebd.api_ebd.repository.ClasseRepository;
import ebd.api_ebd.repository.RegistroChamadaRepository;

@Service
public class RelatorioIdadeGrupoService {
    private final ClasseRepository classeRepository;
    private final ChamadaRepository chamadaRepository;
    private final RegistroChamadaRepository registroChamadaRepository;
    private final AlunoDependenteRepository alunoDependenteRepository;
    private final AlunoResponsavelRepository alunoResponsavelRepository;

    // Construtor...

    public RelatorioIdadeGrupoService(
        ClasseRepository classeRepository, 
        ChamadaRepository chamadaRepository,
        RegistroChamadaRepository registroChamadaRepository, 
        AlunoDependenteRepository alunoDependenteRepository,
        AlunoResponsavelRepository alunoResponsavelRepository) {
        this.classeRepository = classeRepository;
        this.chamadaRepository = chamadaRepository;
        this.registroChamadaRepository = registroChamadaRepository;
        this.alunoDependenteRepository = alunoDependenteRepository;
        this.alunoResponsavelRepository = alunoResponsavelRepository;
    }

    public List<RankingIdadeGrupoDTO> gerarRankingPorIdadeGrupo(Integer trimestreId) {
        List<Chamada> chamadas = chamadaRepository.findByTrimAndStatus(trimestreId, ChamadaStatus.Fechado);
        return processarLogicaGrupo(chamadas);
    }

    public List<RankingIdadeGrupoDTO> gerarRankingAnualPorIdadeGrupo(int ano) {
        List<Chamada> chamadasAno = chamadaRepository.findAll().stream()
                .filter(c -> c.getStatus() == ChamadaStatus.Fechado)
                .filter(c -> c.getData().getYear() == ano)
                .toList();
        return processarLogicaGrupo(chamadasAno);
    }

    private List<RankingIdadeGrupoDTO> processarLogicaGrupo(List<Chamada> chamadas) {
        if (chamadas.isEmpty()) return new ArrayList<>();

        List<Integer> idsChamadas = chamadas.stream().map(Chamada::getId).toList();
        List<RegistroChamada> todosRegistros = registroChamadaRepository.findByChamadaIn(idsChamadas);
        
        List<Classe> todasClasses = classeRepository.findAll();
        Map<IdadeGrupo, List<Classe>> classesPorGrupo = todasClasses.stream()
                .filter(c -> c.getFaixIdadeGrupo() != null)
                .collect(Collectors.groupingBy(Classe::getFaixIdadeGrupo));

        List<RankingIdadeGrupoDTO> ranking = new ArrayList<>();

        classesPorGrupo.forEach((grupo, listaClasses) -> {
            int totalAlunosAtivos = 0;
            int presencasTotal = 0;
            int bibliasTotal = 0;
            int revistasTotal = 0;
            int totalChamadasGrupo = 0;
            long presencasPossiveis = 0;

            for (Classe classe : listaClasses) {
                List<Chamada> chClasse = chamadas.stream().filter(c -> c.getClasse().equals(classe.getId())).toList();
                if (chClasse.isEmpty()) continue;

                totalChamadasGrupo += chClasse.size();
                
                
                int ativos = (int) (alunoDependenteRepository.findByClasse(classe.getId()).stream().filter(d -> d.getStatus() == 1).count() +
                                   alunoResponsavelRepository.findByClasse(classe.getId()).stream().filter(r -> r.getStatus() == 1).count());
                
                totalAlunosAtivos += ativos;
                presencasPossiveis += (long) ativos * chClasse.size();

                
                List<Integer> idsCh = chClasse.stream().map(Chamada::getId).toList();
                List<RegistroChamada> regs = todosRegistros.stream().filter(r -> idsCh.contains(r.getChamada())).toList();
                
                presencasTotal += (int) regs.stream().filter(r -> r.getStatus() == 1).count();
                bibliasTotal += regs.stream().mapToInt(r -> r.getBiblia() != null ? r.getBiblia() : 0).sum();
                revistasTotal += regs.stream().mapToInt(r -> r.getRevista() != null ? r.getRevista() : 0).sum();
            }

            if (totalAlunosAtivos > 0) {
                int ausentesTotal = (int) presencasPossiveis - presencasTotal;
                double percentual = (presencasTotal * 100.0) / presencasPossiveis;

                ranking.add(new RankingIdadeGrupoDTO(
                    grupo.name(), totalAlunosAtivos, presencasTotal, ausentesTotal,
                    bibliasTotal, revistasTotal, totalChamadasGrupo, percentual, 
                    (presencasTotal + bibliasTotal + revistasTotal)
                ));
            }
        });

        ranking.sort(Comparator.comparing(RankingIdadeGrupoDTO::getPercentualPresenca).reversed());
        for (int i = 0; i < ranking.size(); i++) ranking.get(i).setPosicao(i + 1);

        return ranking;
    }
}
