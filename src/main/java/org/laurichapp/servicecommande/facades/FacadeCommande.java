package org.laurichapp.servicecommande.facades;

import org.laurichapp.servicecommande.dtos.out.CommandeDTO;
import org.laurichapp.servicecommande.dtos.pagination.Paginate;
import org.laurichapp.servicecommande.dtos.pagination.PaginateRequestDTO;
import org.laurichapp.servicecommande.dtos.rabbits.GenererCommandeDTO;
import org.laurichapp.servicecommande.enums.EtatsLivraison;
import org.laurichapp.servicecommande.exceptions.CommandeNotFoundException;
import org.laurichapp.servicecommande.models.Commande;
import org.laurichapp.servicecommande.models.Panier;


public interface FacadeCommande {

    /**
     * Créer une commande
     * Créer une commande à partir d'un panier
     */
    void createCommande(Panier panier, String idUtilisateur, String email);

    /*========== GET ==========*/
    /**
     * Récupère la liste des commandes pagines pour l'utilisateur connecté.
     * @return Liste de commandes
     */
    Paginate<CommandeDTO> getAllCommandesUtilisateur(String idUtilisateur, PaginateRequestDTO paginateRequestDTO);

    /**
     * Récupère une commande pour l'utilisateur connecté.
     * @param idCommande
     * @param idUtilisateur
     * @return
     * @throws CommandeNotFoundException
     */
    CommandeDTO getCommandeDTOUtilisateurById(String idCommande, String idUtilisateur) throws CommandeNotFoundException;

    /**
     * Récupère la liste des commandes pagines.
     * @return Liste de toutes les commandes
     */
    Paginate<CommandeDTO> getAllCommandes(PaginateRequestDTO paginateRequestDTO);

    /**
     * Récupère une commande.
     * @param idCommande : id de la commande
     * @return une commande
     */
    Commande getCommandeById(String idCommande) throws CommandeNotFoundException;

    /**
     * Récupère une commandeDTO.
     * @param idCommande : id de la commande
     * @return une commandeDTO
     * @throws CommandeNotFoundException
     */
    CommandeDTO getCommandeDTOById(String idCommande) throws CommandeNotFoundException;

    /*========== POST ==========*/

    /*========== PUT ==========*/

    /**
     * Met à jours l'état de livraison de la commande.
     * @param idCommande
     * @param etat
     * @return
     * @throws CommandeNotFoundException
     */
    CommandeDTO updateEtatLivraison(String idCommande, EtatsLivraison etat) throws CommandeNotFoundException;

    /**
     * Lors de la validation d'une commande, au retour de la reception des produits depuis le catalogue.
     * @param genererCommandeDTO
     */
    void validerCommandeReceptionProduits(GenererCommandeDTO genererCommandeDTO) throws CommandeNotFoundException;

    /*========== DELETE ==========*/

}
