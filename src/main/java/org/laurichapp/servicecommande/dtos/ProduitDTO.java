package org.laurichapp.servicecommande.dtos;


import org.laurichapp.servicecommande.models.Couleur;

public record ProduitDTO(int id_produit, String couleur, int quantite) {
}
