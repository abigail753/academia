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

import net.ausiasmarch.academia.entity.ExamenEntity;
import net.ausiasmarch.academia.service.ExamenService;

@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
@RestController
@RequestMapping("/examen")
public class Examen {
    @Autowired
    ExamenService oExamenService;

    // Random
    @PutMapping("/random/{cantidad}")
    public ResponseEntity<Long> create(@PathVariable Long cantidad) {
        return new ResponseEntity<Long>(oExamenService.randomCreate(cantidad), HttpStatus.OK);
    }

    // Cargar
    @GetMapping("")
    public ResponseEntity<Page<ExamenEntity>> getPage(
            Pageable oPageable,
            @RequestParam Optional<String> filter) {
        return new ResponseEntity<Page<ExamenEntity>>(oExamenService.getPage(oPageable, filter), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExamenEntity> getExamen(@PathVariable Long id) {
        return new ResponseEntity<ExamenEntity>(oExamenService.get(id), HttpStatus.OK);
    }

    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        return new ResponseEntity<Long>(oExamenService.count(), HttpStatus.OK);
    }

    // Crear
    @PostMapping("")
    public ResponseEntity<ExamenEntity> create(@RequestBody ExamenEntity oExamenEntity) {
        return new ResponseEntity<ExamenEntity>(oExamenService.create(oExamenEntity), HttpStatus.OK);
    }

    // Eliminar
    @DeleteMapping("/{id}")
    public ResponseEntity<Long> delete(@PathVariable Long id) {
        return new ResponseEntity<Long>(oExamenService.delete(id), HttpStatus.OK);
    }

    // Editar
    @PutMapping("")
    public ResponseEntity<ExamenEntity> update(@RequestBody ExamenEntity oExamenEntity) {
        return new ResponseEntity<ExamenEntity>(oExamenService.update(oExamenEntity), HttpStatus.OK);
    }


}
