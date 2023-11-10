package com.backend.clinicaodontologica.service;

import com.backend.clinicaodontologica.dto.entradaOdontologo.OdontologoEntradaDto;
import com.backend.clinicaodontologica.dto.salidaOdontologo.OdontologoSalidaDto;
import com.backend.clinicaodontologica.model.Odontologo;
import java.util.List;

public interface IOdontologoService {
    OdontologoSalidaDto registrarOdontologo(OdontologoEntradaDto odontologo);
    List<OdontologoSalidaDto> listarOdontologos();
    OdontologoSalidaDto buscarOdontologoPorId(int id);
    Odontologo actualizarOdontologo(Odontologo odontologo);
}
