package net.ausiasmarch.academia.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table (name="examen")
public class ExamenEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 3, max = 255)
    private String nombre;

    @NotNull
    private Long num_preguntas;

    @OneToMany(mappedBy = "examen", fetch = FetchType.LAZY)
    private java.util.List<CalificacionEntity> calificaciones;

    public ExamenEntity(){
    }

    public ExamenEntity(String nombre, Long num_preguntas) {
        this.nombre = nombre;
        this.num_preguntas = num_preguntas;
    }

    public ExamenEntity(Long id, String nombre, Long num_preguntas) {
        this.id = id;
        this.nombre = nombre;
        this.num_preguntas = num_preguntas;
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

    public Long getNum_preguntas() {
        return num_preguntas;
    }

    public void setNum_preguntas(Long num_preguntas) {
        this.num_preguntas = num_preguntas;
    }
    
    public int getCalificaciones() {
        return calificaciones.size();
    }

}
