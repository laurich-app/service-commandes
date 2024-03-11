package org.laurichapp.servicecommande.facades;

import org.laurichapp.servicecommande.enums.EtatsLivraison;
import org.laurichapp.servicecommande.exceptions.EtatLivraisonInexistantException;
import org.laurichapp.servicecommande.models.Commande;
import org.laurichapp.servicecommande.models.Panier;

import java.util.List;

public interface FacadeCommande {

    /**
     * Créer une commande
     * Créer une commande à partir d'un panier
     */
    void createCommande(Panier panier, String idUtilisateur);

    /*========== GET ==========*/
    /**
     * Récupère la liste des commandes pagines pour l'utilisateur connecté.
     * @return Liste de commandes
     */
    List<Commande> getAllCommandesUtilisateur(String idUtilisateur);

    /**
     * Récupère une commande pour l'utilisateur connecté.
     * @param idCommande : id de la commande
     * @return une commande
     */
    Commande getCommandeUtilisateurById(String idCommande, String idUtilisateur);

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
    Commande updateEtatLivraison(String idCommande, String etat) throws EtatLivraisonInexistantException;

    /*========== DELETE ==========*/

}
