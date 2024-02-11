package org.laurichapp.servicecommande.models;

import jakarta.persistence.*;
import org.laurichapp.servicecommande.dtos.PanierDTO;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "PANIER")
public class Panier {

    @Id
    @GeneratedValue
    @Column(name = "_ID")
    private UUID _idPanier;

    private Date date_creation;
    private String token;
    @ManyToMany(mappedBy = "listPaniers")
    private List<Produit> listProduits;

    public static PanierDTO toDTO(Panier panier) {
        return new PanierDTO(panier.get_idPanier(),
                panier.getDate_creation(),
                panier.getToken(),
                panier.getListProduits());
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
}
