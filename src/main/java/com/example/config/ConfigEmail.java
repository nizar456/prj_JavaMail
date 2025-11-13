package com.example.config;
//rtrq vhks bplr gpxg
import java.util.Properties;

public class ConfigEmail {

    // Constantes pour le compte Gmail et les serveurs
    public static final String EMAIL = "sending99email@gmail.com"; // Remplace par ton email
    public static final String PASSWORD = "rtrq vhks bplr gpxg";    // Mot de passe d'application

    public static final String SMTP_HOST = "smtp.gmail.com";
    public static final String IMAP_HOST = "imap.gmail.com";

    // Méthode pour configurer SMTP
    public static Properties getPropertiesSMTP() {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", SMTP_HOST);
        props.put("mail.smtp.port", "587");
        return props;
    }

    // Méthode pour configurer IMAP
    public static Properties getPropertiesIMAP() {
        Properties props = new Properties();
        props.put("mail.store.protocol", "imaps");
        props.put("mail.imap.host", IMAP_HOST);
        props.put("mail.imap.port", "993");
        props.put("mail.imap.ssl.enable", "true");
        return props;
    }
}
