package org.laurichapp.servicecommande.models;

import jakarta.persistence.*;
import org.laurichapp.servicecommande.dtos.ProduitDTO;

import java.util.List;

@Entity
@Table(name = "PRODUIT")
public class Produit {

    @Id
    private String id_produit;
    private Double prix_unitaire;
    private String sexe, taille, description;
    private int quantite;

    @ManyToOne
    private Couleur couleur;
    @ManyToOne
    private Categorie categorie;

    @ManyToMany(mappedBy = "listProduits")
    private List<Panier> listPaniers;

    @ManyToMany(mappedBy = "produitList")
    private List<Commande> listCommandes;

    public static ProduitDTO toDTO(Produit produit) {
        return new ProduitDTO(
                produit.getId_produit(),
                produit.getPrix_unitaire(),
                produit.getSexe(),
                produit.getTaille(),
                produit.getDescription(),
                produit.getQuantite(),
                produit.getCouleur(),
                produit.getCategorie()
        );
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

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
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

    public List<Panier> getListPaniers() {
        return listPaniers;
    }

    public void setListPaniers(List<Panier> listPaniers) {
        this.listPaniers = listPaniers;
    }

    public List<Commande> getListCommandes() {
        return listCommandes;
    }

    public void setListCommandes(List<Commande> listCommandes) {
        this.listCommandes = listCommandes;
    }
}
