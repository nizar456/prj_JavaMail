package com.example;

import com.example.modeles.Etudiant;
import com.example.services.ServiceEnvoi;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        // 1. Créer un étudiant
        Etudiant etudiant = new Etudiant(
                "E001",
                "Nizar Fikhan",
                "nizarfikhan99@gmail.com",
                15.5
        );

        // 2. Définir la date et la salle de l'examen
        String dateExamen = "20/11/2025";
        String salle = "Salle A101";

        // 3. Créer le service d'envoi
        ServiceEnvoi service = new ServiceEnvoi();

        // 4. Envoyer la convocation
        service.envoyerConvocation(etudiant, dateExamen, salle);
    }

}