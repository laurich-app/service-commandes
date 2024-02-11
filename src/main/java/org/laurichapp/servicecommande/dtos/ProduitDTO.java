package org.laurichapp.servicecommande.dtos;

import org.laurichapp.servicecommande.models.Categorie;
import org.laurichapp.servicecommande.models.Couleur;

public record ProduitDTO(String id_produit, Double prix_unitaire, String sexe, String taille, String description, int quantite, Couleur couleur, Categorie categorie) {
}
