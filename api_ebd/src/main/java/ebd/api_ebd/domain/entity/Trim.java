package ebd.api_ebd.domain.entity;

import java.time.LocalDate;
import java.util.UUID;

import ebd.api_ebd.domain.enums.TrimestreStatus;
import jakarta.persistence.*;
@Entity
@Table(name = "trim")
public class Trim {
    // Entidade Período

    @Id
    @GeneratedValue
    private UUID id;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @Column(name = "id_igreja", nullable = false)
    private UUID igreja;



    private Integer ano;
   

    private LocalDate dataInicio;
    

    private LocalDate dataFim;

    

    @Enumerated(EnumType.STRING)
    private TrimestreStatus status;

    public TrimestreStatus getStatus() {
        return status;
    }

    public void setStatus(TrimestreStatus status) {
        this.status = status;
    }

    public UUID getIgreja() {
        return igreja;
    }

    public void setIgreja(UUID igrejaId) {
        this.igreja = igrejaId;
    }

     public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDate getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDate dataFim) {
        this.dataFim = dataFim;
    }
    
}
