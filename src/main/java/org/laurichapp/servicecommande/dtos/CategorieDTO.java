package org.laurichapp.servicecommande.dtos;

import jakarta.validation.constraints.NotNull;

public record CategorieDTO(@NotNull String libelle) {
}
