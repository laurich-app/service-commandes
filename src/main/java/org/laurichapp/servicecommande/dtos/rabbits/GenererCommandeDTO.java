package org.laurichapp.servicecommande.dtos.rabbits;

import java.io.Serializable;
import java.util.List;

public record GenererCommandeDTO(List<ProduitCatalogueDTO> produits, String id_commande) implements Serializable {
}
