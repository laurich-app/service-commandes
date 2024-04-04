package org.laurichapp.servicecommande.repositories;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.laurichapp.servicecommande.models.Commande;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.mongodb.repository.MongoRepository;

@Repository
public interface CommandeRepository extends MongoRepository<Commande, String> {

    @Query("{ 'id_utilisateur' : ?0 }")
    Page<Commande> findAllByIdUtilisateur(String idUtilisateur, Pageable pageable);
    @Query("{ 'id_utilisateur' : ?1, '_id' : ?0 }")
    Commande findCommandeByIdCommandeAndIdUtilisateur(String idCommande, String idUtillisateur);
}
