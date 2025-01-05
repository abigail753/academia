package net.ausiasmarch.academia.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "tema")
public class TemaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 3, max = 255)
    private String titulo;

    @NotNull
    @Size(min = 3, max = 255)
    private String descripcion;

    @NotNull
    private Long id_curso;

    @NotNull
    private Long id_calificacion;

    public TemaEntity() {
    }

    public TemaEntity(String titulo, String descripcion, Long id_curso, Long id_calificacion) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.id_curso = id_curso;
        this.id_calificacion = id_calificacion;
    }

    public TemaEntity(Long id, String titulo, String descripcion, Long id_curso, Long id_calificacion) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.id_curso = id_curso;
        this.id_calificacion = id_calificacion;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Long getId_curso() {
        return id_curso;
    }

    public void setId_curso(Long id_curso) {
        this.id_curso = id_curso;
    }

    public Long getId_calificacion() {
        return id_calificacion;
    }

    public void setId_calificacion(Long id_calificacion) {
        this.id_calificacion = id_calificacion;
    }
}
