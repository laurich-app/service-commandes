package org.laurichapp.servicecommande.model;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Panier {

    private UUID _idPanier;

    private Date date_creation;
    private String TOKEN;
    private List<Produit> listProduits;

    public Panier(UUID _idPanier, Date date_creation, String TOKEN, List<Produit> listProduits) {
        this._idPanier = _idPanier;
        this.date_creation = date_creation;
        this.TOKEN = TOKEN;
        this.listProduits = listProduits;
    }

    public UUID get_idPanier() {
        return _idPanier;
    }

    public void set_idPanier(UUID _idPanier) {
        this._idPanier = _idPanier;
    }

    public Date getDate_creation() {
        return date_creation;
    }

    public void setDate_creation(Date date_creation) {
        this.date_creation = date_creation;
    }

    public String getTOKEN() {
        return TOKEN;
    }

    public void setTOKEN(String TOKEN) {
        this.TOKEN = TOKEN;
    }

    public List<Produit> getListProduits() {
        return listProduits;
    }

    public void setListProduits(List<Produit> listProduits) {
        this.listProduits = listProduits;
    }
}
