package org.laurichapp.servicecommande.models.paniers;

import org.laurichapp.servicecommande.dtos.in.AjouterProduitDTO;
import org.laurichapp.servicecommande.dtos.out.CouleurDTO;
import org.laurichapp.servicecommande.dtos.out.ProduitPanierResponseDTO;

public class Produit {
    private int id_produit_catalogue;
    private Couleur couleur;
    private int quantite;

    public Produit() {
    }

    public int getId_produit_catalogue() {
        return id_produit_catalogue;
    }

    public void setId_produit_catalogue(int id_produit_catalogue) {
        this.id_produit_catalogue = id_produit_catalogue;
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

    public static ProduitPanierResponseDTO toProduitOutDTO(Produit p) {
        return new ProduitPanierResponseDTO(
                p.getId_produit_catalogue(),
                new CouleurDTO(p.getCouleur().getLibelle()),
                p.getQuantite()
        );
    }

    public static Produit fromDTO(AjouterProduitDTO a) {
        Produit p = new Produit();
        p.setCouleur(new Couleur());
        p.getCouleur().setLibelle(a.couleur_choisi());
        p.setId_produit_catalogue(a.id());
        p.setQuantite(a.quantite());
        return p;
    }
}
