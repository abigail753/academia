package net.ausiasmarch.academia.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "calificacion")
public class CalificacionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Digits(integer = 2, fraction = 2)
    private BigDecimal calificacion;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate fecha_evaluacion;

    @NotNull
    @ManyToOne(fetch = jakarta.persistence.FetchType.EAGER)
    @JoinColumn(name = "id_usuario")
    private UsuarioEntity usuario;

    @NotNull
    @ManyToOne(fetch = jakarta.persistence.FetchType.EAGER)
    @JoinColumn(name = "id_examen")
    private ExamenEntity examen;

    @NotNull
    @ManyToOne(fetch = jakarta.persistence.FetchType.EAGER)
    @JoinColumn(name = "id_tema")
    private TemaEntity tema;

    public CalificacionEntity() {
    }

    public CalificacionEntity(BigDecimal calificacion, LocalDate fecha_evaluacion, UsuarioEntity usuario,
            ExamenEntity examen, TemaEntity tema) {
        this.calificacion = calificacion;
        this.fecha_evaluacion = fecha_evaluacion;
        this.usuario = usuario;
        this.examen = examen;
        this.tema = tema;
    }

    public CalificacionEntity(Long id, BigDecimal calificacion, LocalDate fecha_evaluacion, UsuarioEntity usuario,
            ExamenEntity examen, TemaEntity tema) {
        this.id = id;
        this.calificacion = calificacion;
        this.fecha_evaluacion = fecha_evaluacion;
        this.usuario = usuario;
        this.examen = examen;
        this.tema = tema;
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

    public UsuarioEntity getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioEntity usuario) {
        this.usuario = usuario;
    }

    public ExamenEntity getExamen() {
        return examen;
    }


    public void setExamen(ExamenEntity examen) {
        this.examen = examen;
    }

    public TemaEntity getTema() {
        return tema;
    }

    public void setTema(TemaEntity tema) {
        this.tema = tema;
    }

}
