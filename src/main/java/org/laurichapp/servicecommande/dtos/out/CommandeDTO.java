package org.laurichapp.servicecommande.dtos.out;

import java.util.Date;
import java.util.List;

public record CommandeDTO(String _id, Date date_creation, String id_utillisateur, String id_paiement, Double total, List<ProduitCommandeResponseDTO> produits, String etat_livraison, String statut_paiement, String numero) {
}
