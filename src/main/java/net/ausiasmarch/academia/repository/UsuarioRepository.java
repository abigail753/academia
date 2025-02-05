package net.ausiasmarch.academia.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.JpaRepository;
import net.ausiasmarch.academia.entity.UsuarioEntity;

public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long> {
    Page<UsuarioEntity> findByNombreContainingOrApellidosContainingOrCorreoContainingOrTipousuarioContaining(
            String filter2, String filter3, String filter4, String filter5, Pageable oPageable);
    
    Page<UsuarioEntity> findByTipousuario(String tipousuario,Pageable oPageable);

    Optional<UsuarioEntity> findByCorreo(String correo);

    Optional<UsuarioEntity> findByCorreoAndPassword(String correo, String password);

}
