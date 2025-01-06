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
import net.ausiasmarch.academia.repository.CalificacionRepository;

@Service
public class CalificacionService implements ServiceInterface<CalificacionEntity> {

    @Autowired
    CalificacionRepository oCalificacionRepository;

    @Autowired
    RandomService oRandomService;

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

            oCalificacionEntity.setId_usuario((long) oRandomService.getRandomInt(1, 10));

            oCalificacionEntity.setId_examen((long) oRandomService.getRandomInt(1, 10));

            oCalificacionRepository.save(oCalificacionEntity);
        }
        return oCalificacionRepository.count();
    }

    // Cargar datos
    public Page<CalificacionEntity> getPage(Pageable oPageable, Optional<String> filter) {
        return oCalificacionRepository.findAll(oPageable);
    }

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
        return oCalificacionRepository.save(oCalificacionEntity);
    }

    // Actualizar
    public CalificacionEntity update(CalificacionEntity oCalificacionEntity) {
        CalificacionEntity oCalificacionEntityFromDatabase = oCalificacionRepository
                .findById(oCalificacionEntity.getId()).get();

        if (oCalificacionEntity.getCalificacion() != null) {
            oCalificacionEntityFromDatabase.setCalificacion(oCalificacionEntity.getCalificacion());
        }

        if (oCalificacionEntity.getFecha_evaluacion() != null) {
            oCalificacionEntityFromDatabase.setFecha_evaluacion(oCalificacionEntity.getFecha_evaluacion());
        }

        if (oCalificacionEntity.getId_usuario() != null) {
            oCalificacionEntityFromDatabase.setId_usuario(oCalificacionEntity.getId_usuario());
        }

        if (oCalificacionEntity.getId_examen() != null) {
            oCalificacionEntityFromDatabase.setId_examen(oCalificacionEntity.getId_examen());
        }

        return oCalificacionRepository.save(oCalificacionEntityFromDatabase);
    }

    // Eliminar
    public Long delete(Long id) {
        oCalificacionRepository.deleteById(id);
        return 1L;
    }

    // Random Selection
    public CalificacionEntity randomSelection() {
        return oCalificacionRepository.findById((long) oRandomService.getRandomInt(1, (int) (long) this.count())).get();
    }
}
