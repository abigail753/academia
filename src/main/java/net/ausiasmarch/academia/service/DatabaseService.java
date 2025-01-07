package net.ausiasmarch.academia.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class DatabaseService {

    @Autowired
    UsuarioService oUsuarioService;

    @Autowired
    TemaService oTemaService;

    @Autowired
    InscripcionService oInscripcionService;

    @Autowired
    ExamenService oExamenService;

    @Autowired
    CursoService oCursoService;

    @Autowired
    CalificacionService oCalificacionService;

    

    public Long fill() {
        oUsuarioService.randomCreate(10L);
        oCursoService.randomCreate(10L);
        oTemaService.randomCreate(10L);
        oExamenService.randomCreate(10L);
        oInscripcionService.randomCreate(5L);
        oCalificacionService.randomCreate(5L); 
        return 0L;
    }

}