package net.ausiasmarch.academia.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "usuario")
public class UsuarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 3, max = 255)
    private String nombre;

    @NotNull
    @Size(min = 3, max = 255)
    private String apellidos;

    @Email
    private String correo;

    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY)
    private java.util.List<InscripcionEntity> inscripciones;

    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY)
    private java.util.List<CalificacionEntity> calificaciones;

    private String foto;

    public UsuarioEntity() {
    }

    public UsuarioEntity(String nombre, String apellidos, String correo, String foto) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.correo = correo;
        this.foto = foto;
    }

    public UsuarioEntity(Long id, String nombre, String apellidos, String correo, String foto) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.correo = correo;
        this.foto = foto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
    
    public int getInscripciones() {
        return inscripciones.size();
    }

    public int getCalificaciones() {
        return calificaciones.size();
    }

}