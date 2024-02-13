package org.laurichapp.servicecommande.models;


import org.laurichapp.servicecommande.dtos.CouleurDTO;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;


public class Couleur {

    private String libelle;


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
