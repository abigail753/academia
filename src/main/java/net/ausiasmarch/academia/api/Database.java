package net.ausiasmarch.academia.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.ausiasmarch.academia.service.DatabaseService;


@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
@RestController
@RequestMapping("/database")
public class Database {

    @Autowired
    DatabaseService oDatabaseService;

    @GetMapping("/fill")
    public ResponseEntity<Long> fill() {
        return new ResponseEntity<Long>(oDatabaseService.fill(), HttpStatus.OK);
    }


}
