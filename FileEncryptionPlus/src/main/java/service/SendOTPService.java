package service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class SendOTPService {
    public static void sendOTP(String email, String genOTP) {
        // Recipient's email ID needs to be mentioned.
        String to = email;

        // Sender's email ID needs to be mentioned
        String from = "";

        // Assuming you are sending email from through Gmail's SMTP
        String host = "smtp.gmail.com";

        // Get system properties
        Properties properties = System.getProperties();

        // Setup mail server
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");

        // Get the Session object.// and pass username and password
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {

            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, "");
            }
        });

        // Used to debug SMTP issues
        session.setDebug(true);

        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            // Set Subject: header field
            message.setSubject("FileEncryptionPlus - OTP Verification");

            // Now set the actual message
            String emailContent = "Dear User,\n" +
                    "\n" +
                    "Just a quick update to let you know that we've added an extra layer of security to your FileEncryptionPlus account. You can now log in using a one-time password (OTP) for enhanced protection.\n\n" +
                    "Your OTP is: " + genOTP + "\n\n" +
                    "Enter the OTP to complete the login process.\n\n" +
                    "As always, your security is our top priority. Reach out if you need any assistance.\n\n" +
                    "Best regards,\n" +
                    "Anurag Handique\n" +
                    "FileEncryptionPlus Team";

            message.setText(emailContent);

            System.out.println("Sending...");
            // Send message
            Transport.send(message);
            System.out.println("Message sent successfully!");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }

    }
}
