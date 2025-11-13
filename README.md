# JAVAMail

**JavaMail** est une API (Application Programming Interface) fournie par Java pour envoyer, recevoir et gérer des courriers électroniques à partir d’applications Java.

Elle permet d’interagir avec différents **protocoles de messagerie** tels que :

- **SMTP** (Simple Mail Transfer Protocol) → pour **envoyer** des emails
- **IMAP** (Internet Message Access Protocol) → pour **lire et gérer** les emails
- **POP3** (Post Office Protocol) → pour **récupérer** les emails depuis un serveur

JavaMail est souvent utilisée pour automatiser l’envoi de mails (notifications, rapports, convocations, etc.) ou pour lire les messages entrants (demandes, formulaires, etc.).

## ⚙️ **Fonctionnalités principales de JavaMail**

### 1. **Envoi d’emails (via SMTP)**

- Permet d’envoyer des emails en texte simple ou au format HTML.
- Utilise une **Session** JavaMail configurée avec les propriétés du serveur SMTP.
- Exemple de propriétés typiques pour Gmail :
    
    ```java
    mail.smtp.host = "smtp.gmail.com"
    mail.smtp.port = 587
    mail.smtp.auth = true
    mail.smtp.starttls.enable = true
    
    ```
    
- Étapes :
    1. Créer une `Session` avec authentification (email + mot de passe d’application)
    2. Créer un `MimeMessage`
    3. Définir : expéditeur, destinataire, sujet et contenu
    4. Envoyer avec `Transport.send(message)`

### 2. **Envoi d’emails HTML**

- Permet d’envoyer des messages avec une mise en forme (couleurs, tableaux, liens…).
- Le contenu du message est au format HTML :
    
    ```java
    message.setContent("<h1>Relevé de Notes</h1><p>Votre moyenne : 15.5</p>", "text/html");
    
    ```
    
- Très utilisé pour des messages plus professionnels (convocations, rapports, newsletters…).

### 3. **Envoi d’emails avec pièce jointe**

- Grâce à la classe `MimeMultipart`, on peut ajouter plusieurs parties :
    - Une partie pour le texte/HTML
    - Une ou plusieurs pièces jointes (PDF, images, etc.)
    
    Exemple :
    
    ```java
    MimeBodyPart textPart = new MimeBodyPart();
    textPart.setContent("Voici votre relevé de notes", "text/html");
    
    MimeBodyPart attachment = new MimeBodyPart();
    attachment.attachFile("C:/releve.pdf");
    
    Multipart multipart = new MimeMultipart();
    multipart.addBodyPart(textPart);
    multipart.addBodyPart(attachment);
    message.setContent(multipart);
    
    ```
    

### 4. **Lecture des emails (via IMAP)**

- Permet d’accéder à la boîte de réception (`INBOX`) d’un compte mail.
- Étapes :
    1. Créer une session IMAP (`imap.gmail.com`, port 993)
    2. Se connecter avec le `Store` (`store.connect(...)`)
    3. Ouvrir le dossier `INBOX`
    4. Lire les `Message[] messages`
    5. Extraire l’expéditeur, le sujet, la date, et le contenu

### 5. **Filtrage et classification des messages**

- On peut lire uniquement les **messages non lus** à l’aide des **drapeaux (flags)** :
    
    ```java
    if (!message.isSet(Flags.Flag.SEEN)) { ... }
    
    ```
    
- On peut ensuite **analyser le sujet** pour déterminer le type de message (certificat, réclamation, etc.).

### 6. **Statistiques sur la boîte mail**

- JavaMail permet de calculer facilement :
    - Le **nombre total** de messages
    - Le **nombre de messages non lus**
    - Le **nombre de messages récents**
    
    Utile pour suivre l’activité de la messagerie (ex : dans un tableau de bord administratif).
