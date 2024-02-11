package org.laurichapp.servicecommande.facade;

import org.laurichapp.servicecommande.model.Commande;
import org.laurichapp.servicecommande.model.Panier;
import org.laurichapp.servicecommande.model.Produit;

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

    /*========== PUT ==========*/

    /**
     * Ajouter un produit
     * @param token du panier
     * @param produit à ajouter
     */
    void addProduit(String token, Produit produit);

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
