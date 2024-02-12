package org.laurichapp.servicecommande.repositories;

import org.laurichapp.servicecommande.models.Categorie;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CategorieRepository extends MongoRepository<Categorie, Long> {
}
