package org.laurichapp.servicecommande.facades;

import org.laurichapp.servicecommande.dtos.PanierDTO;
import org.laurichapp.servicecommande.dtos.ProduitDTO;
import org.laurichapp.servicecommande.models.Commande;
import org.laurichapp.servicecommande.models.Panier;
import org.laurichapp.servicecommande.models.Produit;

import java.util.UUID;

public interface FacadePanier {

    /*========== GET ==========*/
    /**
     * Permet de consulter son panier.
     * Vérifie que tous les produits sont bon dans le panier avec l'ESB.
     * @param token du panier
     * @return le panier
     */
    Panier getPanier(String token);

    /*========== POST ==========*/
    /**
     * Créer une commande à partir du panier.
     * Notifie l'admin qu'une commande a été créé.
     * @param token du panier
     * @return la commande créé
     */
    Commande createCommandeFromPanier(String token);

    /**
     * Créer un panier en ajoutant un produit
     * @param produitDTO le produit à ajouter
     * @return
     */
    Panier createPanier(ProduitDTO produitDTO);

    /*========== PUT ==========*/

    /**
     * Ajouter un produit
     * @param token du panier
     * @param produit à ajouter
     */
    void addProduit(String token, Produit produit);

    /**
     * Met à jour un produit,
     * Si il existe augmente la qte sinon le rajoute au panier
     * @param token du panier
     * @param produit à modifier
     */
    void updateProduit(String token, Produit produit);

    /*========== DELETE ==========*/
    /**
     * Supprime le panier
     * @param token du panier
     */
    void deletePanier(String token);

    /**
     * Supprime un produit du panier
     * @param token du panier
     * @param idProduit du produit à supprimer
     */
    void deleteProduitPanier(String token, UUID idProduit);
}
