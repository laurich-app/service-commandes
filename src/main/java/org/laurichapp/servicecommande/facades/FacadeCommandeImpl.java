package org.laurichapp.servicecommande.facades;

import org.laurichapp.servicecommande.enums.EtatsLivraison;
import org.laurichapp.servicecommande.enums.StatutsPaiment;
import org.laurichapp.servicecommande.models.Commande;
import org.laurichapp.servicecommande.models.Panier;
import org.laurichapp.servicecommande.models.Produit;
import org.laurichapp.servicecommande.repositories.CommandeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FacadeCommandeImpl implements FacadeCommande {

    private CommandeRepository commandeRepository;

    public FacadeCommandeImpl(CommandeRepository commandeRepository) {
        this.commandeRepository = commandeRepository;
    }

    @Override
    public void createCommande(Panier panier) {
        //String name = (Principal principal) principal->getName();
        List<Produit> produitList = panier.getListProduits();
        Double sommePanier = 0.0;
        for (Produit p : produitList) {
            sommePanier+= p.getPrix_unitaire()*p.getQuantite();
        }
        Commande commande = new Commande("123456", null, sommePanier, produitList, EtatsLivraison.EN_ATTENTE.label, StatutsPaiment.EN_ATTENTE.label);
        commandeRepository.insert(commande);
    }

    @Override
    public List<Commande> getAllCommandesUtilisateur() {
        // TODO
        return null;
    }

    @Override
    public Commande getCommandeUtilisateurById(String idCommande) {
        // TODO
        return null;
    }

    @Override
    public List<Commande> getAllCommandes() {
        // TODO
        return null;
    }

    @Override
    public Commande getCommandeById(String idCommande) {
        // TODO
        return null;
    }

    @Override
    public void updateEtatLivraison() {
        // TODO
    }
}
