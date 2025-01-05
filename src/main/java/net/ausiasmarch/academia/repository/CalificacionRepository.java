package net.ausiasmarch.academia.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import net.ausiasmarch.academia.entity.CalificacionEntity;

public interface CalificacionRepository  extends JpaRepository<CalificacionEntity, Long> {
    
}
