package net.ausiasmarch.academia.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import net.ausiasmarch.academia.entity.CursoEntity;
import net.ausiasmarch.academia.entity.InscripcionEntity;
import net.ausiasmarch.academia.entity.UsuarioEntity;
import net.ausiasmarch.academia.exception.ResourceNotFoundException;
import net.ausiasmarch.academia.exception.UnauthorizedAccessException;
import net.ausiasmarch.academia.repository.CursoRepository;
import net.ausiasmarch.academia.repository.InscripcionRepository;

@Service
public class CursoService implements ServiceInterface<CursoEntity> {
    @Autowired
    CursoRepository oCursoRepository;

    @Autowired
    RandomService oRandomService;

    @Autowired
    AuthService oAuthService;

    @Autowired
    InscripcionRepository oInscripcionRepository;

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

            oCursoEntity.setNombre(arrNombres[oRandomService.getRandomInt(0, arrNombres.length - 1)]);
            oCursoEntity.setDescripcion(arrDescripcion[oRandomService.getRandomInt(0, arrDescripcion.length - 1)]);

            oCursoRepository.save(oCursoEntity);
        }
        return oCursoRepository.count();
    }

    // Cargar datos
    public Page<CursoEntity> getPage(Pageable oPageable, Optional<String> filter) {
        if (oAuthService.isAdmin()) {
            if (filter.isPresent()) {
                return oCursoRepository
                        .findByNombreContainingOrDescripcion(
                                filter.get(), filter.get(), oPageable);
            } else {
                return oCursoRepository.findAll(oPageable);
            }
        } else if (oAuthService.isProfesor()) {

            UsuarioEntity oProfesor = oAuthService.getUsuarioFromToken();

            if (filter.isPresent()) {
                return oCursoRepository.findByNombreContainingOrDescripcion(oProfesor.getId(), filter.get(), oPageable);
            } else {
                return oCursoRepository.findByProfesor(oProfesor.getId(), oPageable);
            }
        } else {
            throw new UnauthorizedAccessException("No tienes permisos para todo el listado de cursos.");
        }
    }

    public CursoEntity get(Long id) {

        if (oAuthService.isAdmin()) {
            return oCursoRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Curso no encontrado"));
        }

        if (oAuthService.isProfesor()) {
            UsuarioEntity oUsuarioEntity = oAuthService.getUsuarioFromToken();

            CursoEntity oCursoEntity = oCursoRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Curso no encontrado"));

            if (oInscripcionRepository.findByUsuarioIdAndCursoId(oUsuarioEntity.getId(), oCursoEntity.getId()) == null) {
                throw new UnauthorizedAccessException("No tienes permisos para ver este curso");
            } else {
                return oCursoEntity;
            }
        }

        throw new UnauthorizedAccessException("No tienes permisos para ver este curso.");

    }

    // Contar
    public Long count() {
        return oCursoRepository.count();
    }

    // Crear
    public CursoEntity create(CursoEntity oCursoEntity) {

        if (oAuthService.isAdmin()) {
            return oCursoRepository.save(oCursoEntity);
        }

        if (oAuthService.isProfesor()) {
            oCursoRepository.save(oCursoEntity);
            InscripcionEntity oInscripcionEntity = new InscripcionEntity(oAuthService.getUsuarioFromToken(),
                    oCursoEntity);
            oInscripcionRepository.save(oInscripcionEntity);

            return oCursoEntity;
        }

        throw new UnauthorizedAccessException("No tienes permisos para crear cursos.");

    }

    // Actualizar
    public CursoEntity update(CursoEntity oCursoEntity) {

        CursoEntity oCursoEntityFromDatabase = oCursoRepository.findById(oCursoEntity.getId()).get();

        if (oAuthService.isAdmin()) {
            if (oCursoEntity.getNombre() != null) {
                oCursoEntityFromDatabase.setNombre(oCursoEntity.getNombre());
            }

            if (oCursoEntity.getDescripcion() != null) {
                oCursoEntityFromDatabase.setDescripcion(oCursoEntity.getDescripcion());
            }

            return oCursoRepository.save(oCursoEntityFromDatabase);
        }

        if (oAuthService.isProfesor()) {

            UsuarioEntity oUsuarioEntity = oAuthService.getUsuarioFromToken();

            if (oInscripcionRepository.findByUsuarioIdAndCursoId(oUsuarioEntity.getId(),
                    oCursoEntity.getId()) == null) {
                throw new UnauthorizedAccessException("No tienes permisos para editar este curso");
            } else {
                if (oCursoEntity.getNombre() != null) {
                    oCursoEntityFromDatabase.setNombre(oCursoEntity.getNombre());
                }

                if (oCursoEntity.getDescripcion() != null) {
                    oCursoEntityFromDatabase.setDescripcion(oCursoEntity.getDescripcion());
                }

                return oCursoRepository.save(oCursoEntityFromDatabase);
            }

        }

        throw new UnauthorizedAccessException("No tienes permisos para crear cursos.");

    }

    // Eliminar
    public Long delete(Long id) {
        if (!oAuthService.isAdmin()) {
            throw new UnauthorizedAccessException("No tienes permisos para eliminar cursos.");
        }
        oCursoRepository.deleteById(id);
        return 1L;
    }

    // Random Selection
    public CursoEntity randomSelection() {
        return oCursoRepository.findById((long) oRandomService.getRandomInt(1, (int) (long) this.count())).get();
    }
}
