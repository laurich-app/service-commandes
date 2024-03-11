package org.laurichapp.servicecommande.repositories;

import org.laurichapp.servicecommande.models.Commande;
import org.springframework.stereotype.Repository;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

@Repository
public interface CommandeRepository extends MongoRepository<Commande, Long> {

    List<Commande> findAllById_utillisateur(String id_utillisateur);
    Commande findCommandeBy_idCommandeAndId_utillisateur(Long _idCommande, String id_utillisateur);
}
