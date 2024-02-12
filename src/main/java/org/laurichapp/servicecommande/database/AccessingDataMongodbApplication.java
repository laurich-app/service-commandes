package org.laurichapp.servicecommande.database;

import org.laurichapp.servicecommande.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Deprecated
@SpringBootApplication
public class AccessingDataMongodbApplication implements CommandLineRunner {

    private CommandeRepository commandeRepository;
    private PanierRepository panierRepository;
    private ProduitRepository produitRepository;
    private CouleurRepository couleurRepository;
    private CategorieRepository categorieRepository;

    public AccessingDataMongodbApplication(CommandeRepository commandeRepository, PanierRepository panierRepository, ProduitRepository produitRepository, CouleurRepository couleurRepository, CategorieRepository categorieRepository) {
        this.commandeRepository = commandeRepository;
        this.panierRepository = panierRepository;
        this.produitRepository = produitRepository;
        this.couleurRepository = couleurRepository;
        this.categorieRepository = categorieRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // TODO
    }
}
