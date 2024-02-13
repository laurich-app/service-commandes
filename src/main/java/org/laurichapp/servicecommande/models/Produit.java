package org.laurichapp.servicecommande.models;


import org.laurichapp.servicecommande.dtos.ProduitDTO;



public class Produit {

    private int id_produit;
    private int quantite;
    private String couleur;

    public Produit(int id_produit, String couleur, int quantite) {
        this.id_produit = id_produit;
        this.quantite = quantite;
        this.couleur = couleur;
    }

    public static ProduitDTO toDTO(Produit produit) {
        return new ProduitDTO(
                produit.getId_produit(),
                produit.getCouleur(),
                produit.getQuantite()

        );
    }

    public static Produit fromDTO(ProduitDTO produitDTO) {
        Produit p = new Produit(produitDTO.id_produit(), produitDTO.couleur(), produitDTO.quantite());
        return p;
    }

    public int getId_produit() {
        return id_produit;
    }

    public void setId_produit(int id_produit) {
        this.id_produit = id_produit;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public String getCouleur() {
        return couleur;
    }

    public void setCouleur(String couleur) {
        this.couleur = couleur;
    }

}
