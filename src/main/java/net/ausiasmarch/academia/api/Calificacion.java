package net.ausiasmarch.academia.api;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.ausiasmarch.academia.entity.CalificacionEntity;
import net.ausiasmarch.academia.service.CalificacionService;

@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
@RestController
@RequestMapping("/calificacion")
public class Calificacion {
    @Autowired
    CalificacionService oCalificacionService;

    // Random
    @PutMapping("/random/{cantidad}")
    public ResponseEntity<Long> create(@PathVariable Long cantidad) {
        return new ResponseEntity<Long>(oCalificacionService.randomCreate(cantidad), HttpStatus.OK);
    }

    // Cargar Page
    @GetMapping("")
    public ResponseEntity<Page<CalificacionEntity>> getPage(
            Pageable oPageable,
            @RequestParam Optional<String> filter) {
        return new ResponseEntity<Page<CalificacionEntity>>(oCalificacionService.getPage(oPageable, filter), HttpStatus.OK);
    }

    @GetMapping("/xusuario/{id}")
    public ResponseEntity<Page<CalificacionEntity>> getPageXUsuario(
            Pageable oPageable,
            @RequestParam Optional<String> filter,
            @PathVariable Optional<Long> id) {
        return new ResponseEntity<Page<CalificacionEntity>>(oCalificacionService.getPageXUsuario(oPageable, filter, id),
                HttpStatus.OK);
    }

    @GetMapping("/xexamen/{id}")
    public ResponseEntity<Page<CalificacionEntity>> getPageXExamen(
            Pageable oPageable,
            @RequestParam Optional<String> filter,
            @PathVariable Optional<Long> id) {
        return new ResponseEntity<Page<CalificacionEntity>>(oCalificacionService.getPageXExamen(oPageable, filter, id),
                HttpStatus.OK);
    }

    @GetMapping("/xtema/{id}")
    public ResponseEntity<Page<CalificacionEntity>> getPageXTema(
            Pageable oPageable,
            @RequestParam Optional<String> filter,
            @PathVariable Optional<Long> id) {
        return new ResponseEntity<Page<CalificacionEntity>>(oCalificacionService.getPageXTema(oPageable, filter, id),
                HttpStatus.OK);
    }

    // Cargar un Usuario
    @GetMapping("/{id}")
    public ResponseEntity<CalificacionEntity> getCalificacion(@PathVariable Long id) {
        return new ResponseEntity<CalificacionEntity>(oCalificacionService.get(id), HttpStatus.OK);
    }

    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        return new ResponseEntity<Long>(oCalificacionService.count(), HttpStatus.OK);
    }

    // Crear
    @PostMapping("")
    public ResponseEntity<CalificacionEntity> create(@RequestBody CalificacionEntity oCalificacionEntity) {
        return new ResponseEntity<CalificacionEntity>(oCalificacionService.create(oCalificacionEntity), HttpStatus.OK);
    }

    // Eliminar
    @DeleteMapping("/{id}")
    public ResponseEntity<Long> delete(@PathVariable Long id) {
        return new ResponseEntity<Long>(oCalificacionService.delete(id), HttpStatus.OK);
    }

    // Editar
    @PutMapping("")
    public ResponseEntity<CalificacionEntity> update(@RequestBody CalificacionEntity oCalificacionEntity) {
        return new ResponseEntity<CalificacionEntity>(oCalificacionService.update(oCalificacionEntity), HttpStatus.OK);
    }


}
