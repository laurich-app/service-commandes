package org.laurichapp.servicecommande.controllers;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import org.laurichapp.servicecommande.dtos.in.LivraisonCommandeDTO;
import org.laurichapp.servicecommande.dtos.out.CommandeDTO;
import org.laurichapp.servicecommande.dtos.pagination.Paginate;
import org.laurichapp.servicecommande.dtos.pagination.PaginateRequestDTO;
import org.laurichapp.servicecommande.exceptions.CommandeNotFoundException;
import org.laurichapp.servicecommande.facades.FacadeCommande;
import org.laurichapp.servicecommande.models.Commande;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/gestionnaires")
public class GestionaireController {

    private final FacadeCommande facadeCommande;
    private final Validator validator;

    public GestionaireController(FacadeCommande facadeCommande, @Autowired Validator validator) {
        this.facadeCommande = facadeCommande;
        this.validator = validator;
    }
    /*========== GET ==========*/

    @GetMapping("/commandes")
    @PreAuthorize("hasRole('GESTIONNAIRE')")
    public ResponseEntity<Paginate<CommandeDTO>> getAllComandes(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "limit", defaultValue = "10", required = false) int limit,
            @RequestParam(name = "sort", required = false) String sort,
            @RequestParam(name = "sortDirection", required = false) Sort.Direction sortDirection
    ) {
        PaginateRequestDTO paginateRequest = new PaginateRequestDTO(page, limit, sort, sortDirection);

        Set<ConstraintViolation<PaginateRequestDTO>> violations = this.validator.validate(paginateRequest);
        if(!violations.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        Paginate<CommandeDTO> commandes = facadeCommande.getAllCommandes(paginateRequest);
        return ResponseEntity.ok(commandes);
    }

    @GetMapping("/commandes/{id}")
    @PreAuthorize("hasRole('GESTIONNAIRE')")
    public ResponseEntity<CommandeDTO> getComandesById(@PathVariable String id) {
        try {
            Commande commande = facadeCommande.getCommandeById(id);
            return ResponseEntity.ok(Commande.toDTO(commande));
        } catch (CommandeNotFoundException c){
            return ResponseEntity.notFound().build();
        }
    }

    /*========== POST ==========*/

    /*========== PUT ==========*/

    @PutMapping("/commandes/{id}/livraison")
    @PreAuthorize("hasRole('GESTIONNAIRE')")
    public ResponseEntity<CommandeDTO> updateCommande(@PathVariable String id, @Valid @RequestBody LivraisonCommandeDTO livraisonCommandeDTO) {
        Commande commande = null;
        try {
            commande = facadeCommande.updateEtatLivraison(id, livraisonCommandeDTO.etat_livraison());
        } catch (CommandeNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(Commande.toDTO(commande));
    }

    /*========== DELETE ==========*/
}
