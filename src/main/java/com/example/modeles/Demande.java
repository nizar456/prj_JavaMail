package com.example.modeles;

import lombok.Data;

import java.util.Date;

@Data
public class Demande {
    private String expediteur;
    private String sujet;
    private String contenu;
    private Date dateReception;
    private TypeDemande type;
    // Enum interne pour les types possibles
    public enum TypeDemande {
        CERTIFICAT, RECLAMATION, INFORMATION, AUTRE
    }
    // Constructeur
    public Demande(String expediteur, String sujet, String contenu, Date dateReception) {
        this.expediteur = expediteur;
        this.sujet = sujet;
        this.contenu = contenu;
        this.dateReception = dateReception;
        determinerType(); // Détermine automatiquement le type dès la création
    }
    // Méthode pour déterminer le type en fonction du sujet
    public void determinerType() {
        if (sujet == null) {
            this.type = TypeDemande.AUTRE;
            return;
        }

        String lower = sujet.toLowerCase();

        if (lower.contains("certificat")) {
            this.type = TypeDemande.CERTIFICAT;
        } else if (lower.contains("réclamation") || lower.contains("reclamation") || lower.contains("problème") || lower.contains("probleme")) {
            this.type = TypeDemande.RECLAMATION;
        } else if (lower.contains("information") || lower.contains("question")) {
            this.type = TypeDemande.INFORMATION;
        } else {
            this.type = TypeDemande.AUTRE;
        }
    }

    @Override
    public String toString() {
        return "Demande{" +
                "expediteur='" + expediteur + '\'' +
                ", sujet='" + sujet + '\'' +
                ", contenu='" + contenu + '\'' +
                ", dateReception=" + dateReception +
                ", type=" + type +
                '}';
    }
}
