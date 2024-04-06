package org.laurichapp.servicecommande.facades;

import org.laurichapp.servicecommande.dtos.in.AjouterProduitDTO;
import org.laurichapp.servicecommande.dtos.in.UpdateProduitDTO;
import org.laurichapp.servicecommande.dtos.out.PanierDTO;
import org.laurichapp.servicecommande.exceptions.PanierNotFoundException;
import org.laurichapp.servicecommande.exceptions.ProduitPasDansPanierException;
import org.laurichapp.servicecommande.models.Panier;
import org.laurichapp.servicecommande.models.paniers.Produit;
import org.laurichapp.servicecommande.repositories.PanierRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class FacadePanierImpl implements FacadePanier {
    private static final Logger logger = LoggerFactory.getLogger(FacadePanierImpl.class);

    PanierRepository panierRepository;

    FacadeCommande facadeCommande;

    public FacadePanierImpl(PanierRepository panierRepository, FacadeCommandeImpl facadeCommande) {
        this.panierRepository = panierRepository;
        this.facadeCommande = facadeCommande;
    }


    @Override
    public PanierDTO getPanier(String token) throws PanierNotFoundException {
        Panier panier = panierRepository.findByToken(token);
        if(panier == null)
            throw new PanierNotFoundException();
        return Panier.toDTO(panier);
    }

    @Override
    public void createCommandeFromPanier(String token, String idUtilisateur, String email) throws PanierNotFoundException {
        Panier panier = panierRepository.findByToken(token);
        if(panier == null)
            throw new PanierNotFoundException();
        facadeCommande.createCommande(panier, idUtilisateur, email);
        panierRepository.delete(panier);
    }

    @Override
    public PanierDTO createPanier(AjouterProduitDTO produitDTO) {
        Panier panier = new Panier();
        Produit produit = Produit.fromDTO(produitDTO);
        panier.getProduits().add(produit);
        panier.setToken(UUID.randomUUID().toString());
        this.panierRepository.insert(panier);
        return Panier.toDTO(panier);
    }

    @Override
    public PanierDTO addProduit(String token, AjouterProduitDTO produitDTO) throws PanierNotFoundException {
        Panier panier = panierRepository.findByToken(token);
        if(panier == null)
            throw new PanierNotFoundException();
        Produit produit = Produit.fromDTO(produitDTO);
        panier.getProduits().add(produit);
        panierRepository.save(panier);
        return Panier.toDTO(panier);
    }

    @Override
    public PanierDTO updateProduit(String token, int idProduit, UpdateProduitDTO updateProduitDTO) throws ProduitPasDansPanierException, PanierNotFoundException {
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
            return Panier.toDTO(panier);
        }
        else { // si la qte est == 0 alors on supprime le produit
            this.deleteProduitPanier(token, idProduit, updateProduitDTO.couleur_choisi());
            return Panier.toDTO(panierRepository.findByToken(token));
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

    @Override
    public void deleteProduitPanierByIdAndCommande(int idProduit, String couleur) {
        List<Panier> paniers = this.panierRepository.findByProduitsIdAndCouleur(idProduit, couleur);
        paniers.stream().forEach(p -> {
            try {
                Produit produit = p.getProduit(idProduit, couleur);
                p.getProduits().remove(produit);
                panierRepository.save(p);
            } catch (ProduitPasDansPanierException e) {
                logger.error("Le produit n'a pas été trouvé dans le panier.");
            }
        });
    }
}
