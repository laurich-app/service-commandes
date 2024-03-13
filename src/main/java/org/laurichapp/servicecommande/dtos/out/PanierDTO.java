package org.laurichapp.servicecommande.dtos.out;

import java.util.Date;
import java.util.List;

public record PanierDTO(String _id, Date date_creation, String token, List<ProduitPanierResponseDTO> produits) {
}
