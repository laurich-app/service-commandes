package org.laurichapp.servicecommande.facades;

import org.laurichapp.servicecommande.models.Commande;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FacadeCommandeImpl implements FacadeCommande {
    @Override
    public void createCommande() {
        // TODO
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
