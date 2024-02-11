package org.laurichapp.servicecommande.facade;

import org.laurichapp.servicecommande.model.Commande;
import org.laurichapp.servicecommande.model.Panier;
import org.laurichapp.servicecommande.model.Produit;

import java.util.UUID;

public class FacadePanierImpl implements FacadePanier {

    @Override
    public Panier getPanier(String token) {
        // TODO
        return null;
    }

    @Override
    public Commande createCommandeFromPanier(String token) {
        // TODO
        return null;
    }

    @Override
    public void addProduit(String token, Produit produit) {
        // TODO
    }

    @Override
    public void deletePanier(String token) {
        // TODO
    }

    @Override
    public void deleteProduitPanier(String token, UUID idProduit) {
        // TODO
    }
}
