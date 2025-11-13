package com.example.modeles;

import lombok.Data;

@Data
public class Etudiant {
    private String numero;
    private String nom;
    private String email;
    private double moyenne;

    // Constructeur avec tous les paramètres
    public Etudiant(String numero, String nom, String email, double moyenne) {
        this.numero = numero;
        this.nom = nom;
        this.email = email;
        this.moyenne = moyenne;
    }
}
