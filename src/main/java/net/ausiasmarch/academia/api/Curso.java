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

import net.ausiasmarch.academia.entity.CursoEntity;
import net.ausiasmarch.academia.entity.CursoEntity;
import net.ausiasmarch.academia.service.CursoService;

@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
@RestController
@RequestMapping("/curso")
public class Curso {
    
    @Autowired
    CursoService oCursoService;

    // Random
    @PutMapping("/random/{cantidad}")
    public ResponseEntity<Long> create(@PathVariable Long cantidad) {
        return new ResponseEntity<Long>(oCursoService.randomCreate(cantidad), HttpStatus.OK);
    }

    // Cargar
    @GetMapping("")
    public ResponseEntity<Page<CursoEntity>> getPage(
            Pageable oPageable,
            @RequestParam Optional<String> filter) {
        return new ResponseEntity<Page<CursoEntity>>(oCursoService.getPage(oPageable, filter), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CursoEntity> getCurso(@PathVariable Long id) {
        return new ResponseEntity<CursoEntity>(oCursoService.get(id), HttpStatus.OK);
    }

    // Contar
    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        return new ResponseEntity<Long>(oCursoService.count(), HttpStatus.OK);
    }

    // Crear
    @PutMapping("")
    public ResponseEntity<CursoEntity> create(@RequestBody CursoEntity oCursoEntity) {
        return new ResponseEntity<CursoEntity>(oCursoService.create(oCursoEntity), HttpStatus.OK);
    }

    // Editar
    @PostMapping("")
    public ResponseEntity<CursoEntity> update(@RequestBody CursoEntity oCursoEntity) {
        return new ResponseEntity<CursoEntity>(oCursoService.update(oCursoEntity), HttpStatus.OK);
    }

    // Eliminar
    @DeleteMapping("/{id}")
    public ResponseEntity<Long> delete(@PathVariable Long id) {
        return new ResponseEntity<Long>(oCursoService.delete(id), HttpStatus.OK);
    }

}
