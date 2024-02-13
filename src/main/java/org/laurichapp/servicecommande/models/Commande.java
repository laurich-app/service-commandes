package org.laurichapp.servicecommande.models;

import org.bson.types.ObjectId;
import org.laurichapp.servicecommande.dtos.CommandeDTO;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document(collection = "commandes")
public class Commande {

    @Id
    private ObjectId _idCommande;

    Date date_creation;

    private String id_utillisateur, id_paiement;

    Double total;

    private List<Produit> produitList;

    private String etat_livraison, statut_paiement;

    private String numero;

    public static CommandeDTO toDTO(Commande commande) {
        return new CommandeDTO(commande.get_idCommande().toString(),
                commande.getDate_creation(),
                commande.getId_utillisateur(),
                commande.getId_paiement(),
                commande.getTotal(),
                commande.getProduitList(),
                commande.getEtat_livraison(),
                commande.getStatut_paiement(),
                commande.getNumero());
    }

    public static Commande fromDTO(CommandeDTO commandeDTO) {
        // TODO
        return null;
    }

    public ObjectId get_idCommande() {
        return _idCommande;
    }

    public void set_idCommande(ObjectId _idCommande) {
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
