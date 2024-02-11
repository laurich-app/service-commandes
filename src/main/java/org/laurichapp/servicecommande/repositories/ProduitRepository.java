package org.laurichapp.servicecommande.repositories;

import org.laurichapp.servicecommande.models.Panier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProduitRepository extends JpaRepository<Panier, Long> {
}
