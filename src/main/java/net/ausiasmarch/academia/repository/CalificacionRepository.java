package net.ausiasmarch.academia.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import jakarta.transaction.Transactional;
import net.ausiasmarch.academia.entity.CalificacionEntity;

public interface CalificacionRepository  extends JpaRepository<CalificacionEntity, Long> {
    @Query(value = "SELECT * FROM calificacion WHERE id_usuario=:id_usuario", nativeQuery = true)
    Page<CalificacionEntity> findByUsuarioId(Long id_usuario, Pageable oPageable);

    @Query(value = "SELECT * FROM calificacion WHERE id_examen=:id_examen", nativeQuery = true)
    Page<CalificacionEntity> findByExamenId(Long id_examen, Pageable oPageable);

    @Query(value = "SELECT * FROM calificacion WHERE id_tema=:id_tema", nativeQuery = true)
    Page<CalificacionEntity> findByTemaId(Long id_tema, Pageable oPageable);

    // Delete de Examen
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM calificacion WHERE id_examen=:id_examen", nativeQuery = true)
    void deleteCalificacionesByExamenId(Long id_examen);

    // Page para profesores
    /*  @Query(value = "SELECT c.* " +
                   "FROM calificacion c " +
                   "INNER JOIN tema t ON c.id_tema = t.id " +
                   "INNER JOIN curso cu ON t.id_curso = cu.id " +
                   "WHERE cu.id IN ( " +
                   "    SELECT i.id_curso " +
                   "    FROM inscripcion i " +
                   "    WHERE i.id_usuario = :idUsuario " +
                   ") " +
                   "AND c.id_usuario <> :idUsuario", 
           nativeQuery = true)
    List<Calificacion> findCalificacionesByInscripcionExcludingUser(@Param("idUsuario") Long idUsuario); */
}
