package com.roberto.avitec.service;

import com.roberto.avitec.domain.enums.ETipoEmail;

import java.util.Properties;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EmailService implements Runnable {

    private final Logger log = LoggerFactory.getLogger(EmailService.class);
    private Properties parametrosServidorEmail = new Properties();
    private String assunto;
    private String corpo;
    private ETipoEmail tipo;

    public EmailService(String assunto, String corpo, ETipoEmail tipo) {
        this.assunto = assunto;
        this.corpo = corpo;
        this.tipo = tipo;
    }

    private Properties configurarParametrosServidorEmail() {
        parametrosServidorEmail.put("mail.smtp.host", "smtp.gmail.com");
        parametrosServidorEmail.put("mail.smtp.socketFactory.port", "465");
        parametrosServidorEmail.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        parametrosServidorEmail.put("mail.smtp.auth", "true");
        parametrosServidorEmail.put("mail.smtp.port", "465");
        return parametrosServidorEmail;
    }

    public void enviarEmail() throws Exception {
        try {
            Session session = Session.getDefaultInstance(configurarParametrosServidorEmail(),
            new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication()
                {
                    return new PasswordAuthentication("controle.equipamentos123@gmail.com", "geovana123456789");
                }
            });
            Message email = new MimeMessage(session);
            email.setFrom(new InternetAddress("controle.equipamentos123@gmail.com"));
            Address[] toDestinatarios = {new InternetAddress("controle.equipamentos123@gmail.com")};
            email.setRecipients(Message.RecipientType.TO, toDestinatarios);
            email.setSubject(assunto);
            email.setText(corpo);
            Transport.send(email);
        } catch (Exception ex) {
            throw new Exception(ex);
        }
    }

    public void run() {
        try {
            enviarEmail();
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
    }
}
