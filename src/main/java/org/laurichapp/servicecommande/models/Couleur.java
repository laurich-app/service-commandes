package org.laurichapp.servicecommande.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import org.laurichapp.servicecommande.dtos.CouleurDTO;

import java.util.List;

@Entity
@Table(name = "COULEUR")
public class Couleur {

    @Id
    private String libelle;

    @OneToMany(mappedBy = "couleur")
    private List<Produit> listProduits;
    @OneToMany(mappedBy = "categorie")
    private List<Categorie> listCategories;

    public static CouleurDTO toDTO(Couleur couleur) {
        return new CouleurDTO(couleur.getLibelle());
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }
}
