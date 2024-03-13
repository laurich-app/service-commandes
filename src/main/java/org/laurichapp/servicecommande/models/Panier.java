package org.laurichapp.servicecommande.models;


import org.bson.types.ObjectId;
import org.laurichapp.servicecommande.dtos.out.PanierDTO;
import org.laurichapp.servicecommande.exceptions.ProduitPasDansPanierException;
import org.laurichapp.servicecommande.models.paniers.Produit;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Document(collection = "paniers")
public class Panier {

    @Id
    private ObjectId _idPanier;

    private Date date_creation = new Date(System.currentTimeMillis());
    private String token;

    private List<Produit> produits = new ArrayList<>();

    public static PanierDTO toDTO(Panier panier) {
        return new PanierDTO(panier.get_idPanier().toString(),
                panier.getDate_creation(),
                panier.getToken(),
                panier.getProduits().stream().map(Produit::toProduitOutDTO).collect(Collectors.toList()));
    }

    public ObjectId get_idPanier() {
        return _idPanier;
    }

    public void set_idPanier(ObjectId _idPanier) {
        this._idPanier = _idPanier;
    }

    public Date getDate_creation() {
        return date_creation;
    }

    public void setDate_creation(Date date_creation) {
        this.date_creation = date_creation;
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
            if (p.getId_produit_catalogue() == idProduit && p.getCouleur().getLibelle().equals(couleur)) {
                return p;
            }
        }
        throw new ProduitPasDansPanierException();
    }
}
