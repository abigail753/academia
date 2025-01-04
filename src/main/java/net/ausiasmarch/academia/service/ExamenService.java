package net.ausiasmarch.academia.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import net.ausiasmarch.academia.entity.ExamenEntity;
import net.ausiasmarch.academia.exception.ResourceNotFoundException;
import net.ausiasmarch.academia.repository.ExamenRepository;

@Service
public class ExamenService implements ServiceInterface<ExamenEntity> {

    @Autowired
    ExamenRepository oExamenRepository;

    @Autowired
    RandomService oRandomService;

    // Cargar datos aleatorios
    private String[] arrNombres = {"Evaluación Final",
  "Examen de Conocimientos Generales",
  "Prueba de Comprensión de Lectura",
  "Examen de Matemáticas Avanzadas",
  "Evaluación de Ciencias Naturales",
  "Prueba de Historia y Geografía",
  "Examen de Inglés Básico",
  "Evaluación de Competencias Digitales",
  "Prueba de Resolución de Problemas",
  "Examen de Ortografía y Redacción",
  "Evaluación Semestral",
  "Examen Diagnóstico",
  "Prueba de Aptitudes Lógicas",
  "Examen de Física Experimental",
  "Evaluación de Literatura Contemporánea",
  "Prueba de Química Orgánica",
  "Examen de Programación en JavaScript",
  "Evaluación de Ética y Ciudadanía",
  "Prueba de Análisis de Datos",
  "Examen de Diseño Gráfico",};

    public Long randomCreate(Long cantidad) {
        for (int i = 0; i < cantidad; i++) {
            ExamenEntity oExamenEntity = new ExamenEntity();
            oExamenEntity.setNombre(arrNombres[oRandomService.getRandomInt(0, arrNombres.length - 1)]);
            oExamenEntity.setNum_preguntas((long) oRandomService.getRandomInt(5, 20));
            oExamenRepository.save(oExamenEntity);
        }
        return oExamenRepository.count();
    }

    // Cargar datos
    public Page<ExamenEntity> getPage(Pageable oPageable, Optional<String> filter) {

        if (filter.isPresent()) {
            return oExamenRepository
                    .findByNombreContaining(
                            filter.get(), oPageable);
        } else {
            return oExamenRepository.findAll(oPageable);
        }
    }

    public ExamenEntity get(Long id) {
        return oExamenRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Examen no encontrado"));
        // return oExamenRepository.findById(id).get();
    }

    // Contar
    public Long count() {
        return oExamenRepository.count();
    }

    // Crear
    public ExamenEntity create(ExamenEntity oExamenEntity) {
        return oExamenRepository.save(oExamenEntity);
    }

    // Eliminar
    public Long delete(Long id) {
        oExamenRepository.deleteById(id);
        return 1L;
    }

    // Actualizar
    public ExamenEntity update(ExamenEntity oExamenEntity) {
        ExamenEntity oExamenEntityFromDatabase = oExamenRepository.findById(oExamenEntity.getId()).get();
        if (oExamenEntity.getNombre() != null) {
            oExamenEntityFromDatabase.setNombre(oExamenEntity.getNombre());
        }

        if (oExamenEntity.getNum_preguntas() != null) {
            oExamenEntityFromDatabase.setNum_preguntas(oExamenEntity.getNum_preguntas());
        }
        return oExamenRepository.save(oExamenEntityFromDatabase);
    }

}