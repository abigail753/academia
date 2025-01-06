package net.ausiasmarch.academia.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "calificacion")
public class CalificacionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Digits(integer = 2, fraction = 2)
    private BigDecimal calificacion;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate fecha_evaluacion;

    @NotNull
    private Long id_usuario;

    @NotNull
    private Long id_examen;

    public CalificacionEntity() {
    }

    public CalificacionEntity(BigDecimal calificacion, LocalDate fecha_evaluacion, Long id_usuario, Long id_examen) {
        this.calificacion = calificacion;
        this.fecha_evaluacion = fecha_evaluacion;
        this.id_usuario = id_usuario;
        this.id_examen = id_examen;
    }

    public CalificacionEntity(Long id, BigDecimal calificacion, LocalDate fecha_evaluacion, Long id_usuario,
            Long id_examen) {
        this.id = id;
        this.calificacion = calificacion;
        this.fecha_evaluacion = fecha_evaluacion;
        this.id_usuario = id_usuario;
        this.id_examen = id_examen;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(BigDecimal calificacion) {
        this.calificacion = calificacion;
    }

    public LocalDate getFecha_evaluacion() {
        return fecha_evaluacion;
    }

    public void setFecha_evaluacion(LocalDate fecha_evaluacion) {
        this.fecha_evaluacion = fecha_evaluacion;
    }

    public Long getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(Long id_usuario) {
        this.id_usuario = id_usuario;
    }

    public Long getId_examen() {
        return id_examen;
    }

    public void setId_examen(Long id_examen) {
        this.id_examen = id_examen;
    }

    public int getTemas() {
        return temas.size();
    }

}
