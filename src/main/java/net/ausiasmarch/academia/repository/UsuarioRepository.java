package net.ausiasmarch.academia.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.JpaRepository;
import net.ausiasmarch.academia.entity.UsuarioEntity;

public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long> {
    Page<UsuarioEntity> findByNombreContainingOrApellidosContainingOrCorreoContainingOrTipousuarioContaining (
            String nombre, String apellidos, String correo, String tipousuario, Pageable oPageable);

    Page<UsuarioEntity> findByTipousuarioAndNombreContainingOrTipousuarioAndApellidosContainingOrTipousuarioAndCorreoContaining (
        String tipousuario1, String nombre, 
        String tipousuario2, String apellidos, 
        String tipousuario3, String correo, 
        Pageable pageable);
    
    Page<UsuarioEntity> findByTipousuario(String tipousuario,Pageable oPageable);

    Optional<UsuarioEntity> findByCorreo(String correo);

    Optional<UsuarioEntity> findByCorreoAndPassword(String correo, String password);

}
