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

import java.util.UUID;

@RestController
@RequestMapping("/paniers")
public class PanierController {

    private final PanierRepository panierRepository;
    private final FacadePanier facadePanier;

    public PanierController(FacadePanier facadePanier, PanierRepository panierRepository) {
        this.facadePanier = facadePanier;
        this.panierRepository = panierRepository;
    }
    /*========== GET ==========*/

    @GetMapping
    public void enter() {
        Panier panier = new Panier();
        panier.setToken(UUID.randomUUID().toString());
        this.panierRepository.save(panier);
    }


    /*========== POST ==========*/

    /*========== PUT ==========*/

    /*========== DELETE ==========*/
}
