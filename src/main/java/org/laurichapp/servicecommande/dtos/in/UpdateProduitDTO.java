package org.laurichapp.servicecommande.dtos.in;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record UpdateProduitDTO(@Valid @NotNull String couleur_choisi, @Valid @NotNull int quantite) {
}
