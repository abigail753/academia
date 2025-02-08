package net.ausiasmarch.academia.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import net.ausiasmarch.academia.entity.InscripcionEntity;

public interface InscripcionRepository extends JpaRepository<InscripcionEntity, Long> {
    @Query(value = "SELECT * FROM inscripcion WHERE id_usuario=:id_usuario", nativeQuery = true)
    Page<InscripcionEntity> findByUsuarioId(Long id_usuario, Pageable oPageable);

    @Query(value = "SELECT * FROM inscripcion WHERE id_curso=:id_curso", nativeQuery = true)
    Page<InscripcionEntity> findByCursoId(Long id_curso, Pageable oPageable);

    // Verifica si el profesor esta inscrito en el curso
    @Query(value = "SELECT i FROM InscripcionEntity i WHERE i.usuario.id = :id_usuario AND i.curso.id = :id_curso")
    InscripcionEntity findByUsuarioIdAndCursoId(@Param("id_usuario") Long id_usuario, @Param("id_curso") Long id_curso);
}
