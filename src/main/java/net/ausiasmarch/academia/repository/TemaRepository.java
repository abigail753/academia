package net.ausiasmarch.academia.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import net.ausiasmarch.academia.entity.TemaEntity;

public interface TemaRepository extends JpaRepository<TemaEntity, Long> {
        Page<TemaEntity> findByTituloContainingOrDescripcionContaining(
                        String filter2, String filter3, Pageable oPageable);

        @Query(value = "SELECT * FROM tema WHERE id_curso=:id_curso", nativeQuery = true)
        Page<TemaEntity> findByCursoId(Long id_curso, Pageable oPageable);

        @Query(value = "SELECT * FROM tema WHERE id_curso IN (SELECT id_curso FROM inscripcion WHERE id_usuario = :id_usuario)", nativeQuery = true)
        Page<TemaEntity> findTemasByUsuarioId(@Param("id_usuario") Long id_usuario, Pageable pageable);

        @Query(value = "SELECT * FROM tema WHERE id_curso IN (SELECT id_curso FROM inscripcion WHERE id_usuario = :id_usuario) AND (titulo LIKE CONCAT('%', :filter, '%') OR descripcion LIKE CONCAT('%', :filter, '%'))", nativeQuery = true)
        Page<TemaEntity> findTemasByUsuarioIdAndFilter(@Param("id_usuario") Long id_usuario,
                        @Param("filter") String filter, Pageable pageable);

        //
        @Query(value = "SELECT COUNT(*) > 0 FROM tema t WHERE t.id=:id_tema AND id_curso IN (SELECT id_curso FROM inscripcion WHERE id_usuario = :id_usuario)", nativeQuery = true)
        int existsTemaByUsuarioId(@Param("id_usuario") Long id_usuario, @Param("id_tema") Long id_tema);

        // Devuelve los temas de un examen
        @Query(value = "SELECT * FROM tema t WHERE t.id IN (SELECT c.id_tema FROM calificacion c WHERE c.id_usuario=:id_profesor AND c.id_examen=:id_examen)", nativeQuery = true)
        Page<TemaEntity> findByExamen(Pageable oPageable, @Param("id_examen") Long id_examen,
                        @Param("id_profesor") Long id_profesor);

        // Listar temas por curso y por Alumno
        @Query(value="SELECT * FROM tema WHERE id_curso=:id_curso AND id_curso IN "+
                        "(SELECT id_curso FROM inscripcion WHERE id_curso=:id_curso AND id_usuario=:id_alumno)", 
                        nativeQuery = true)
        Page<TemaEntity>findByCursoAndAlumno(Pageable oPageable, @Param("id_curso") Long id_curso, @Param("id_alumno") Long id_alumno);
}
