package org.laurichapp.servicecommande.dtos.rabbits;


import java.io.Serializable;

public record SupprimerStockDTO(String couleur, int id_produit) implements Serializable {
}
