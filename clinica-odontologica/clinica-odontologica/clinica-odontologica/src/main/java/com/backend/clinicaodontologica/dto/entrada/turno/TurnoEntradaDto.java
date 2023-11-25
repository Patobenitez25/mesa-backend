package com.backend.clinicaodontologica.dto.entrada.turno;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.Valid;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

public class TurnoEntradaDto {

    @NotNull(message = "El id del paciente no puede ser nulo")
    @NotBlank(message = "Debe especificarse el id del paciente")
    private Long idPaciente;

    @NotNull(message = "El id del odontólogo no puede ser nulo")
    @NotBlank(message = "Debe especificarse el id del odontologo")
    private Long idOdontologo;

    @FutureOrPresent(message = "La fecha no puede ser anterior al día de hoy")
    @NotNull(message = "Debe especificarse la fecha del turno")
    //@JsonProperty("fecha_ingreso")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDate fechaTurno;

    public TurnoEntradaDto(Long idPaciente, Long idOdontologo, LocalDate fechaTurno) {
        this.idPaciente = idPaciente;
        this.idOdontologo = idOdontologo;
        this.fechaTurno = fechaTurno;
    }

    public TurnoEntradaDto() {
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

