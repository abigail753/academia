package net.ausiasmarch.academia.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
    @ManyToOne(fetch = jakarta.persistence.FetchType.EAGER)
    @JoinColumn(name = "id_curso")
    private CursoEntity curso;

    @OneToMany(mappedBy = "tema", fetch = FetchType.LAZY)
    private java.util.List<ExamenEntity> examenes;

    public TemaEntity() {
    }

    public TemaEntity(String titulo, String descripcion, CursoEntity id_curso) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.curso = id_curso;
    }

    public TemaEntity(Long id, String titulo, String descripcion, CursoEntity id_curso) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.curso = id_curso;;
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

    public CursoEntity getCurso() {
        return curso;
    }

    public void setCurso(CursoEntity curso) {
        this.curso = curso;
    }

    public int getCalificaciones() {
        return examenes.size();
    }

}
