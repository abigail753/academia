package net.ausiasmarch.academia.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import net.ausiasmarch.academia.entity.UsuarioEntity;

public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long> {
    
}
