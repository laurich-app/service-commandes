package org.laurichapp.servicecommande.models.commandes;

import org.laurichapp.servicecommande.dtos.out.CategorieOutDTO;
import org.laurichapp.servicecommande.dtos.out.CouleurDTO;
import org.laurichapp.servicecommande.dtos.out.ProduitCommandeResponseDTO;

public class Produit {
    private double prix_unitaire;
    private String sexe;
    private String taille;
    private String description;
    private Couleur couleur;
    private Categorie categorie;
    private int quantite;

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    public double getPrix_unitaire() {
        return prix_unitaire;
    }

    public void setPrix_unitaire(double prix_unitaire) {
        this.prix_unitaire = prix_unitaire;
    }

    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public String getTaille() {
        return taille;
    }

    public void setTaille(String taille) {
        this.taille = taille;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Couleur getCouleur() {
        return couleur;
    }

    public void setCouleur(Couleur couleur) {
        this.couleur = couleur;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public static ProduitCommandeResponseDTO toProduitDTO(Produit p) {
        return new ProduitCommandeResponseDTO(
            p.getPrix_unitaire(),
            p.getSexe(),
            p.getTaille(),
            p.getDescription(),
            new CouleurDTO(p.getCouleur().getLibelle()),
            p.getQuantite(),
            new CategorieOutDTO(p.getCategorie().getLibelle())
        );
    }
}
