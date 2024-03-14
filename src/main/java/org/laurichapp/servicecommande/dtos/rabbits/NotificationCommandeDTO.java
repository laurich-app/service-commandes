package org.laurichapp.servicecommande.dtos.rabbits;

import org.laurichapp.servicecommande.dtos.out.CommandeDTO;

import java.io.Serializable;

public record NotificationCommandeDTO(String email, CommandeDTO commande) implements Serializable {
}
