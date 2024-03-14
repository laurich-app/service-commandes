package org.laurichapp.servicecommande.dtos.out;

public record ProduitCommandeResponseDTO(double prix_unitaire, String sexe, String taille, String libelle, String description, CouleurDTO couleur, int quantite, CategorieOutDTO categorie) {
}
