package org.laurichapp.servicecommande.model;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Commande {

    private UUID _idCommande;
    Date date_creation;
    private String id_utillisateur, id_paiement;
    Double total;
    private List<Produit> produitList;
    private String etat_livraison, statut_paiement;
    private String numero;

    public Commande(UUID _idCommande, Date date_creation, String id_utillisateur, String id_paiement, Double total, List<Produit> produitList, String etat_livraison, String statut_paiement, String numero) {
        this._idCommande = _idCommande;
        this.date_creation = date_creation;
        this.id_utillisateur = id_utillisateur;
        this.id_paiement = id_paiement;
        this.total = total;
        this.produitList = produitList;
        this.etat_livraison = etat_livraison;
        this.statut_paiement = statut_paiement;
        this.numero = numero;
    }

    public UUID get_idCommande() {
        return _idCommande;
    }

    public void set_idCommande(UUID _idCommande) {
        this._idCommande = _idCommande;
    }

    public Date getDate_creation() {
        return date_creation;
    }

    public void setDate_creation(Date date_creation) {
        this.date_creation = date_creation;
    }

    public String getId_utillisateur() {
        return id_utillisateur;
    }

    public void setId_utillisateur(String id_utillisateur) {
        this.id_utillisateur = id_utillisateur;
    }

    public String getId_paiement() {
        return id_paiement;
    }

    public void setId_paiement(String id_paiement) {
        this.id_paiement = id_paiement;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public List<Produit> getProduitList() {
        return produitList;
    }

    public void setProduitList(List<Produit> produitList) {
        this.produitList = produitList;
    }

    public String getEtat_livraison() {
        return etat_livraison;
    }

    public void setEtat_livraison(String etat_livraison) {
        this.etat_livraison = etat_livraison;
    }

    public String getStatut_paiement() {
        return statut_paiement;
    }

    public void setStatut_paiement(String statut_paiement) {
        this.statut_paiement = statut_paiement;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }
}
