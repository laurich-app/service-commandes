package org.laurichapp.servicecommande.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/commandes")
public class CommandesController {

    @GetMapping
    public ResponseEntity get() {
        // TO DO
        return ResponseEntity.ok().build();
    }
}
