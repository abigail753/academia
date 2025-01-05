package net.ausiasmarch.academia.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.JpaRepository;

import net.ausiasmarch.academia.entity.TemaEntity;

public interface TemaRepository extends JpaRepository<TemaEntity, Long> {
    Page<TemaEntity> findByTituloContainingOrDescripcionContaining (
            String filter2, String filter3, Pageable oPageable);
}
