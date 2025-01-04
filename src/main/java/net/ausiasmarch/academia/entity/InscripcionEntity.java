package net.ausiasmarch.academia.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table (name="inscripcion")
public class InscripcionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Long id_usuario;

    @NotNull
    
    private Long id_curso;

    public InscripcionEntity() {
    }

    public InscripcionEntity(Long id_usuario, Long id_curso) {
        this.id_usuario = id_usuario;
        this.id_curso = id_curso;
    }

    public InscripcionEntity(Long id, Long id_usuario, Long id_curso) {
        this.id = id;
        this.id_usuario = id_usuario;
        this.id_curso = id_curso;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(Long id_usuario) {
        this.id_usuario = id_usuario;
    }

    public Long getId_curso() {
        return id_curso;
    }

    public void setId_curso(Long id_curso) {
        this.id_curso = id_curso;
    }
}
