package org.laurichapp.servicecommande.controllers;

import org.laurichapp.servicecommande.dtos.ProduitDTO;
import org.laurichapp.servicecommande.exceptions.ProduitPasDansPanierException;
import org.laurichapp.servicecommande.facades.FacadePanier;
import org.laurichapp.servicecommande.models.Panier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;

@RestController
@RequestMapping("/paniers")
public class PanierController {

    private final FacadePanier facadePanier;

    public PanierController(FacadePanier facadePanier) {
        this.facadePanier = facadePanier;
    }

    /*========== GET ==========*/

    @GetMapping("/{token}")
    public ResponseEntity<Panier> getPanier(@PathVariable String token) {
        Panier panier = facadePanier.getPanier(token);
        return ResponseEntity.ok(panier);
    }

    /*========== POST ==========*/

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Panier> createPanier(@RequestBody ProduitDTO produitDTO) {
        Panier panier =  facadePanier.createPanier(produitDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(panier);
    }

    @PostMapping("/{token}/valider_commande")
    public ResponseEntity<String> createCommandeFromPanier(@PathVariable(name = "token") String token, Principal principal) {
        try {
            facadePanier.createCommandeFromPanier(token, principal.getName());
        }
        catch (Exception e) {
            return ResponseEntity.notFound().build(); // TODO
        }
        return ResponseEntity.status(HttpStatus.CREATED).body("Commande prise en compte");
    }

    @PostMapping("/{token}")
    public ResponseEntity<Panier> addProduit(@PathVariable String token, @RequestBody ProduitDTO produitDTO) {
        Panier panier = facadePanier.addProduit(token, produitDTO);
        return ResponseEntity.ok(panier);
    }

    /*========== PUT ==========*/

    @PutMapping("/{token}/produits/{id}")
    public ResponseEntity<Panier> updateProduit(@PathVariable String token, @PathVariable String id, @RequestBody Map map) {
        Panier panier = null;
        try {
            panier = facadePanier.updateProduit(token, Integer.parseInt(id), map.get("couleur").toString(), (Integer) map.get("quantite"), (Double) map.get("prix_unitaire"));
        } catch (ProduitPasDansPanierException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(panier);
    }

    /*========== DELETE ==========*/

    @DeleteMapping("/{token}")
    public ResponseEntity<Void> deletePanier(@PathVariable String token) {
        facadePanier.deletePanier(token);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/{token}/produits/{id}/couleurs/{couleur}")
    public ResponseEntity<Void> deleteProduitPanier(@PathVariable String token, @PathVariable String id, @PathVariable String couleur) {
        try {
            facadePanier.deleteProduitPanier(token, Integer.parseInt(id), couleur);
        } catch (ProduitPasDansPanierException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
