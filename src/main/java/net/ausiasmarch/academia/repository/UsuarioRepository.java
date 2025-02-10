package net.ausiasmarch.academia.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import net.ausiasmarch.academia.entity.UsuarioEntity;

public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long> {
    Page<UsuarioEntity> findByNombreContainingOrApellidosContainingOrCorreoContainingOrTipousuarioContaining (
            String nombre, String apellidos, String correo, String tipousuario, Pageable oPageable);

    Page<UsuarioEntity> findByTipousuario(String tipousuario,Pageable oPageable);

    Optional<UsuarioEntity> findByCorreo(String correo);

    Optional<UsuarioEntity> findByCorreoAndPassword(String correo, String password);

    @Query(value = "SELECT u.* FROM usuario u WHERE u.id IN " + 
                        "(SELECT i.id_usuario FROM inscripcion i WHERE i.id_curso IN " + 
                            "(SELECT i2.id_curso FROM inscripcion i2 WHERE i2.id_usuario = :idProfesor)) AND u.tipousuario = 'Estudiante'", nativeQuery = true)
    Page<UsuarioEntity> findAlumnosByProfesor(@Param("idProfesor") Long idProfesor, Pageable oPageable);

    @Query(value = "SELECT u.* FROM usuario u WHERE u.id IN " + 
    "(SELECT i.id_usuario FROM inscripcion i WHERE i.id_curso IN " + 
        "(SELECT i2.id_curso FROM inscripcion i2 WHERE i2.id_usuario = :idProfesor)) AND u.tipousuario = 'Estudiante' AND (u.nombre LIKE %:filtro% OR u.apellidos LIKE %:filtro%)", nativeQuery = true)
    Page<UsuarioEntity> findAlumnosByProfesor(@Param("idProfesor") Long idProfesor, @Param("filtro") String filtro, Pageable oPageable);
    
    //

    @Query(value = "SELECT u.* FROM usuario u WHERE u.id IN " + 
    "(SELECT i.id_usuario FROM inscripcion i WHERE i.id_curso IN " + 
        "(SELECT i2.id_curso FROM inscripcion i2 WHERE i2.id_usuario = :idProfesor)) AND u.tipousuario = 'Estudiante' AND u.id = :id_alumno", nativeQuery = true)
    Optional<UsuarioEntity> findAlumnoByProfesor(@Param("idProfesor") Long idProfesor, @Param("id_alumno") Long id_alumno);

}
