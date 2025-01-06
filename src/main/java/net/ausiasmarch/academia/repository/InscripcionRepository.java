package net.ausiasmarch.academia.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import net.ausiasmarch.academia.entity.InscripcionEntity;

public interface InscripcionRepository extends JpaRepository<InscripcionEntity, Long> {
    @Query(value = "SELECT * FROM inscripcion WHERE id_usuario=:id_usuario", nativeQuery = true)
    Page<InscripcionEntity> findByUsuarioIdContaining(Long id_usuario, Pageable oPageable);
}
