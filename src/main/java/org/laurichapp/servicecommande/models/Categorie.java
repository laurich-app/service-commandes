package org.laurichapp.servicecommande.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.laurichapp.servicecommande.dtos.CategorieDTO;

@Entity
@Table(name = "CATEGORIE")
public class Categorie {

    @Id
    private String libelle;

    public static CategorieDTO toDTO(Categorie categorie) {
        return new CategorieDTO(categorie.getLibelle());
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }
}
