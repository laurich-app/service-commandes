package org.laurichapp.servicecommande.repositories;

import org.laurichapp.servicecommande.models.Commande;
import org.springframework.stereotype.Repository;
import org.springframework.data.mongodb.repository.MongoRepository;

@Repository
public interface CommandeRepository extends MongoRepository<Commande, Long> {
}
