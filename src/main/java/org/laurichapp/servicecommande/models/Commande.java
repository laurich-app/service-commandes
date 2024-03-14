package org.laurichapp.servicecommande.models;

import org.bson.types.ObjectId;
import org.laurichapp.servicecommande.dtos.out.CommandeDTO;
import org.laurichapp.servicecommande.dtos.rabbits.ValiderCommandeDTO;
import org.laurichapp.servicecommande.enums.EtatsLivraison;
import org.laurichapp.servicecommande.enums.StatutsPaiment;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.laurichapp.servicecommande.models.commandes.Produit;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Document(collection = "commandes")
public class Commande {

    @Id
    private ObjectId _idCommande;

    private Date date_creation;

    private String id_utillisateur, id_paiement;

    private Double total;

    private List<Produit> produits;

    private EtatsLivraison etat_livraison;
    private StatutsPaiment statut_paiement;

    private String numero;

    public Commande() {
        this.produits = new ArrayList<>();
    }

    public static CommandeDTO toDTO(Commande commande) {
        return new CommandeDTO(commande.get_idCommande().toString(),
                commande.getDate_creation(),
                commande.getId_utillisateur(),
                commande.getId_paiement(),
                commande.getTotal(),
                commande.getProduits().stream().map(Produit::toProduitDTO).collect(Collectors.toList()),
                commande.getEtat_livraison().toString(),
                commande.getStatut_paiement().toString(),
                commande.getNumero());
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

    public List<Produit> getProduits() {
        return produits;
    }

    public void setProduits(List<Produit> produits) {
        this.produits = produits;
    }

    public EtatsLivraison getEtat_livraison() {
        return etat_livraison;
    }

    public void setEtat_livraison(EtatsLivraison etat_livraison) {
        this.etat_livraison = etat_livraison;
    }

    public StatutsPaiment getStatut_paiement() {
        return statut_paiement;
    }

    public void setStatut_paiement(StatutsPaiment statut_paiement) {
        this.statut_paiement = statut_paiement;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public static ValiderCommandeDTO toValiderCommandeDTO(Commande commande, Panier panier) {
        return new ValiderCommandeDTO(
                commande.get_idCommande().toString(),
                panier.getProduits().stream().map(org.laurichapp.servicecommande.models.paniers.Produit::toProduitCommandeDTO)
                        .collect(Collectors.toList())
        );
    }
}
