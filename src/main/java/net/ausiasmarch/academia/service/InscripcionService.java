package net.ausiasmarch.academia.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import net.ausiasmarch.academia.entity.InscripcionEntity;
import net.ausiasmarch.academia.entity.UsuarioEntity;
import net.ausiasmarch.academia.exception.ResourceNotFoundException;
import net.ausiasmarch.academia.exception.UnauthorizedAccessException;
import net.ausiasmarch.academia.repository.InscripcionRepository;

@Service
public class InscripcionService implements ServiceInterface<InscripcionEntity> {

    @Autowired
    InscripcionRepository oInscripcionRepository;

    @Autowired
    RandomService oRandomService;

    @Autowired
    UsuarioService oUsuarioService;

    @Autowired
    CursoService oCursoService;

    @Autowired
    AuthService oAuthService;

    public Long randomCreate(Long cantidad) {
        for (int i = 0; i < cantidad; i++) {
            InscripcionEntity oInscripcionEntity = new InscripcionEntity();

            oInscripcionEntity.setUsuario(oUsuarioService.randomSelection());

            oInscripcionEntity.setCurso(oCursoService.randomSelection());

            oInscripcionRepository.save(oInscripcionEntity);
        }
        return oInscripcionRepository.count();
    }

    // Cargar datos - Inscripcion
    public Page<InscripcionEntity> getPage(Pageable oPageable, Optional<String> filter) {
        if (!oAuthService.isAdmin()) {
            throw new UnauthorizedAccessException("No tienes permisos para acceder a este listado.");
        }

        return oInscripcionRepository.findAll(oPageable);
    }

    public InscripcionEntity get(Long id) {

        if (!oAuthService.isAdmin()) {
            throw new UnauthorizedAccessException("No tienes permisos para acceder a este campo.");
        }

        return oInscripcionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Inscripción no encontrada"));
    }

    // Cargar datos - Usuario
    public Page<InscripcionEntity> getPageXUsuario(Pageable oPageable, Optional<String> filter,
            Optional<Long> id_usuario) {

        if (!oAuthService.isAdmin()) {
            throw new UnauthorizedAccessException("No tienes permisos para acceder a este listado.");
        }

        if (id_usuario.isPresent()) {
            return oInscripcionRepository
                    .findByUsuarioId(id_usuario.get(), oPageable);
        } else {
            throw new ResourceNotFoundException("Usuario no encontrado");
        }
    }

    // Cargar datos - Curso
    public Page<InscripcionEntity> getPageXCurso(Pageable oPageable, Optional<String> filter,
            Optional<Long> id_curso) {

        if (!oAuthService.isAdmin()) {
            throw new UnauthorizedAccessException("No tienes permisos para acceder a este listado.");
        }

        if (id_curso.isPresent()) {
            return oInscripcionRepository
                    .findByCursoId(id_curso.get(), oPageable);
        } else {
            throw new ResourceNotFoundException("Usuario no encontrado");
        }
    }

    // Contar
    public Long count() {
        if (!oAuthService.isAdmin()) {
            throw new UnauthorizedAccessException("No tienes permisos para acceder a este campo.");
        }
        return oInscripcionRepository.count();
    }

    // Crear
    public InscripcionEntity create(InscripcionEntity oInscripcionEntity) {
        if (!oAuthService.isAdmin()) {
            throw new UnauthorizedAccessException("No tienes permisos para crear inscripciones.");
        }

        UsuarioEntity oUsuarioEntity = oInscripcionEntity.getUsuario();

        if (oUsuarioEntity.getTipousuario().equals("Profesor")) {
            if (oInscripcionRepository.findByUsuarioId(oUsuarioEntity.getId()) > 0) {
                throw new ResourceNotFoundException("Profesor ya inscrito en un curso.");
            } else {
                return oInscripcionRepository.save(oInscripcionEntity);
            }
        } else {
            return oInscripcionRepository.save(oInscripcionEntity);
        }
    }

    // Actualizar
    public InscripcionEntity update(InscripcionEntity oInscripcionEntity) {
        if (!oAuthService.isAdmin()) {
            throw new UnauthorizedAccessException("No tienes permisos para editar inscripciones.");
        }

        InscripcionEntity oInscripcionEntityFromDatabase = oInscripcionRepository.findById(oInscripcionEntity.getId())
                .get();

        if (oInscripcionEntity.getUsuario() != null) {
            oInscripcionEntityFromDatabase.setUsuario(oUsuarioService.get(oUsuarioService.randomSelection().getId()));
        }

        if (oInscripcionEntity.getCurso() != null) {
            oInscripcionEntityFromDatabase.setCurso(oInscripcionEntity.getCurso());
        }

        return oInscripcionRepository.save(oInscripcionEntityFromDatabase);
    }

    // Eliminar
    public Long delete(Long id) {
        if (!oAuthService.isAdmin()) {
            throw new UnauthorizedAccessException("No tienes permisos para eliminar inscripciones.");
        }

        oInscripcionRepository.deleteById(id);
        return 1L;
    }
}
