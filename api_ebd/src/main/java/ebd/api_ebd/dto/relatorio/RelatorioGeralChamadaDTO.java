package ebd.api_ebd.dto.relatorio;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class RelatorioGeralChamadaDTO {
    private LocalDate data;
    private List<RelatorioClasseChamadaDTO> classes;
    private Integer totalMatriculados;
    private Integer totalClasses;
    private Integer totalPresentes;
    private Integer totalAusentes;
    private Integer totalVisitantes;
    private BigDecimal totalOferta;
    private Integer totalBiblias;
    private Integer totalRevistas;

    public RelatorioGeralChamadaDTO() {}

    public RelatorioGeralChamadaDTO(
        LocalDate data,
        List<RelatorioClasseChamadaDTO> classes,
        Integer totalMatriculados,
        Integer totalClasses,
        Integer totalPresentes,
        Integer totalAusentes,
        Integer totalVisitantes,
        BigDecimal totalOferta,
        Integer totalBiblias,
        Integer totalRevistas
    ) {
        this.data = data;
        this.classes = classes;
        this.totalMatriculados = totalMatriculados;
        this.totalClasses = totalClasses;
        this.totalPresentes = totalPresentes;
        this.totalAusentes = totalAusentes;
        this.totalVisitantes = totalVisitantes;
        this.totalOferta = totalOferta;
        this.totalBiblias = totalBiblias;
        this.totalRevistas = totalRevistas;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public List<RelatorioClasseChamadaDTO> getClasses() {
        return classes;
    }

    public void setClasses(List<RelatorioClasseChamadaDTO> classes) {
        this.classes = classes;
    }

    public Integer getTotalMatriculados() {
        return totalMatriculados;
    }

    public void setTotalMatriculados(Integer totalMatriculados) {
        this.totalMatriculados = totalMatriculados;
    }

    public Integer getTotalClasses() {
        return totalClasses;
    }

    public void setTotalClasses(Integer totalClasses) {
        this.totalClasses = totalClasses;
    }

    public Integer getTotalPresentes() {
        return totalPresentes;
    }

    public void setTotalPresentes(Integer totalPresentes) {
        this.totalPresentes = totalPresentes;
    }

    public Integer getTotalAusentes() {
        return totalAusentes;
    }

    public void setTotalAusentes(Integer totalAusentes) {
        this.totalAusentes = totalAusentes;
    }

    public Integer getTotalVisitantes() {
        return totalVisitantes;
    }

    public void setTotalVisitantes(Integer totalVisitantes) {
        this.totalVisitantes = totalVisitantes;
    }

    public BigDecimal getTotalOferta() {
        return totalOferta;
    }

    public void setTotalOferta(BigDecimal totalOferta) {
        this.totalOferta = totalOferta;
    }

    public Integer getTotalBiblias() {
        return totalBiblias;
    }

    public void setTotalBiblias(Integer totalBiblias) {
        this.totalBiblias = totalBiblias;
    }

    public Integer getTotalRevistas() {
        return totalRevistas;
    }

    public void setTotalRevistas(Integer totalRevistas) {
        this.totalRevistas = totalRevistas;
    }
}
