package com.example.services;

import com.example.config.ConfigEmail;
import com.example.modeles.Etudiant;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;
import java.io.File;

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
    // Question 4 : Envoi d'email HTML
    public void envoyerReleveNotes(Etudiant etudiant) {
        Properties props = ConfigEmail.getPropertiesSMTP();
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(ConfigEmail.EMAIL, ConfigEmail.PASSWORD);
            }
        });
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(ConfigEmail.EMAIL));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(etudiant.getEmail())
            );
            message.setSubject("Relevé de Notes - Semestre 1");
            // Contenu HTML
            String htmlContent = "<h2>Bonjour " + etudiant.getNom() + ",</h2>" +
                    "<p>Voici votre relevé de notes pour le semestre 1 :</p>" +
                    "<ul>" +
                    "<li>Numéro : " + etudiant.getNumero() + "</li>" +
                    "<li>Moyenne : " + etudiant.getMoyenne() + "</li>" +
                    "</ul>" +
                    "<p>Cordialement,<br/>Service Examens ENSIAS</p>";

            message.setContent(htmlContent, "text/html; charset=utf-8");
            Transport.send(message);
            System.out.println("Relevé de notes envoyé à " + etudiant.getEmail());
        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de l'envoi du relevé de notes à " + etudiant.getEmail());
        }
    }
    // Question 5 : Envoi avec pièce jointe PDF
    public void envoyerReleveAvecPDF(Etudiant etudiant, String cheminPDF) {
        File pdfFile = new File(cheminPDF);
        if (!pdfFile.exists()) {
            System.out.println("Le fichier PDF n'existe pas : " + cheminPDF);
            return;
        }
        Properties props = ConfigEmail.getPropertiesSMTP();
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(ConfigEmail.EMAIL, ConfigEmail.PASSWORD);
            }
        });
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(ConfigEmail.EMAIL));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(etudiant.getEmail())
            );
            message.setSubject("Relevé de Notes - Semestre 1 (PDF)");
            // Création du multipart
            Multipart multipart = new MimeMultipart();
            // Partie 1 : contenu HTML
            MimeBodyPart bodyPart = new MimeBodyPart();
            String htmlContent = "<h2>Bonjour " + etudiant.getNom() + ",</h2>" +
                    "<p>Veuillez trouver votre relevé de notes en pièce jointe.</p>" +
                    "<p>Cordialement,<br/>Service Examens ENSIAS</p>";
            bodyPart.setContent(htmlContent, "text/html; charset=utf-8");
            multipart.addBodyPart(bodyPart);
            // Partie 2 : pièce jointe PDF
            MimeBodyPart attachmentPart = new MimeBodyPart();
            attachmentPart.attachFile(pdfFile);
            multipart.addBodyPart(attachmentPart);
            // Ajouter le multipart au message
            message.setContent(multipart);
            // Envoyer
            Transport.send(message);
            System.out.println("Relevé de notes avec PDF envoyé à " + etudiant.getEmail());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erreur lors de l'envoi du relevé PDF à " + etudiant.getEmail());
        }
    }
}
