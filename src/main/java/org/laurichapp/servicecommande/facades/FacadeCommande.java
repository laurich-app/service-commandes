package org.laurichapp.servicecommande.facades;

import org.laurichapp.servicecommande.models.Commande;

import java.util.List;

public interface FacadeCommande {

    /**
     * Créer une commande
     * TODO : à définir
     */
    void createCommande();

    /*========== GET ==========*/
    /**
     * Récupère la liste des commandes pagines pour l'utilisateur connecté.
     * @return Liste de commandes
     */
    List<Commande> getAllCommandesUtilisateur();

    /**
     * Récupère une commande pour l'utilisateur connecté.
     * @param idCommande : id de la commande
     * @return une commande
     */
    Commande getCommandeUtilisateurById(String idCommande);

    /**
     * Récupère la liste des commandes pagines.
     * @return Liste de toutes les commandes
     */
    List<Commande> getAllCommandes();

    /**
     * Récupère une commande.
     * @param idCommande : id de la commande
     * @return une commande
     */
    Commande getCommandeById(String idCommande);

    /*========== POST ==========*/

    /*========== PUT ==========*/

    /**
     * Met à jours l'état de livraison de la commande.
     */
    void updateEtatLivraison();

    /*========== DELETE ==========*/

}
