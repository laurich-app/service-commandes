package org.laurichapp.servicecommande.dtos;

import jakarta.validation.constraints.NotNull;
import org.laurichapp.servicecommande.models.Produit;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public record PanierDTO(@NotNull UUID _idPanier, Date date_creation, String token, List<Produit> listProduits) {
}
