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

import net.ausiasmarch.academia.entity.InscripcionEntity;
import net.ausiasmarch.academia.service.InscripcionService;

@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
@RestController
@RequestMapping("/inscripcion")
public class Inscripcion {
    
    @Autowired
    InscripcionService oInscripcionService;

    // Random
    @PutMapping("/random/{cantidad}")
    public ResponseEntity<Long> create(@PathVariable Long cantidad) {
        return new ResponseEntity<Long>(oInscripcionService.randomCreate(cantidad), HttpStatus.OK);
    }

    // Cargar
    @GetMapping("")
    public ResponseEntity<Page<InscripcionEntity>> getPage(
            Pageable oPageable,
            @RequestParam Optional<String> filter) {
        return new ResponseEntity<Page<InscripcionEntity>>(oInscripcionService.getPage(oPageable, filter), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InscripcionEntity> getInscripcion(@PathVariable Long id) {
        return new ResponseEntity<InscripcionEntity>(oInscripcionService.get(id), HttpStatus.OK);
    }

    // Contar
    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        return new ResponseEntity<Long>(oInscripcionService.count(), HttpStatus.OK);
    }

    // Crear
    @PutMapping("")
    public ResponseEntity<InscripcionEntity> create(@RequestBody InscripcionEntity oInscripcionEntity) {
        return new ResponseEntity<InscripcionEntity>(oInscripcionService.create(oInscripcionEntity), HttpStatus.OK);
    }

    // Editar
    @PostMapping("")
    public ResponseEntity<InscripcionEntity> update(@RequestBody InscripcionEntity oInscripcionEntity) {
        return new ResponseEntity<InscripcionEntity>(oInscripcionService.update(oInscripcionEntity), HttpStatus.OK);
    }

    // Eliminar
    @DeleteMapping("/{id}")
    public ResponseEntity<Long> delete(@PathVariable Long id) {
        return new ResponseEntity<Long>(oInscripcionService.delete(id), HttpStatus.OK);
    }

}
