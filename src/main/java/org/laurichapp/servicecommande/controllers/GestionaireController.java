package org.laurichapp.servicecommande.controllers;

import org.apache.http.HttpStatus;
import org.laurichapp.servicecommande.exceptions.EtatLivraisonInexistantException;
import org.laurichapp.servicecommande.facades.FacadeCommande;
import org.laurichapp.servicecommande.models.Commande;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/gestionnaires")
public class GestionaireController {

    private final FacadeCommande facadeCommande;

    public GestionaireController(FacadeCommande facadeCommande) {
        this.facadeCommande = facadeCommande;
    }
    /*========== GET ==========*/

    @GetMapping("commandes")
    @PreAuthorize("GESTIONNAIRE")
    public ResponseEntity<List<Commande>> getAllComandes() {
        List<Commande> commandes = facadeCommande.getAllCommandes();
        return ResponseEntity.ok(commandes);
    }

    @GetMapping("commandes/{id}")
    @PreAuthorize("GESTIONNAIRE")
    public ResponseEntity<Commande> getComandesById(@PathVariable String idCommande) {
        Commande commande = facadeCommande.getCommandeById(idCommande);
        return ResponseEntity.ok(commande);
    }

    /*========== POST ==========*/

    /*========== PUT ==========*/

    @GetMapping("/commandes/{id}/livraison")
    @PreAuthorize("GESTIONNAIRE")
    public ResponseEntity updateCommande(@PathVariable String idCommande,  @RequestBody Map map) {

        Commande commande = null;
        try {
            commande = facadeCommande.updateEtatLivraison(idCommande, map.get("etat_livraison").toString());
        } catch (EtatLivraisonInexistantException e) {
            return ResponseEntity.status(HttpStatus.SC_BAD_REQUEST).body("Etat de livraison inexistant");
        }
        return ResponseEntity.ok(commande);
    }

    /*========== DELETE ==========*/
}
