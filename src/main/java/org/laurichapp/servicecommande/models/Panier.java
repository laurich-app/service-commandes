package org.laurichapp.servicecommande.models;


import org.bson.types.ObjectId;
import org.laurichapp.servicecommande.dtos.out.PanierDTO;
import org.laurichapp.servicecommande.exceptions.ProduitPasDansPanierException;
import org.laurichapp.servicecommande.models.paniers.Produit;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Document(collection = "paniers")
public class Panier {

    @Id
    @Field("_id")
    private ObjectId idPanier;

    @Field("date_creation")
    private Date dateCreation = new Date(System.currentTimeMillis());
    private String token;

    private List<Produit> produits = new ArrayList<>();

    public static PanierDTO toDTO(Panier panier) {
        return new PanierDTO(panier.getIdPanier().toString(),
                panier.getDateCreation(),
                panier.getToken(),
                panier.getProduits().stream().map(Produit::toProduitOutDTO).toList());
    }

    public ObjectId getIdPanier() {
        return idPanier;
    }

    public void setIdPanier(ObjectId idPanier) {
        this.idPanier = idPanier;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<Produit> getProduits() {
        return produits;
    }

    public void setProduits(List<Produit> produits) {
        this.produits = produits;
    }

    public Produit getProduit(int idProduit, String couleur) throws ProduitPasDansPanierException {
        for (Produit p : this.produits) {
            if (p.getIdProduitCatalogue() == idProduit && p.getCouleur().getLibelle().equals(couleur)) {
                return p;
            }
        }
        throw new ProduitPasDansPanierException();
    }
}
