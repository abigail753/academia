package net.ausiasmarch.academia.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import net.ausiasmarch.academia.entity.TemaEntity;
import net.ausiasmarch.academia.exception.ResourceNotFoundException;
import net.ausiasmarch.academia.repository.TemaRepository;

@Service
public class TemaService implements ServiceInterface<TemaEntity> {
    @Autowired
    TemaRepository oTemaRepository;

    @Autowired
    RandomService oRandomService;

    private String[] arrTitulo = {
        "Matemáticas Básicas",
        "Historia Mundial",
        "Fundamentos de Programación",
        "Química Orgánica",
        "Lengua y Literatura",
        "Introducción a la Física",
        "Análisis de Datos",
        "Biología Celular",
        "Psicología General",
        "Economía Básica"
    };
    
    private String[] arrDescripcion = {
        "Examen que evalúa habilidades aritméticas y algebraicas fundamentales.",
        "Prueba que abarca eventos clave y figuras históricas globales.",
        "Evaluación de conocimientos básicos en lógica y estructuras de programación.",
        "Examen sobre las propiedades y reacciones de compuestos orgánicos.",
        "Prueba centrada en análisis de textos literarios y comprensión gramatical.",
        "Evaluación de conceptos básicos de mecánica y leyes de Newton.",
        "Examen sobre técnicas de recopilación y análisis de datos estadísticos.",
        "Prueba que aborda estructuras y funciones celulares en organismos vivos.",
        "Examen introductorio a teorías y conceptos en psicología.",
        "Evaluación de principios económicos y fundamentos de oferta y demanda."
    };
    

    public Long randomCreate(Long cantidad) {
        for (int i = 0; i < cantidad; i++) {
            TemaEntity oTemaEntity = new TemaEntity();
            oTemaEntity.setTitulo(arrTitulo[oRandomService.getRandomInt(0, arrTitulo.length - 1)]);
            oTemaEntity.setDescripcion(arrDescripcion[oRandomService.getRandomInt(0, arrDescripcion.length - 1)]);
            oTemaEntity.setId_curso((long) oRandomService.getRandomInt(1,10));
            oTemaEntity.setId_calificacion((long) oRandomService.getRandomInt(1,10));
            
            oTemaRepository.save(oTemaEntity);
        }
        return oTemaRepository.count();
    } 

    // Cargar datos
    public Page<TemaEntity> getPage(Pageable oPageable, Optional<String> filter) {

        if (filter.isPresent()) {
            return oTemaRepository
                    .findByTituloContainingOrDescripcionContaining(
                            filter.get(), filter.get(),
                            oPageable);
        } else {
            return oTemaRepository.findAll(oPageable);
        }
    }

    public TemaEntity get(Long id) {
        return oTemaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tema no encontrado"));
    }

    // Contar
    public Long count() {
        return oTemaRepository.count();
    }

    // Crear
    public TemaEntity create(TemaEntity oTemaEntity) {
        return oTemaRepository.save(oTemaEntity);
    }

    // Eliminar
    public Long delete(Long id) {
        oTemaRepository.deleteById(id);
        return 1L;
    }

    // Actualizar
    public TemaEntity update(TemaEntity oTemaEntity) {
        TemaEntity oTemaEntityFromDatabase = oTemaRepository.findById(oTemaEntity.getId()).get();
        if (oTemaEntity.getTitulo() != null) {
            oTemaEntityFromDatabase.setTitulo(oTemaEntity.getTitulo());
        }

        if (oTemaEntity.getDescripcion() != null) {
            oTemaEntityFromDatabase.setDescripcion(oTemaEntity.getDescripcion());
        }

        if (oTemaEntity.getId_curso() != null) {
            oTemaEntityFromDatabase.setId_curso(oTemaEntity.getId_curso());
        }

        if (oTemaEntity.getId_calificacion() != null) {
            oTemaEntityFromDatabase.setId_calificacion(oTemaEntity.getId_calificacion());
        }

        return oTemaRepository.save(oTemaEntityFromDatabase);
    }

}