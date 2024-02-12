package org.laurichapp.servicecommande.repositories;

import org.laurichapp.servicecommande.models.Couleur;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CouleurRepository extends MongoRepository<Couleur, Long> {
}
