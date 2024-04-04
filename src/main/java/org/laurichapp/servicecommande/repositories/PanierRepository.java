package org.laurichapp.servicecommande.repositories;

import org.laurichapp.servicecommande.models.Panier;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PanierRepository extends MongoRepository<Panier, String> {

    Panier findByToken(String token);

    @Query("{'produits': { $elemMatch: { 'id_produit_catalogue': ?0, 'couleur.libelle': ?1 } } }")
    List<Panier> findByProduitsIdAndCouleur(int idProduitCatalogue, String couleur);
}
