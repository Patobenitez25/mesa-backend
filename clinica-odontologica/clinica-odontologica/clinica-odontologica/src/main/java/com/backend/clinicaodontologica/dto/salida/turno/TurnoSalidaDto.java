package com.backend.clinicaodontologica.dto.salida.turno;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class TurnoSalidaDto {

    private Long id;
    private Long idPaciente;
    private Long idOdontologo;
    private LocalDate fechaTurno;


    public TurnoSalidaDto(Long id, Long idPaciente, Long idOdontologo, LocalDate fechaTurno) {
        this.id = id;
        this.idPaciente = idPaciente;
        this.idOdontologo = idOdontologo;
        this.fechaTurno = fechaTurno;
    }

    public TurnoSalidaDto() {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(Long idPaciente) {
        this.idPaciente = idPaciente;
    }

    public Long getIdOdontologo() {
        return idOdontologo;
    }

    public void setIdOdontologo(Long idOdontologo) {
        this.idOdontologo = idOdontologo;
    }

    public LocalDate getFechaTurno() {
        return fechaTurno;
    }

    public void setFechaTurno(LocalDate fechaTurno) {
        this.fechaTurno = fechaTurno;
    }
}
