package net.ausiasmarch.academia.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import net.ausiasmarch.academia.entity.CursoEntity;
import net.ausiasmarch.academia.exception.ResourceNotFoundException;
import net.ausiasmarch.academia.repository.CursoRepository;

@Service
public class CursoService implements ServiceInterface<CursoEntity> {
    @Autowired
    CursoRepository oCursoRepository;

    @Autowired
    RandomService oRandomService;

    // Cargar datos aleatorios
    private String[] arrProfesor = { "Luis", "María", "Javier", "Ana", "Miguel", "Sofía", "Carlos",
            "Laura", "José", "Patricia", "Fernando", "Isabel", "David", "Cristina", "Antonio", "Elena" };

    private String[] arrNombres = { "Matemáticas", "Java", "Historia", "Física", "Filosofía", "Inglés", "Marketing",
            "Photoshop", "Finanzas", "Web", "Python", "Diseño", "Proyectos", "Música",
            "Literatura", "Química" };

    private String[] arrDescripcion = {
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Phasellus lacinia quam vitae nunc.",
            "Pellentesque auctor neque nec urna. Proin sapien ipsum, porta a, auctor quis.",
            "Aenean commodo ligula eget dolor. Aenean massa. Cum sociis natoque penatibus.",
            "Sed cursus turpis vitae tortor. Donec posuere vulputate arcu. Phasellus accumsan cursus.",
            "Nam pretium turpis et arcu. Duis arcu tortor, suscipit eget, imperdiet nec.",
            "Fusce egestas elit eget lorem. Suspendisse nisl elit, rhoncus eget, elementum ac.",
            "Morbi nec metus. Phasellus blandit leo ut odio. Maecenas tincidunt lacus at velit.",
            "Quisque malesuada placerat nisl. Etiam sit amet orci eget eros faucibus tincidunt.",
            "Donec posuere vulputate arcu. Sed cursus turpis vitae tortor. Pellentesque posuere.",
            "Curabitur vestibulum aliquam leo. Praesent egestas tristique nibh. Etiam ultricies nisi.",
            "Aenean imperdiet. Etiam ultricies nisi vel augue. Nam eget dui. Etiam rhoncus.",
            "Donec mollis hendrerit risus. Phasellus nec sem in justo pellentesque facilisis.",
            "Sed lectus. Integer euismod lacus luctus magna. Aenean lectus elit, fermentum non.",
            "Duis arcu tortor, suscipit eget, imperdiet nec, imperdiet iaculis, ipsum. Suspendisse feugiat.",
            "Morbi mattis ullamcorper velit. Phasellus gravida semper nisi. Suspendisse potenti.",
            "Quisque rutrum. Integer tincidunt. Cras dapibus. Fusce ac turpis quis ligula lacinia aliquet."
    };

    public Long randomCreate(Long cantidad) {
        for (int i = 0; i < cantidad; i++) {
            CursoEntity oCursoEntity = new CursoEntity();

            oCursoEntity.setProfesor(arrProfesor[oRandomService.getRandomInt(0, arrProfesor.length - 1)]);
            oCursoEntity.setNombre(arrNombres[oRandomService.getRandomInt(0, arrNombres.length - 1)]);
            oCursoEntity.setDescripcion(arrDescripcion[oRandomService.getRandomInt(0, arrDescripcion.length - 1)]);

            oCursoRepository.save(oCursoEntity);
        }
        return oCursoRepository.count();
    }

    // Cargar datos
    public Page<CursoEntity> getPage(Pageable oPageable, Optional<String> filter) {

        if (filter.isPresent()) {
            return oCursoRepository
                    .findByProfesorContainingOrNombreContainingOrDescripcion(
                            filter.get(), filter.get(), filter.get(), oPageable);
        } else {
            return oCursoRepository.findAll(oPageable);
        }
    }

    public CursoEntity get(Long id) {
        return oCursoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Curso no encontrado"));
        // return oCursoRepository.findById(id).get();
    }

    // Contar
    public Long count() {
        return oCursoRepository.count();
    }

    // Crear
    public CursoEntity create(CursoEntity oCursoEntity) {
        return oCursoRepository.save(oCursoEntity);
    }

    // Actualizar
    public CursoEntity update(CursoEntity oCursoEntity) {
        CursoEntity oCursoEntityFromDatabase = oCursoRepository.findById(oCursoEntity.getId()).get();
        if (oCursoEntity.getProfesor() != null) {
            oCursoEntityFromDatabase.setProfesor(oCursoEntity.getProfesor());
        }

        if (oCursoEntity.getNombre() != null) {
            oCursoEntityFromDatabase.setNombre(oCursoEntity.getNombre());
        }

        if (oCursoEntity.getDescripcion() != null) {
            oCursoEntityFromDatabase.setDescripcion(oCursoEntity.getDescripcion());
        }
        return oCursoRepository.save(oCursoEntityFromDatabase);
    }

    // Eliminar
    public Long delete(Long id) {
        oCursoRepository.deleteById(id);
        return 1L;
    }
}
