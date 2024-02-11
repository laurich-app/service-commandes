package org.laurichapp.servicecommande.repositories;

import org.laurichapp.servicecommande.models.Categorie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategorieRepository extends JpaRepository<Categorie, Long> {
}
