package org.laurichapp.servicecommande.controllers;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.laurichapp.servicecommande.dtos.out.CommandeDTO;
import org.laurichapp.servicecommande.dtos.pagination.Paginate;
import org.laurichapp.servicecommande.dtos.pagination.PaginateRequestDTO;
import org.laurichapp.servicecommande.exceptions.CommandeNotFoundException;
import org.laurichapp.servicecommande.facades.FacadeCommande;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Set;

@RestController
@RequestMapping("/commandes")
public class CommandesController {

    private final FacadeCommande facadeCommande;
    private final Validator validator;

    public CommandesController(FacadeCommande facadeCommande, @Autowired Validator validator) {
        this.facadeCommande = facadeCommande;
        this.validator = validator;
    }

    /*========== GET ==========*/
    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Paginate<CommandeDTO>> getAllCommandes(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "limit", defaultValue = "10", required = false) int limit,
            @RequestParam(name = "sort", required = false) String sort,
            @RequestParam(name = "sortDirection", required = false) Sort.Direction sortDirection,
            Principal principal) {

        PaginateRequestDTO paginateRequest = new PaginateRequestDTO(page, limit, sort, sortDirection);

        Set<ConstraintViolation<PaginateRequestDTO>> violations = this.validator.validate(paginateRequest);
        if(!violations.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        Paginate<CommandeDTO> commandes = facadeCommande.getAllCommandesUtilisateur(principal.getName(), paginateRequest);
        return ResponseEntity.ok(commandes);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<CommandeDTO> getCommande(@PathVariable String id, Principal principal) {
        try {
            CommandeDTO commande = facadeCommande.getCommandeDTOUtilisateurById(id, principal.getName());
            return ResponseEntity.ok(commande);
        } catch (CommandeNotFoundException c) {
            return ResponseEntity.notFound().build();
        }
    }

    /*========== POST ==========*/

    /*========== PUT ==========*/

    /*========== DELETE ==========*/


}
