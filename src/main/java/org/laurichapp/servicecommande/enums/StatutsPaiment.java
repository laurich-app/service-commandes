package org.laurichapp.servicecommande.enums;

public enum StatutsPaiment {

    EN_ATTENTE("EN_ATTENTE"), REFUSE("REFUSE"), ACCEPTE("ACCEPTE");

    public final String label;

    StatutsPaiment(String label) {
        this.label = label;
    }
}
