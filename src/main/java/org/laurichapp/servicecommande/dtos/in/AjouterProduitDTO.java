package org.laurichapp.servicecommande.dtos.in;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record AjouterProduitDTO(@Valid @NotNull @Min(1) int id, @Valid @NotNull String couleur_choisi, @Valid @NotNull int quantite) {
}
