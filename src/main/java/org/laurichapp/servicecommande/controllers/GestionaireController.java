package org.laurichapp.servicecommande.controllers;

import org.laurichapp.servicecommande.facades.FacadeCommande;
import org.laurichapp.servicecommande.facades.FacadePanier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/gestionnaires")
public class GestionaireController {

    private final FacadeCommande facadeCommande;

    public GestionaireController(FacadeCommande facadeCommande) {
        this.facadeCommande = facadeCommande;
    }
    /*========== GET ==========*/


    /*========== POST ==========*/

    /*========== PUT ==========*/

    /*========== DELETE ==========*/
}
