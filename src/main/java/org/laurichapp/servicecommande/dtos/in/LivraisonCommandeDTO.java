package org.laurichapp.servicecommande.dtos.in;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.laurichapp.servicecommande.enums.EtatsLivraison;

public record LivraisonCommandeDTO(@Valid @NotNull EtatsLivraison etat_livraison) {
}
