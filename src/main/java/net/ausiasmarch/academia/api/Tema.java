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

import net.ausiasmarch.academia.entity.TemaEntity;
import net.ausiasmarch.academia.service.TemaService;

@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
@RestController
@RequestMapping("/tema")
public class Tema {
    @Autowired
    TemaService oTemaService;

    // Random
    @PutMapping("/random/{cantidad}")
    public ResponseEntity<Long> create(@PathVariable Long cantidad) {
        return new ResponseEntity<Long>(oTemaService.randomCreate(cantidad), HttpStatus.OK);
    }

    // Cargar
    @GetMapping("")
    public ResponseEntity<Page<TemaEntity>> getPage(
            Pageable oPageable,
            @RequestParam Optional<String> filter) {
        return new ResponseEntity<Page<TemaEntity>>(oTemaService.getPage(oPageable, filter), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TemaEntity> getTema(@PathVariable Long id) {
        return new ResponseEntity<TemaEntity>(oTemaService.get(id), HttpStatus.OK);
    }

    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        return new ResponseEntity<Long>(oTemaService.count(), HttpStatus.OK);
    }

    // Crear
    @PutMapping("")
    public ResponseEntity<TemaEntity> create(@RequestBody TemaEntity oTemaEntity) {
        return new ResponseEntity<TemaEntity>(oTemaService.create(oTemaEntity), HttpStatus.OK);
    }

    // Eliminar
    @DeleteMapping("/{id}")
    public ResponseEntity<Long> delete(@PathVariable Long id) {
        return new ResponseEntity<Long>(oTemaService.delete(id), HttpStatus.OK);
    }

    // Editar
    @PostMapping("")
    public ResponseEntity<TemaEntity> update(@RequestBody TemaEntity oTemaEntity) {
        return new ResponseEntity<TemaEntity>(oTemaService.update(oTemaEntity), HttpStatus.OK);
    }


}
