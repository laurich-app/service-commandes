package org.laurichapp.servicecommande.facades;

import org.laurichapp.servicecommande.dtos.PanierDTO;
import org.laurichapp.servicecommande.dtos.ProduitDTO;
import org.laurichapp.servicecommande.exceptions.ProduitPasDansPanierException;
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
    void createCommandeFromPanier(String token, String idUtilisateur);

    /**
     * Créer un panier en ajoutant un produit
     * @param produitDTO le produit à ajouter
     * @return
     */
    Panier createPanier(ProduitDTO produitDTO);

    /**
     * Ajouter un produit
     * @param token du panier
     * @param produitDTO à ajouter
     */
    Panier addProduit(String token, ProduitDTO produitDTO);

    /*========== PUT ==========*/

    /**
     * Met à jour un produit,
     * Si il existe augmente la qte sinon le rajoute au panier
     * @param token du panier
     * @param idProduit du produit
     * @param couleur du produit
     * @param quantite du produit
     * @return
     * @throws ProduitPasDansPanierException
     */
    Panier updateProduit(String token, int idProduit, String couleur, int quantite, Double prix_unitaire) throws ProduitPasDansPanierException;

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
    void deleteProduitPanier(String token, int idProduit, String couleur) throws ProduitPasDansPanierException;
}
