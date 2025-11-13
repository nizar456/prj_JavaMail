package com.example.services;

import com.example.config.ConfigEmail;
import com.example.modeles.Etudiant;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class ServiceEnvoi {
    public void envoyerConvocation(Etudiant etudiant, String dateExamen, String salle) {
        // 1. Récupérer les propriétés SMTP
        Properties props = ConfigEmail.getPropertiesSMTP();

        // 2. Créer la session avec authentification
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(ConfigEmail.EMAIL, ConfigEmail.PASSWORD);
            }
        });

        try {
            // 3. Créer le message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(ConfigEmail.EMAIL));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(etudiant.getEmail())
            );

            message.setSubject("Convocation Examen - ENSIAS");

            // 4. Corps du message (texte brut)
            String corps = "Bonjour " + etudiant.getNom() + ",\n\n" +
                    "Vous êtes convoqué(e) pour l'examen.\n" +
                    "Date : " + dateExamen + "\n" +
                    "Salle : " + salle + "\n\n" +
                    "Cordialement,\n" +
                    "Service Examens ENSIAS";

            message.setText(corps);

            // 5. Envoi du message
            Transport.send(message);

            System.out.println("Convocation envoyée à " + etudiant.getEmail());

        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de l'envoi de la convocation à " + etudiant.getEmail());
        }
    }
}
