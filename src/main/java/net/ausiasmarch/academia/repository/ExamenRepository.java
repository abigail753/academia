package net.ausiasmarch.academia.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import net.ausiasmarch.academia.api.Examen;
import net.ausiasmarch.academia.entity.ExamenEntity;

public interface ExamenRepository extends JpaRepository<ExamenEntity, Long> {
    Page<ExamenEntity> findByNombreContaining(
            String filter2, Pageable oPageable);

    @Query(value = "SELECT DISTINCT e.* FROM examen e INNER JOIN calificacion c ON e.id = c.id_examen WHERE c.id_usuario = :id_profesor", nativeQuery = true)
    Page<ExamenEntity> findExamenesByProfesor(@Param("id_profesor") Long id_profesor, Pageable oPageable);

    @Query(value = "SELECT DISTINCT e.* FROM examen e " +
            "INNER JOIN calificacion c ON e.id = c.id_examen " +
            "WHERE c.id_usuario = :id_profesor " +
            "AND e.nombre LIKE %:nombre%", nativeQuery = true)
            
    Page<ExamenEntity> findExamenesByProfesorAndNombre(@Param("id_profesor") Long id_profesor,
            @Param("nombre") String nombre, Pageable oPageable);

    @Query("SELECT e FROM Examen e " +
            "JOIN Calificacion c ON e.id = c.examen.id " +
            "WHERE c.usuario.id = :idUsuario " +
            "AND e.id = :idExamen")
    Optional<ExamenEntity> findExamenByUsuarioAndId(@Param("idUsuario") Long idUsuario,
            @Param("idExamen") Long idExamen);

}
