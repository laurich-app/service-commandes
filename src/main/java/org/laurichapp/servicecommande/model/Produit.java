package org.laurichapp.servicecommande.model;

public class Produit {

    private String id_produit;
    private Double prix_unitaire;
    private String sexe, taille, description;
    private int quantité;

    private Couleur couleur;
    private Categorie categorie;

    public Produit(String id_produit, Double prix_unitaire, String sexe, String taille, String description, int quantité, Couleur couleur, Categorie categorie) {
        this.id_produit = id_produit;
        this.prix_unitaire = prix_unitaire;
        this.sexe = sexe;
        this.taille = taille;
        this.description = description;
        this.quantité = quantité;
        this.couleur = couleur;
        this.categorie = categorie;
    }

    public String getId_produit() {
        return id_produit;
    }

    public void setId_produit(String id_produit) {
        this.id_produit = id_produit;
    }

    public Double getPrix_unitaire() {
        return prix_unitaire;
    }

    public void setPrix_unitaire(Double prix_unitaire) {
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

    public int getQuantité() {
        return quantité;
    }

    public void setQuantité(int quantité) {
        this.quantité = quantité;
    }

    public Couleur getCouleur() {
        return couleur;
    }

    public void setCouleur(Couleur couleur) {
        this.couleur = couleur;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }
}
