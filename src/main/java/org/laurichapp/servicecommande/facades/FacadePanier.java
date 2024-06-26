package org.laurichapp.servicecommande.facades;

import org.laurichapp.servicecommande.dtos.in.AjouterProduitDTO;
import org.laurichapp.servicecommande.dtos.in.UpdateProduitDTO;
import org.laurichapp.servicecommande.dtos.out.PanierDTO;
import org.laurichapp.servicecommande.exceptions.PanierNotFoundException;
import org.laurichapp.servicecommande.exceptions.ProduitPasDansPanierException;

public interface FacadePanier {

    /*========== GET ==========*/
    /**
     * Permet de consulter son panier.
     * Vérifie que tous les produits sont bon dans le panier avec l'ESB.
     * @param token du panier
     * @return le panier
     */
    PanierDTO getPanier(String token) throws PanierNotFoundException;

    /*========== POST ==========*/
    /**
     * Appel l'ESB afin de créer une commande.
     * Notifie l'admin qu'une commande a été créé.
     * @param token du panier
     * @return la commande créé
     */
    void createCommandeFromPanier(String token, String idUtilisateur, String email) throws PanierNotFoundException;

    /**
     * Créer un panier en ajoutant un produit
     * @param produitDTO le produit à ajouter
     * @return
     */
    PanierDTO createPanier(AjouterProduitDTO produitDTO);

    /**
     * Ajouter un produit
     * @param token du panier
     * @param produitDTO à ajouter
     */
    PanierDTO addProduit(String token, AjouterProduitDTO produitDTO) throws PanierNotFoundException;

    /*========== PUT ==========*/

    /**
     * Met à jour un produit,
     * Si il existe augmente la qte sinon le rajoute au panier
     * @param token du panier
     * @param idProduit du produit
     * @param updateProduitDTO produit
     * @return
     * @throws ProduitPasDansPanierException
     */
    PanierDTO updateProduit(String token, int idProduit, UpdateProduitDTO updateProduitDTO) throws ProduitPasDansPanierException, PanierNotFoundException;

    /*========== DELETE ==========*/
    /**
     * Supprime le panier
     * @param token du panier
     */
    void deletePanier(String token) throws PanierNotFoundException;

    /**
     * Supprime un produit du panier
     * @param token du panier
     * @param idProduit du produit à supprimer
     */
    void deleteProduitPanier(String token, int idProduit, String couleur) throws ProduitPasDansPanierException, PanierNotFoundException;

    /**
     * Lors du suppression d'un stock on supprime les produits des paniers correspondants.
     * @param idProduit
     * @param couleur
     */
    void deleteProduitPanierByIdAndCommande(int idProduit, String couleur);
}
