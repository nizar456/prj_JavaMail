package com.example.services;

import com.example.config.ConfigEmail;
import com.example.modeles.Demande;

import javax.mail.*;
import javax.mail.internet.MimeMultipart;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

public class ServiceReception {
    public List<Demande> lireDemandesNonLues() {
        List<Demande> demandes = new ArrayList<>();
        Properties props = ConfigEmail.getPropertiesIMAP();
        try {
            // 1. Connexion IMAP
            Session session = Session.getDefaultInstance(props, null);
            Store store = session.getStore("imaps");
            store.connect(ConfigEmail.IMAP_HOST, ConfigEmail.EMAIL, ConfigEmail.PASSWORD);
            // 2. Ouvrir le dossier INBOX
            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_ONLY);
            // 3. Récupérer tous les messages
            Message[] messages = inbox.getMessages();
            for (Message msg : messages) {
                // 4. Filtrer les messages non lus
                if (!msg.isSet(Flags.Flag.SEEN)) {
                    String expediteur = msg.getFrom()[0].toString();
                    String sujet = msg.getSubject();
                    String contenu = getTextFromMessage(msg);
                    Date dateReception = msg.getReceivedDate();
                    // Créer une demande
                    Demande demande = new Demande(expediteur, sujet, contenu, dateReception);
                    demandes.add(demande);
                }
            }
            // 5. Fermer proprement
            inbox.close(false);
            store.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 6. Retourner la liste
        return demandes;
    }
    // Méthode utilitaire pour extraire le texte du message
    private String getTextFromMessage(Message message) throws MessagingException, IOException {
        Object content = message.getContent();
        if (content instanceof String) {
            return (String) content;
        } else if (content instanceof MimeMultipart) {
            MimeMultipart multipart = (MimeMultipart) content;
            BodyPart bodyPart = multipart.getBodyPart(0);
            return bodyPart.getContent().toString();
        }
        return "";
    }
    // ✅ Méthode pour lire les statistiques
    public void afficherStatistiques() {
        try {
            // 1️⃣ Configuration des propriétés IMAP
            Properties props = ConfigEmail.getPropertiesIMAP();
            // 2️⃣ Création d'une session
            Session session = Session.getInstance(props);
            // 3️⃣ Connexion à la boîte mail
            Store store = session.getStore("imaps");
            store.connect(ConfigEmail.IMAP_HOST, ConfigEmail.EMAIL, ConfigEmail.PASSWORD);
            // 4️⃣ Accès au dossier INBOX
            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_ONLY);
            // 5️⃣ Récupération des messages
            Message[] messages = inbox.getMessages();
            // 6️⃣ Calcul des statistiques
            int totalMessages = messages.length;
            int nonLus = 0;
            int nouveaux = 0;
            for (Message msg : messages) {
                if (!msg.isSet(Flags.Flag.SEEN)) {
                    nonLus++;
                }
                if (msg.isSet(Flags.Flag.RECENT)) {
                    nouveaux++;
                }
            }
            // 7️⃣ Affichage des statistiques
            System.out.println("📊 === Statistiques de la boîte mail ===");
            System.out.println("Nombre total de messages : " + totalMessages);
            System.out.println("Messages non lus : " + nonLus);
            System.out.println("Messages récents : " + nouveaux);
            // 8️⃣ Fermeture des connexions
            inbox.close(false);
            store.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
