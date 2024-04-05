package org.laurichapp.servicecommande.models;

import org.bson.types.ObjectId;
import org.laurichapp.servicecommande.dtos.out.CommandeDTO;
import org.laurichapp.servicecommande.dtos.rabbits.ValiderCommandeDTO;
import org.laurichapp.servicecommande.enums.EtatsLivraison;
import org.laurichapp.servicecommande.enums.StatutsPaiment;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.laurichapp.servicecommande.models.commandes.Produit;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Document(collection = "commandes")
public class Commande {

    @Id
    @Field("_id")
    private ObjectId idCommande;

    @Field("date_creation")
    private Date dateCreation;

    @Field("id_utilisateur")
    private String idUtilisateur;

    @Field("id_paiement")
    private String idPaiement;

    private String email;

    private Double total;

    private List<Produit> produits;

    @Field("etat_livraison")
    private EtatsLivraison etatLivraison;

    @Field("statut_paiement")
    private StatutsPaiment statutPaiement;

    private String numero;

    public Commande() {
        this.produits = new ArrayList<>();
    }

    public static CommandeDTO toDTO(Commande commande) {
        return new CommandeDTO(commande.getIdCommande().toString(),
                commande.getDateCreation(),
                commande.getIdUtilisateur(),
                commande.getIdPaiement(),
                commande.getTotal(),
                commande.getProduits().stream().map(Produit::toProduitDTO).toList(),
                commande.getEtatLivraison().toString(),
                commande.getStatutPaiement().toString(),
                commande.getNumero());
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ObjectId getIdCommande() {
        return idCommande;
    }

    public void setIdCommande(ObjectId idCommande) {
        this.idCommande = idCommande;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public String getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(String idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    public String getIdPaiement() {
        return idPaiement;
    }

    public void setIdPaiement(String idPaiement) {
        this.idPaiement = idPaiement;
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

    public EtatsLivraison getEtatLivraison() {
        return etatLivraison;
    }

    public void setEtatLivraison(EtatsLivraison etatLivraison) {
        this.etatLivraison = etatLivraison;
    }

    public StatutsPaiment getStatutPaiement() {
        return statutPaiement;
    }

    public void setStatutPaiement(StatutsPaiment statutPaiement) {
        this.statutPaiement = statutPaiement;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public static ValiderCommandeDTO toValiderCommandeDTO(Commande commande, Panier panier) {
        return new ValiderCommandeDTO(
                commande.getIdCommande().toString(),
                panier.getProduits().stream().map(org.laurichapp.servicecommande.models.paniers.Produit::toProduitCommandeDTO)
                        .toList()
        );
    }
}
