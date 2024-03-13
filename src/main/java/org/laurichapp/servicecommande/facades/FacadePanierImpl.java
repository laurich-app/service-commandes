package org.laurichapp.servicecommande.facades;

import org.laurichapp.servicecommande.dtos.in.AjouterProduitDTO;
import org.laurichapp.servicecommande.dtos.in.UpdateProduitDTO;
import org.laurichapp.servicecommande.exceptions.PanierNotFoundException;
import org.laurichapp.servicecommande.exceptions.ProduitPasDansPanierException;
import org.laurichapp.servicecommande.models.Panier;
import org.laurichapp.servicecommande.models.paniers.Couleur;
import org.laurichapp.servicecommande.models.paniers.Produit;
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
    public Panier getPanier(String token) throws PanierNotFoundException {
        Panier panier = panierRepository.findByToken(token);
        if(panier == null)
            throw new PanierNotFoundException();
        return panier;
    }

    @Override
    public void createCommandeFromPanier(String token, String idUtilisateur) throws PanierNotFoundException {
        Panier panier = panierRepository.findByToken(token);
        if(panier == null)
            throw new PanierNotFoundException();
        facadeCommande.createCommande(panier, idUtilisateur);
        panierRepository.delete(panier);
    }

    @Override
    public Panier createPanier(AjouterProduitDTO produitDTO) {
        Panier panier = new Panier();
        Produit produit = Produit.fromDTO(produitDTO);
        panier.getProduits().add(produit);
        panier.setToken(UUID.randomUUID().toString());
        this.panierRepository.insert(panier);
        return panier;
    }

    @Override
    public Panier addProduit(String token, AjouterProduitDTO produitDTO) throws PanierNotFoundException {
        Panier panier = panierRepository.findByToken(token);
        if(panier == null)
            throw new PanierNotFoundException();
        Produit produit = Produit.fromDTO(produitDTO);
        panier.getProduits().add(produit);
        panierRepository.save(panier);
        return panier;
    }

    @Override
    public Panier updateProduit(String token, int idProduit, UpdateProduitDTO updateProduitDTO) throws ProduitPasDansPanierException, PanierNotFoundException {
        Panier panier = panierRepository.findByToken(token);
        if(panier == null)
            throw new PanierNotFoundException();

        if (updateProduitDTO.quantite() != 0) {
            Produit produit = panier.getProduit(idProduit, updateProduitDTO.couleur_choisi());
            if (produit == null) {
                throw new ProduitPasDansPanierException();
            }
            else {
                produit.setQuantite(updateProduitDTO.quantite());
                panierRepository.save(panier);
            }
            return panier;
        }
        else { // si la qte est == 0 alors on supprime le produit
            this.deleteProduitPanier(token, idProduit, updateProduitDTO.couleur_choisi());
            return panierRepository.findByToken(token);
        }

    }

    @Override
    public void deletePanier(String token) throws PanierNotFoundException {
        Panier panier = panierRepository.findByToken(token);
        if(panier == null)
            throw new PanierNotFoundException();
        panierRepository.delete(panier);
    }

    @Override
    public void deleteProduitPanier(String token, int idProduit, String couleur) throws ProduitPasDansPanierException, PanierNotFoundException {
        Panier panier = panierRepository.findByToken(token);
        if(panier == null)
            throw new PanierNotFoundException();
        Produit produit = panier.getProduit(idProduit, couleur);
        panier.getProduits().remove(produit);
        panierRepository.save(panier);
    }
}
