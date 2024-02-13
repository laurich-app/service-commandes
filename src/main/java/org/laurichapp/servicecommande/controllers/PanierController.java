package org.laurichapp.servicecommande.controllers;

import org.laurichapp.servicecommande.facades.FacadeCommande;
import org.laurichapp.servicecommande.facades.FacadePanier;
import org.laurichapp.servicecommande.models.Panier;
import org.laurichapp.servicecommande.repositories.PanierRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/paniers")
public class PanierController {

    private final FacadePanier facadePanier;

    public PanierController(FacadePanier facadePanier) {
        this.facadePanier = facadePanier;
    }
    /*========== GET ==========*/


    /*========== POST ==========*/

    /*========== PUT ==========*/

    /*========== DELETE ==========*/
}
