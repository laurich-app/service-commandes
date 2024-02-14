package org.laurichapp.servicecommande.dtos;

import org.laurichapp.servicecommande.models.Produit;

import java.util.Date;
import java.util.List;

public record CommandeDTO(String _idCommande, Date date_creation, String id_utillisateur, String id_paiement, Double total, List<Produit> produitList, String etat_livraison, String statut_paiement, String numero) {
}
