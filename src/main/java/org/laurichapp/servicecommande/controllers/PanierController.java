package org.laurichapp.servicecommande.controllers;

import jakarta.validation.Valid;
import org.laurichapp.servicecommande.dtos.in.AjouterProduitDTO;
import org.laurichapp.servicecommande.dtos.in.UpdateProduitDTO;
import org.laurichapp.servicecommande.dtos.out.PanierDTO;
import org.laurichapp.servicecommande.exceptions.PanierNotFoundException;
import org.laurichapp.servicecommande.exceptions.ProduitPasDansPanierException;
import org.laurichapp.servicecommande.facades.FacadePanier;
import org.laurichapp.servicecommande.models.Panier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/paniers")
public class PanierController {

    private final FacadePanier facadePanier;

    public PanierController(FacadePanier facadePanier) {
        this.facadePanier = facadePanier;
    }

    /*========== GET ==========*/

    @GetMapping("/{token}")
    public ResponseEntity<PanierDTO> getPanier(@PathVariable String token) {
        try {
            Panier panier = facadePanier.getPanier(token);
            return ResponseEntity.ok(Panier.toDTO(panier));
        } catch (PanierNotFoundException p) {
            return ResponseEntity.notFound().build();
        }
    }

    /*========== POST ==========*/

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<PanierDTO> createPanier(@Valid @RequestBody AjouterProduitDTO ajouterProduitDTO) {
        Panier panier =  facadePanier.createPanier(ajouterProduitDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(Panier.toDTO(panier));
    }

    @PostMapping("/{token}/valider_commande")
    public ResponseEntity<Void> createCommandeFromPanier(@PathVariable(name = "token") String token, Principal principal) {
        try {
            facadePanier.createCommandeFromPanier(token, principal.getName());
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        catch (PanierNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{token}")
    public ResponseEntity<PanierDTO> addProduit(@PathVariable String token, @Valid @RequestBody AjouterProduitDTO ajouterProduitDTO) {
        try {
            Panier panier = facadePanier.addProduit(token, ajouterProduitDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(Panier.toDTO(panier));
        } catch (PanierNotFoundException p) {
            return ResponseEntity.notFound().build();
        }
    }

    /*========== PUT ==========*/

    @PutMapping("/{token}/produits/{id}")
    public ResponseEntity<PanierDTO> updateProduit(@PathVariable String token, @PathVariable String id, @Valid @RequestBody UpdateProduitDTO updateProduitDTO) {
        Panier panier = null;
        try {
            panier = facadePanier.updateProduit(token, Integer.parseInt(id), updateProduitDTO);
        } catch (ProduitPasDansPanierException | PanierNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(Panier.toDTO(panier));
    }

    /*========== DELETE ==========*/

    @DeleteMapping("/{token}")
    public ResponseEntity<Void> deletePanier(@PathVariable String token) {
        try {
            facadePanier.deletePanier(token);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (PanierNotFoundException p) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{token}/produits/{id}/couleurs/{couleur}")
    public ResponseEntity<Void> deleteProduitPanier(@PathVariable String token, @PathVariable String id, @PathVariable String couleur) {
        try {
            facadePanier.deleteProduitPanier(token, Integer.parseInt(id), couleur);
        } catch (ProduitPasDansPanierException | PanierNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
