package org.laurichapp.servicecommande.repositories;

import org.laurichapp.servicecommande.models.Commande;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommandeRepository extends JpaRepository<Commande, Long> {
}
