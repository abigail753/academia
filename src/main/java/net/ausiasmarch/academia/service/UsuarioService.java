package net.ausiasmarch.academia.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import net.ausiasmarch.academia.entity.UsuarioEntity;
import net.ausiasmarch.academia.exception.ResourceNotFoundException;
import net.ausiasmarch.academia.exception.UnauthorizedAccessException;
import net.ausiasmarch.academia.repository.UsuarioRepository;

@Service
@AllArgsConstructor
public class UsuarioService implements ServiceInterface<UsuarioEntity> {

    HttpServletRequest oHttpServletRequest;

    @Autowired
    UsuarioRepository oUsuarioRepository;

    @Autowired
    RandomService oRandomService;

    @Autowired
    HashService oHashService;

    @Autowired
    AuthService oAuthService;

    // Cargar datos aleatorios - Si esta fuera no lo lee
    private String[] arrNombres = { "Juan", "Sofía", "Andrea", "Claudia", "Esteban", "Julia", "Isabel", "Fernando",
            "Carlos", "Beatriz", "Pedro", "Verónica", "Alberto", "Patricia", "Diego", "Elena" };

    private String[] arrApellidos = { "Ruiz", "Alonso", "Castaño", "Molina", "Blanco", "Navarro", "Ortega",
            "Ramos", "Castro", "Domínguez", "Suárez", "Nieto", "Aguilar", "Vargas",
            "Iglesias", "Crespo", "Delgado" };

    private String[] arrTipousuario = { "Administrador", "Estudiante", "Profesor" };

    public Long randomCreate(Long cantidad) {
        for (int i = 0; i < cantidad; i++) {
            UsuarioEntity oUsuarioEntity = new UsuarioEntity();
            oUsuarioEntity.setNombre(arrNombres[oRandomService.getRandomInt(0, arrNombres.length - 1)]);
            oUsuarioEntity.setApellidos(arrApellidos[oRandomService.getRandomInt(0, arrApellidos.length - 1)]);
            oUsuarioEntity
                    .setCorreo(oUsuarioEntity.getNombre() + oRandomService.getRandomInt(999, 9999) + "@gmail.com");
            oUsuarioEntity.setFoto(null);

            oUsuarioEntity.setTipousuario(arrTipousuario[oRandomService.getRandomInt(0, arrTipousuario.length - 1)]);

            oUsuarioRepository.save(oUsuarioEntity);
        }
        return oUsuarioRepository.count();
    }

    // Cargar datos
    public Page<UsuarioEntity> getPage(Pageable oPageable, Optional<String> filter) {

        if (oAuthService.isAdmin()) {
            if (filter.isPresent()) {
                return oUsuarioRepository
                        .findByNombreContainingOrApellidosContainingOrCorreoContainingOrTipousuarioContaining(
                                filter.get(), filter.get(), filter.get(), filter.get(),
                                oPageable);
            } else {
                return oUsuarioRepository.findAll(oPageable);
            }
        }

        if (oAuthService.isProfesor()) {
            if (filter.isPresent()) {
                return oUsuarioRepository
                        .findAlumnosByProfesor(oAuthService.getUsuarioFromToken().getId(), filter.get(), oPageable);
            } else {
                return oUsuarioRepository.findAlumnosByProfesor(oAuthService.getUsuarioFromToken().getId(), oPageable);
            }
        }

        throw new UnauthorizedAccessException("No tienes permisos para ver los datos de usuarios.");

    }

    public UsuarioEntity get(Long id) {
        if (oAuthService.isAdmin()) {
            return oUsuarioRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado."));
        }

        if (oAuthService.isProfesor()) {
            return oUsuarioRepository
                    .findAlumnoByProfesor(oAuthService.getUsuarioFromToken().getId(), id)
                    .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado."));
        }

        throw new UnauthorizedAccessException("No tienes permisos para ver los datos de usuarios.");

    }

    public UsuarioEntity getByCorreo(String correo) {
        return oUsuarioRepository.findByCorreo(correo)
                .orElseThrow(() -> new ResourceNotFoundException("Correo no encontrado"));
    }

    // Contar
    public Long count() {
        return oUsuarioRepository.count();
    }

    // Crear
    public UsuarioEntity create(UsuarioEntity oUsuarioEntity) {

        if (!oAuthService.isAdmin()) {
            throw new UnauthorizedAccessException("No tienes permisos para crear usuarios.");
        }

        oUsuarioEntity.setPassword(oHashService.hashPassword(oUsuarioEntity.getPassword()));

        return oUsuarioRepository.save(oUsuarioEntity);
    }

    // Eliminar
    public Long delete(Long id) {

        if (!oAuthService.isAdmin()) {
            throw new UnauthorizedAccessException("No tienes permisos para eliminar usuarios.");
        }

        oUsuarioRepository.deleteById(id);
        return 1L;
    }

    // Actualizar
    public UsuarioEntity update(UsuarioEntity oUsuarioEntity) {

        if (oAuthService.isAdmin()) {
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

            if (oUsuarioEntity.getPassword() != null) {
                oUsuarioEntityFromDatabase.setPassword(oHashService.hashPassword(oUsuarioEntity.getPassword()));
            }

            if (oUsuarioEntity.getFoto() != null) {
                oUsuarioEntityFromDatabase.setFoto(oUsuarioEntity.getFoto());
            }

            if (oUsuarioEntity.getTipousuario() != null) {
                oUsuarioEntityFromDatabase.setTipousuario(oUsuarioEntity.getTipousuario());
            }

            return oUsuarioRepository.save(oUsuarioEntityFromDatabase);
        }

        if (oAuthService.isProfesor()) {

            UsuarioEntity oUsuarioEntityFromDatabase = oUsuarioRepository
                    .findAlumnoByProfesor(oAuthService.getUsuarioFromToken().getId(), oUsuarioEntity.getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado."));

            if (oUsuarioEntity.getNombre() != null) {
                oUsuarioEntityFromDatabase.setNombre(oUsuarioEntity.getNombre());
            }

            if (oUsuarioEntity.getApellidos() != null) {
                oUsuarioEntityFromDatabase.setApellidos(oUsuarioEntity.getApellidos());
            }

            return oUsuarioRepository.save(oUsuarioEntityFromDatabase);
        }

        throw new UnauthorizedAccessException("No tienes permisos para editar usuarios.");

    }

    // Random Selection
    public UsuarioEntity randomSelection() {
        return oUsuarioRepository.findById((long) oRandomService.getRandomInt(1, (int) (long) this.count())).get();
    }

}