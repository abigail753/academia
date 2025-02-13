package net.ausiasmarch.academia.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import net.ausiasmarch.academia.entity.CalificacionEntity;
import net.ausiasmarch.academia.exception.ResourceNotFoundException;
import net.ausiasmarch.academia.exception.UnauthorizedAccessException;
import net.ausiasmarch.academia.repository.CalificacionRepository;

@Service
public class CalificacionService implements ServiceInterface<CalificacionEntity> {

    @Autowired
    CalificacionRepository oCalificacionRepository;

    @Autowired
    RandomService oRandomService;

    @Autowired
    UsuarioService oUsuarioService;

    @Autowired
    ExamenService oExamenService;

    @Autowired
    TemaService oTemaService;

    @Autowired
    AuthService oAuthService;

    private BigDecimal[] arrCalificaciones = {
            new BigDecimal("5.0"),
            new BigDecimal("4.5"),
            new BigDecimal("8.0"),
            new BigDecimal("7.5"),
            new BigDecimal("10.0"),
            new BigDecimal("3.5"),
            new BigDecimal("2.0")
    };

    private LocalDate[] arrFechas = {
            LocalDate.of(2023, 5, 15),
            LocalDate.of(2024, 6, 20),
            LocalDate.of(2025, 7, 30),
            LocalDate.of(2023, 11, 10),
            LocalDate.of(2024, 9, 5)
    };

    public Long randomCreate(Long cantidad) {
        for (int i = 0; i < cantidad; i++) {
            CalificacionEntity oCalificacionEntity = new CalificacionEntity();

            oCalificacionEntity
                    .setCalificacion(arrCalificaciones[oRandomService.getRandomInt(0, arrCalificaciones.length - 1)]);

            oCalificacionEntity.setFecha_evaluacion(arrFechas[oRandomService.getRandomInt(0, arrFechas.length - 1)]);

            oCalificacionEntity.setUsuario(oUsuarioService.randomSelection());

            oCalificacionEntity.setExamen(oExamenService.randomSelection());

            oCalificacionEntity.setTema(oTemaService.randomSelection());

            oCalificacionRepository.save(oCalificacionEntity);
        }
        return oCalificacionRepository.count();
    }

    // Cargar datos - Calificacion
    public Page<CalificacionEntity> getPage(Pageable oPageable, Optional<String> filter) {
        if (oAuthService.isAdmin()) {
            return oCalificacionRepository.findAll(oPageable);
        }

        if (oAuthService.isProfesor()) {
            return oCalificacionRepository.findCalificacionesAlumnos(oAuthService.getUsuarioFromToken().getId(),
                    oPageable);
        }

        throw new UnauthorizedAccessException("No tienes permisos para acceder a este listado.");

    }

    // Cargar datos - Usuario
    public Page<CalificacionEntity> getPageXUsuario(Pageable oPageable, Optional<String> filter,
            Optional<Long> id_usuario) {

        if (id_usuario.isPresent()) {
            return oCalificacionRepository
                    .findByUsuarioId(id_usuario.get(), oPageable);
        } else {
            throw new ResourceNotFoundException("Usuario no encontrado");
        }
    }

    // Cargar datos - Examen
    public Page<CalificacionEntity> getPageXExamen(Pageable oPageable, Optional<String> filter,
            Optional<Long> id_examen) {

        if (id_examen.isPresent()) {
            return oCalificacionRepository
                    .findByExamenId(id_examen.get(), oPageable);
        } else {
            throw new ResourceNotFoundException("Examen no encontrado");
        }
    }

    // Cargar datos - Tema
    public Page<CalificacionEntity> getPageXTema(Pageable oPageable, Optional<String> filter,
            Optional<Long> id_tema) {

        if (id_tema.isPresent()) {
            return oCalificacionRepository
                    .findByTemaId(id_tema.get(), oPageable);
        } else {
            throw new ResourceNotFoundException("Tema no encontrado");
        }
    }

    // Cargar datos - Calificacion
    public CalificacionEntity get(Long id) {
        return oCalificacionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Calificacion no encontrada"));
    }

    // Contar
    public Long count() {
        return oCalificacionRepository.count();
    }

    // Crear
    public CalificacionEntity create(CalificacionEntity oCalificacionEntity) {
        if (oAuthService.isAdminOrProfesor()) {
            return oCalificacionRepository.save(oCalificacionEntity);
        }

        throw new UnauthorizedAccessException("No tienes permisos para crear calificaciones ");
    }

    // Actualizar
    public CalificacionEntity update(CalificacionEntity oCalificacionEntity) {

        if (oAuthService.isAdminOrProfesor()) {
            CalificacionEntity oCalificacionEntityFromDatabase = oCalificacionRepository
                    .findById(oCalificacionEntity.getId()).get();

            if (oCalificacionEntity.getCalificacion() != null) {
                oCalificacionEntityFromDatabase.setCalificacion(oCalificacionEntity.getCalificacion());
            }

            if (oCalificacionEntity.getFecha_evaluacion() != null) {
                oCalificacionEntityFromDatabase.setFecha_evaluacion(oCalificacionEntity.getFecha_evaluacion());
            }

            if (oCalificacionEntity.getUsuario() != null) {
                oCalificacionEntityFromDatabase
                        .setUsuario(oUsuarioService.get(oUsuarioService.randomSelection().getId()));
            }

            if (oCalificacionEntity.getExamen() != null) {
                oCalificacionEntityFromDatabase
                        .setExamen(oExamenService.get(oUsuarioService.randomSelection().getId()));
            }

            if (oCalificacionEntity.getTema() != null) {
                oCalificacionEntityFromDatabase.setTema(oTemaService.get(oUsuarioService.randomSelection().getId()));
            }

            return oCalificacionRepository.save(oCalificacionEntityFromDatabase);
        }

        if (oAuthService.isProfesor()) {

        }

        throw new UnauthorizedAccessException("No tienes permisos para editar calificaciones.");

    }

    // Eliminar
    public Long delete(Long id) {
        if (oAuthService.isAdminOrProfesor()) {
            oCalificacionRepository.deleteById(id);
            return 1L;
        }
        throw new UnauthorizedAccessException("No tienes permisos para eliminar calificaciones.");
    }

    // Random Selection
    public CalificacionEntity randomSelection() {
        return oCalificacionRepository.findById((long) oRandomService.getRandomInt(1, (int) (long) this.count())).get();
    }
}
