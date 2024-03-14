package org.laurichapp.servicecommande.dtos.rabbits;

import java.io.Serializable;

public record ProduitCommandeDTO(int id_produit, String couleur, int quantite) implements Serializable {
}
