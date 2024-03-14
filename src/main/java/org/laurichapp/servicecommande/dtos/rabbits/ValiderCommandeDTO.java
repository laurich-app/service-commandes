package org.laurichapp.servicecommande.dtos.rabbits;

import java.io.Serializable;
import java.util.List;

public record ValiderCommandeDTO(String id_commande, List<ProduitCommandeDTO> produits) implements Serializable {
}
