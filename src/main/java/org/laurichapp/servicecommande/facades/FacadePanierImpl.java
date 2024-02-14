package org.laurichapp.servicecommande.facades;

import org.laurichapp.servicecommande.dtos.ProduitDTO;
import org.laurichapp.servicecommande.exceptions.ProduitPasDansPanierException;
import org.laurichapp.servicecommande.models.Commande;
import org.laurichapp.servicecommande.models.Panier;
import org.laurichapp.servicecommande.models.Produit;
import org.laurichapp.servicecommande.repositories.PanierRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class FacadePanierImpl implements FacadePanier {

    PanierRepository panierRepository;

    FacadeCommande facadeCommande;

    public FacadePanierImpl(PanierRepository panierRepository, FacadeCommandeImpl facadeCommande) {
        this.panierRepository = panierRepository;
        this.facadeCommande = facadeCommande;
    }


    @Override
    public Panier getPanier(String token) {
        Panier panier = panierRepository.findByToken(token);
        return panier;
    }

    @Override
    public void createCommandeFromPanier(String token) {
        Panier panier = panierRepository.findByToken(token);
        facadeCommande.createCommande(panier);
        panierRepository.delete(panier);
    }

    @Override
    public Panier createPanier(ProduitDTO produitDTO) {
        Panier panier = new Panier();
        Produit produit = Produit.fromDTO(produitDTO);
        panier.addProduitListProduits(produit);
        panier.setToken(UUID.randomUUID().toString());
        this.panierRepository.insert(panier);
        return panier;
    }

    @Override
    public Panier addProduit(String token, ProduitDTO produitDTO) {
        Panier panier = panierRepository.findByToken(token);
        Produit produit = Produit.fromDTO(produitDTO);
        panier.addProduitListProduits(produit);
        panierRepository.save(panier);
        return panier;
    }

    @Override
    public Panier updateProduit(String token, int idProduit, String couleur, int quantite, Double prix_unitaire) throws ProduitPasDansPanierException {
        if (quantite != 0) {
            Panier panier = panierRepository.findByToken(token);
            Produit produit = panier.getProduitById(idProduit);
            produit.setCouleur(couleur);
            produit.setQuantite(quantite);
            produit.setPrix_unitaire(prix_unitaire);
            panierRepository.save(panier);
            return panier;
        }
        else { // si la qte est == 0 alors on supprime le produit
            this.deleteProduitPanier(token, idProduit);
            return panierRepository.findByToken(token);
        }

    }

    @Override
    public void deletePanier(String token) {
        Panier panier = panierRepository.findByToken(token);
        panierRepository.delete(panier);
    }

    @Override
    public void deleteProduitPanier(String token, int idProduit) throws ProduitPasDansPanierException {
        Panier panier = panierRepository.findByToken(token);
        Produit produit = panier.getProduitById(idProduit);
        panier.getListProduits().remove(produit);
        panierRepository.save(panier);
    }
}
