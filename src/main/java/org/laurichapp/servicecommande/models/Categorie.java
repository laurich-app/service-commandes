package org.laurichapp.servicecommande.models;


import org.laurichapp.servicecommande.dtos.CategorieDTO;

public class Categorie {

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
