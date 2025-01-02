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

    public Page<UsuarioEntity> getPage(Pageable oPageable, Optional<String> filter) {
            return oUsuarioRepository.findAll(oPageable);
    }

    public UsuarioEntity get(Long id) {
        return oUsuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
        // return oUsuarioRepository.findById(id).get();
    }

    public Long count() {
        return oUsuarioRepository.count();
    }

    public UsuarioEntity create(UsuarioEntity oUsuarioEntity) {
        return oUsuarioRepository.save(oUsuarioEntity);
    }
    
    public Long delete(Long id) {
        oUsuarioRepository.deleteById(id);
        return 1L;
    }

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