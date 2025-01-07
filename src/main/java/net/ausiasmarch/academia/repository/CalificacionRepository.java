package net.ausiasmarch.academia.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import net.ausiasmarch.academia.entity.CalificacionEntity;

public interface CalificacionRepository  extends JpaRepository<CalificacionEntity, Long> {
    @Query(value = "SELECT * FROM calificacion WHERE id_usuario=:id_usuario", nativeQuery = true)
    Page<CalificacionEntity> findByUsuarioId(Long id_usuario, Pageable oPageable);

    @Query(value = "SELECT * FROM calificacion WHERE id_examen=:id_examen", nativeQuery = true)
    Page<CalificacionEntity> findByExamenId(Long id_examen, Pageable oPageable);

    @Query(value = "SELECT * FROM calificacion WHERE id_tema=:id_tema", nativeQuery = true)
    Page<CalificacionEntity> findByTemaId(Long id_tema, Pageable oPageable);
}
