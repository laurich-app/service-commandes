package org.laurichapp.servicecommande.exceptions;

public class CommandeNotFoundException extends Exception {
    public CommandeNotFoundException() {
    }

    public CommandeNotFoundException(String message) {
        super(message);
    }
}
