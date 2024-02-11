package org.laurichapp.servicecommande.dtos;

import jakarta.validation.constraints.NotNull;
import org.laurichapp.servicecommande.models.Produit;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public record CommandeDTO(@NotNull UUID _idCommande, Date date_creation, String id_utillisateur, String id_paiement, Double total, List<Produit> produitList, String etat_livraison, String statut_paiement, String numero) {
}
