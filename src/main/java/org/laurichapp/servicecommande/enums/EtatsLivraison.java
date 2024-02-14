package org.laurichapp.servicecommande.enums;

public enum EtatsLivraison {
    EN_ATTENTE("EN_ATTENTE"), EN_COURS("EN_COURS"), LIVRE("LIVRE");

    public final String label;

    EtatsLivraison(String label) {
        this.label = label;
    }
}
