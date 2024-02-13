package org.laurichapp.servicecommande.models;


import org.bson.types.ObjectId;
import org.laurichapp.servicecommande.dtos.PanierDTO;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Document(collection = "paniers")
public class Panier {

    @Id
    private ObjectId _idPanier;

    private Date date_creation = new Date(System.currentTimeMillis());
    private String token;

    private List<Produit> listProduits = new ArrayList<>();

    public static PanierDTO toDTO(Panier panier) {
        return new PanierDTO(panier.get_idPanier().toString(),
                panier.getDate_creation(),
                panier.getToken(),
                panier.getListProduits());
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

    public List<Produit> getListProduits() {
        return listProduits;
    }

    public void setListProduits(List<Produit> listProduits) {
        this.listProduits = listProduits;
    }

    public void addProduitListProduits(Produit produit) {
        this.listProduits.add(produit);
    }
}
