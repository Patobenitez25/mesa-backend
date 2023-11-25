package com.backend.clinicaodontologica.service.impl;

import com.backend.clinicaodontologica.dto.entrada.odontologo.OdontologoEntradaDto;
import com.backend.clinicaodontologica.dto.entrada.paciente.PacienteEntradaDto;
import com.backend.clinicaodontologica.dto.entrada.turno.TurnoEntradaDto;
import com.backend.clinicaodontologica.dto.modificacion.PacienteModificacionEntradaDto;
import com.backend.clinicaodontologica.dto.modificacion.TurnoModificacionEntradaDto;
import com.backend.clinicaodontologica.dto.salida.paciente.PacienteSalidaDto;
import com.backend.clinicaodontologica.dto.salida.turno.TurnoSalidaDto;
import com.backend.clinicaodontologica.entity.Odontologo;
import com.backend.clinicaodontologica.entity.Paciente;
import com.backend.clinicaodontologica.entity.Turno;
import com.backend.clinicaodontologica.exceptions.BadRequestException;
import com.backend.clinicaodontologica.exceptions.ResourceNotFoundException;
import com.backend.clinicaodontologica.repository.TurnoRepository;
import com.backend.clinicaodontologica.service.ITurnoService;
import com.backend.clinicaodontologica.utils.JsonPrinter;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class TurnoService implements ITurnoService {

    private final Logger LOGGER = LoggerFactory.getLogger(TurnoService.class);
    private final TurnoRepository TurnoRepository;
    private final ModelMapper modelMapper;

    public TurnoService(TurnoRepository turnoRepository, ModelMapper modelMapper) {
        this.TurnoRepository = turnoRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public TurnoSalidaDto registrarTurno(TurnoEntradaDto Turno) throws BadRequestException {

        if(Turno.getIdOdontologo() != null && Turno.getIdPaciente() != null){
            //convertimos mediante el mapper de dtoEntrada a entidad
            LOGGER.info("TurnoEntradaDto: " + JsonPrinter.toString(Turno));
            Turno TurnoEntidad = modelMapper.map(Turno, Turno.class);

            //mandamos a persistir a la capa dao y obtenemos una entidad
            Turno TurnoAPersistir = TurnoRepository.save(TurnoEntidad);
            //transformamos la entidad obtenida en salidaDto
            TurnoSalidaDto TurnoSalidaDto = modelMapper.map(TurnoAPersistir, TurnoSalidaDto.class);
            LOGGER.info("TurnoSalidaDto: " + JsonPrinter.toString(TurnoSalidaDto));
            return TurnoSalidaDto;
        }
        else{
            LOGGER.error("No se ha encontrado el paciente u odontologo en la base de datos");
            throw new BadRequestException("No se ha podido registrar el turno");
        }
    }

    public List<TurnoSalidaDto> listarTurnos() {
        List<TurnoSalidaDto> TurnosSalidaDto = TurnoRepository.findAll()
                .stream()
                .map(Turno -> modelMapper.map(Turno, TurnoSalidaDto.class))
                .toList();


        //List<Turno> Turnos = TurnoIDao.listarTodos();
        //List<TurnoSalidaDto> TurnoSalidaDtos = new ArrayList<>();
        //for (Turno Turno : Turnos){
        //    TurnoSalidaDto TurnoSalidaDto = modelMapper.map(Turno, TurnoSalidaDto.class);
        //    TurnoSalidaDtos.add(TurnoSalidaDto);
        //}

        if (LOGGER.isInfoEnabled())
            LOGGER.info("Listado de todos los Turnos: {}", JsonPrinter.toString(TurnosSalidaDto));
        return TurnosSalidaDto;
    }

    @Override
    public TurnoSalidaDto buscarTurnoPorId(Long id) {
        Turno TurnoBuscado = TurnoRepository.findById(id).orElse(null);
        TurnoSalidaDto TurnoEncontrado = null;

        if (TurnoBuscado != null) {
            TurnoEncontrado = modelMapper.map(TurnoBuscado, TurnoSalidaDto.class);
            LOGGER.info("Turno encontrado: {}", JsonPrinter.toString(TurnoEncontrado));
        } else LOGGER.error("El id no se encuentra registrado en la base de datos");

        return TurnoEncontrado;
    }

    @Override
    public TurnoSalidaDto actualizarTurno(TurnoModificacionEntradaDto Turno) {
        Turno TurnoRecibido = modelMapper.map(Turno, Turno.class);
        Turno TurnoAActualizar = TurnoRepository.findById(TurnoRecibido.getId()).orElse(null);

        TurnoSalidaDto TurnoSalidaDto = null;

        if (TurnoAActualizar != null) {
            TurnoAActualizar = TurnoRecibido;
            TurnoRepository.save(TurnoAActualizar);

            TurnoSalidaDto = modelMapper.map(TurnoAActualizar, TurnoSalidaDto.class);
            LOGGER.warn("Turno actualizado: {}", JsonPrinter.toString(TurnoSalidaDto));

        } else {
            LOGGER.error("No fue posible actualizar el Turno porque no se encuentra en nuestra base de datos");
            //lanzar excepcion correspondiente
        }


        return TurnoSalidaDto;
    }

    @Override
    public void eliminarTurno(Long id) throws ResourceNotFoundException {
        if (TurnoRepository.findById(id).orElse(null) != null) {
            TurnoRepository.deleteById(id);
            LOGGER.warn("Se ha eliminado el Turno con id: {}", id);
        } else {
            LOGGER.error("No se ha encontrado el Turno con id {}", id);
            throw new ResourceNotFoundException("No se ha encontrado el Turno con id " + id);
        }

    }



    private void configureMapping() {
        modelMapper.typeMap(PacienteEntradaDto.class, Paciente.class)
                .addMappings(mapper -> mapper.map(PacienteEntradaDto::getDomicilioEntradaDto, Paciente::setDomicilio));
        modelMapper.typeMap(Paciente.class, PacienteSalidaDto.class)
                .addMappings(modelMapper -> modelMapper.map(Paciente::getDomicilio, PacienteSalidaDto::setDomicilioSalidaDto));
        modelMapper.typeMap(PacienteModificacionEntradaDto.class, Paciente.class)
                .addMappings(mapper -> mapper.map(PacienteModificacionEntradaDto::getDomicilioModificacionEntradaDto, Paciente::setDomicilio));

    }


}
