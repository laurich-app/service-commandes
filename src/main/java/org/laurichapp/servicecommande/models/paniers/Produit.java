package org.laurichapp.servicecommande.models.paniers;

import org.laurichapp.servicecommande.dtos.in.AjouterProduitDTO;
import org.laurichapp.servicecommande.dtos.out.CouleurDTO;
import org.laurichapp.servicecommande.dtos.out.ProduitPanierResponseDTO;
import org.laurichapp.servicecommande.dtos.rabbits.ProduitCommandeDTO;
import org.springframework.data.mongodb.core.mapping.Field;

public class Produit {
    @Field("id_produit_catalogue")
    private int idProduitCatalogue;
    private Couleur couleur;
    private int quantite;

    public Produit() {
        // NOP
    }

    public int getIdProduitCatalogue() {
        return idProduitCatalogue;
    }

    public void setIdProduitCatalogue(int idProduitCatalogue) {
        this.idProduitCatalogue = idProduitCatalogue;
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
                p.getIdProduitCatalogue(),
                new CouleurDTO(p.getCouleur().getLibelle()),
                p.getQuantite()
        );
    }

    public static Produit fromDTO(AjouterProduitDTO a) {
        Produit p = new Produit();
        p.setCouleur(new Couleur());
        p.getCouleur().setLibelle(a.couleur_choisi());
        p.setIdProduitCatalogue(a.id());
        p.setQuantite(a.quantite());
        return p;
    }

    /**
     * Lors de la validation d'une commande
     * @param produit
     * @return
     */
    public static ProduitCommandeDTO toProduitCommandeDTO(Produit produit) {
        return new ProduitCommandeDTO(
                produit.getIdProduitCatalogue(),
                produit.getCouleur().getLibelle(),
                produit.getQuantite()
        );
    }
}
