package net.ausiasmarch.academia.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import net.ausiasmarch.academia.entity.TemaEntity;

public interface TemaRepository extends JpaRepository<TemaEntity, Long> {
    Page<TemaEntity> findByTituloContainingOrDescripcionContaining (
            String filter2, String filter3, Pageable oPageable);

    @Query(value = "SELECT * FROM tema WHERE id_curso=:id_curso", nativeQuery = true)
    Page<TemaEntity> findByCursoId(Long id_curso, Pageable oPageable);

}
