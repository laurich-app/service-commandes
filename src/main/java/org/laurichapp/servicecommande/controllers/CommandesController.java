package org.laurichapp.servicecommande.controllers;

import org.laurichapp.servicecommande.facades.FacadeCommande;
import org.laurichapp.servicecommande.facades.FacadePanier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/commandes")
public class CommandesController {

    private final FacadeCommande facadeCommande;
    private final FacadePanier facadePanier;

    public CommandesController(FacadeCommande facadeCommande, FacadePanier facadePanier) {
        this.facadeCommande = facadeCommande;
        this.facadePanier = facadePanier;
    }

    @GetMapping
    @PreAuthorize("hasRole('GESTIONNAIRE')")
    public ResponseEntity get() {
        // TODO
        return ResponseEntity.ok().build();
    }
}
