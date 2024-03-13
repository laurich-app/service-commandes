package org.laurichapp.servicecommande.dtos.out;

public record ProduitCommandeResponseDTO(double prix_unitaire, String sexe, String taille, String description, CouleurDTO couleur, int quantite, CategorieOutDTO categorie) {
}
