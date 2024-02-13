package org.laurichapp.servicecommande.facades;

import org.laurichapp.servicecommande.dtos.ProduitDTO;
import org.laurichapp.servicecommande.models.Commande;
import org.laurichapp.servicecommande.models.Panier;
import org.laurichapp.servicecommande.models.Produit;
import org.laurichapp.servicecommande.repositories.PanierRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class FacadePanierImpl implements FacadePanier {

    PanierRepository panierRepository;

    public FacadePanierImpl(PanierRepository panierRepository) {
        this.panierRepository = panierRepository;
    }


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
    public Panier createPanier(ProduitDTO produitDTO) {
        Panier panier = new Panier();
        Produit p = Produit.fromDTO(produitDTO);
        panier.addProduitListProduits(p);
        panier.setToken(UUID.randomUUID().toString());
        this.panierRepository.save(panier);
        return panier;
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
