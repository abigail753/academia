package net.ausiasmarch.academia.entity;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;


@Entity
@Table (name="inscripcion")
public class InscripcionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(fetch = jakarta.persistence.FetchType.EAGER)
    @JoinColumn(name = "id_usuario")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private UsuarioEntity usuario;

    @NotNull
    @ManyToOne(fetch = jakarta.persistence.FetchType.EAGER)
    @JoinColumn(name = "id_curso")
    @OnDelete(action = OnDeleteAction.CASCADE)

    private CursoEntity curso;
    

    public InscripcionEntity() {
    }

    public InscripcionEntity(UsuarioEntity id_usuario, CursoEntity id_curso) {
        this.usuario = id_usuario;
        this.curso = id_curso;
    }

    public InscripcionEntity(Long id, UsuarioEntity id_usuario, CursoEntity id_curso) {
        this.id = id;
        this.usuario = id_usuario;
        this.curso = id_curso;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UsuarioEntity getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioEntity id_usuario) {
        this.usuario = id_usuario;
    }

    public CursoEntity getCurso() {
        return curso;
    }

    public void setCurso(CursoEntity id_curso) {
        this.curso = id_curso;
    }

}
