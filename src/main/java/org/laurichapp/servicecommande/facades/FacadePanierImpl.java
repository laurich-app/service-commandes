package org.laurichapp.servicecommande.facades;

import org.laurichapp.servicecommande.models.Commande;
import org.laurichapp.servicecommande.models.Panier;
import org.laurichapp.servicecommande.models.Produit;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
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
    public Panier createPanier(Produit produit) {
        // TODO
        return null;
    }

    @Override
    public void addProduit(String token, Produit produit) {
        // TODO
    }

    @Override
    public void updateProduit(String token, Produit produit) {
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
