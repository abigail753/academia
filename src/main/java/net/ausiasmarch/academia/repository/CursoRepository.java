package net.ausiasmarch.academia.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import net.ausiasmarch.academia.entity.CursoEntity;

public interface CursoRepository extends JpaRepository<CursoEntity, Long>  {
    Page<CursoEntity> findByNombreContainingOrDescripcion(
            String filter2, String filter3, String filter4, Pageable oPageable);

    @Query(value = "SELECT c.* FROM curso c JOIN inscripcion i ON c.id = i.id_curso WHERE i.id_usuario = :id_usuario", nativeQuery = true)
    Page<CursoEntity> findByProfesor(@Param("id_usuario") Long id_usuario, Pageable pageable);

}
