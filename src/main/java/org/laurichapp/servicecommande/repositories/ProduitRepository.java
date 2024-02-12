package org.laurichapp.servicecommande.repositories;

import org.laurichapp.servicecommande.models.Panier;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProduitRepository extends MongoRepository<Panier, Long> {
}
