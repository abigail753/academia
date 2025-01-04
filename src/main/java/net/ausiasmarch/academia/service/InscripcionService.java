package net.ausiasmarch.academia.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import net.ausiasmarch.academia.entity.InscripcionEntity;
import net.ausiasmarch.academia.exception.ResourceNotFoundException;
import net.ausiasmarch.academia.repository.InscripcionRepository;

@Service
public class InscripcionService implements ServiceInterface<InscripcionEntity> {

    @Autowired
    InscripcionRepository oInscripcionRepository;

    @Autowired
    RandomService oRandomService;

    public Long randomCreate(Long cantidad) {
         for (int i = 0; i < cantidad; i++) {
            InscripcionEntity oInscripcionEntity = new InscripcionEntity();

            oInscripcionEntity.setId_usuario((long) oRandomService.getRandomInt(1,10));
            oInscripcionEntity.setId_curso((long) oRandomService.getRandomInt(1,10));

            oInscripcionRepository.save(oInscripcionEntity);
        }
        return oInscripcionRepository.count();
    }

    // Cargar datos
    public Page<InscripcionEntity> getPage(Pageable oPageable, Optional<String> filter) {
            return oInscripcionRepository.findAll(oPageable);
    }

    public InscripcionEntity get(Long id) {
        return oInscripcionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Inscripcion no encontrada"));    }

    // Contar
    public Long count() {
        return oInscripcionRepository.count();
    }

    // Crear
    public InscripcionEntity create(InscripcionEntity oInscripcionEntity) {
        return oInscripcionRepository.save(oInscripcionEntity);
    }

    // Actualizar
    public InscripcionEntity update(InscripcionEntity oInscripcionEntity) {
        InscripcionEntity oInscripcionEntityFromDatabase = oInscripcionRepository.findById(oInscripcionEntity.getId()).get();
        if (oInscripcionEntity.getId_usuario() != null) {
            oInscripcionEntityFromDatabase.setId_usuario(oInscripcionEntity.getId_usuario());
        }

        if (oInscripcionEntity.getId_curso() != null) {
            oInscripcionEntityFromDatabase.setId_curso(oInscripcionEntity.getId_curso());
        }

        return oInscripcionRepository.save(oInscripcionEntityFromDatabase);
    }

    // Eliminar
    public Long delete(Long id) {
        oInscripcionRepository.deleteById(id);
        return 1L;
    }
}
