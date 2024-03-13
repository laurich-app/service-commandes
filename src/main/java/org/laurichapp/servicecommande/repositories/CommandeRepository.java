package org.laurichapp.servicecommande.repositories;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.laurichapp.servicecommande.models.Commande;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

@Repository
public interface CommandeRepository extends MongoRepository<Commande, String> {

    @Query("{ 'id_utillisateur' : ?0 }")
    Page<Commande> findAllById_utillisateur(String id_utillisateur, Pageable pageable);
    @Query("{ 'id_utillisateur' : ?1, '_idCommande' : ?0 }")
    Commande findCommandeBy_idCommandeAndId_utillisateur(String _idCommande, String id_utillisateur);
}
