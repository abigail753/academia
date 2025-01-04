package net.ausiasmarch.academia.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import net.ausiasmarch.academia.entity.InscripcionEntity;

public interface InscripcionRepository extends JpaRepository<InscripcionEntity, Long>   {
    
}
