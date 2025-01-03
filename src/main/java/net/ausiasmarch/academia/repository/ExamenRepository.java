package net.ausiasmarch.academia.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import net.ausiasmarch.academia.entity.ExamenEntity;

public interface ExamenRepository extends JpaRepository<ExamenEntity, Long> {
    Page<ExamenEntity> findByNombreContaining (
        String filter2, String filter3, Pageable oPageable);
}
