package org.laurichapp.servicecommande.controllers;

import org.laurichapp.servicecommande.facades.FacadeCommande;
import org.laurichapp.servicecommande.models.Commande;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/commandes")
public class CommandesController {

    private final FacadeCommande facadeCommande;

    public CommandesController(FacadeCommande facadeCommande) {
        this.facadeCommande = facadeCommande;
    }

    /*========== GET ==========*/
    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<Commande>> getAllCommandes(Principal principal) {
        List<Commande> commandes = facadeCommande.getAllCommandesUtilisateur(principal.getName());
        return ResponseEntity.ok(commandes);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Commande> getCommande(@PathVariable String idCommande, Principal principal) {
        Commande commandes = facadeCommande.getCommandeUtilisateurById(idCommande, principal.getName());
        return ResponseEntity.ok(commandes);
    }

    /*========== POST ==========*/

    /*========== PUT ==========*/

    /*========== DELETE ==========*/


}
