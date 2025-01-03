package net.ausiasmarch.academia.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import net.ausiasmarch.academia.entity.UsuarioEntity;
import net.ausiasmarch.academia.exception.ResourceNotFoundException;
import net.ausiasmarch.academia.repository.UsuarioRepository;

@Service
public class UsuarioService implements ServiceInterface<UsuarioEntity> {

    @Autowired
    UsuarioRepository oUsuarioRepository;

    @Autowired
    RandomService oRandomService;

    // Cargar datos aleatorios
    private String[] arrNombres = {"Juan", "Sofía", "Andrea", "Claudia", "Esteban", "Julia", "Isabel", "Fernando", 
                                  "Carlos", "Beatriz", "Pedro", "Verónica", "Alberto", "Patricia", "Diego", "Elena"};
    
    private String[] arrApellidos = {"Ruiz", "Alonso", "Castaño", "Molina", "Blanco", "Navarro", "Ortega", 
                                    "Ramos", "Castro", "Domínguez", "Suárez", "Nieto", "Aguilar", "Vargas", 
                                    "Iglesias", "Crespo", "Delgado"};
    
    public Long randomCreate(Long cantidad) {
        for (int i = 0; i < cantidad; i++) {
            UsuarioEntity oUsuarioEntity = new UsuarioEntity();
            oUsuarioEntity.setNombre(arrNombres[oRandomService.getRandomInt(0, arrNombres.length - 1)]);
            oUsuarioEntity.setApellidos(arrApellidos[oRandomService.getRandomInt(0, arrApellidos.length - 1)]);
            oUsuarioEntity.setCorreo(oUsuarioEntity.getNombre() + oRandomService.getRandomInt(999, 9999) + "@gmail.com");
            oUsuarioRepository.save(oUsuarioEntity);
        }
        return oUsuarioRepository.count();
    }                                

    // Cargar datos
    public Page<UsuarioEntity> getPage(Pageable oPageable, Optional<String> filter) {

        if (filter.isPresent()) {
            return oUsuarioRepository
                    .findByNombreContainingOrApellidosContainingOrCorreoContaining(
                            filter.get(), filter.get(), filter.get(),
                            oPageable);
        } else {
            return oUsuarioRepository.findAll(oPageable);
        }
    }

    public UsuarioEntity get(Long id) {
        return oUsuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
        // return oUsuarioRepository.findById(id).get();
    }

    // Contar
    public Long count() {
        return oUsuarioRepository.count();
    }

    // Crear
    public UsuarioEntity create(UsuarioEntity oUsuarioEntity) {
        return oUsuarioRepository.save(oUsuarioEntity);
    }

    // Eliminar
    public Long delete(Long id) {
        oUsuarioRepository.deleteById(id);
        return 1L;
    }

    // Actualizar
    public UsuarioEntity update(UsuarioEntity oUsuarioEntity) {
        UsuarioEntity oUsuarioEntityFromDatabase = oUsuarioRepository.findById(oUsuarioEntity.getId()).get();
        if (oUsuarioEntity.getNombre() != null) {
            oUsuarioEntityFromDatabase.setNombre(oUsuarioEntity.getNombre());
        }

        if (oUsuarioEntity.getApellidos() != null) {
            oUsuarioEntityFromDatabase.setApellidos(oUsuarioEntity.getApellidos());
        }

        if (oUsuarioEntity.getCorreo() != null) {
            oUsuarioEntityFromDatabase.setCorreo(oUsuarioEntity.getCorreo());
        }
        return oUsuarioRepository.save(oUsuarioEntityFromDatabase);
    }

}