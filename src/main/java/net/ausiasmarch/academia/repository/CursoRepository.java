package net.ausiasmarch.academia.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import net.ausiasmarch.academia.entity.CursoEntity;

public interface CursoRepository extends JpaRepository<CursoEntity, Long>  {
    Page<CursoEntity> findByNombreContainingOrDescripcion(
            String filter2, String filter3, String filter4, Pageable oPageable);
}
