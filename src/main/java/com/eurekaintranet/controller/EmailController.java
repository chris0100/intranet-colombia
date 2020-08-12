/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eurekaintranet.controller;

import javax.faces.bean.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.mail.MessagingException;
import java.io.FileNotFoundException;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Luis Carlos Cabal
 */
@RequestScoped
@Named
public class EmailController {

    private String body;
    private String subject;
    private String to;

    /**
     * Creates a new instance of EmailController
     */
    public EmailController() {
    }
    
   public void sendMailAdjunto(String para , String asunto, String correo, String adjunto , String nombreAdjunto) throws FileNotFoundException {
        // Recipient's email ID needs to be mentioned.
        String to = para;
        // Sender's email ID needs to be mentioned
         String from = "notificaciones@ekazipper.com";
        final String username = "notificaciones@ekazipper.com";//change accordingly
        final String password = "Nto*20-03Aut";//change accordingly
        // Assuming you are sending email through relay.jangosmtp.net
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.office365.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        // Get the Session object.
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
        try {
            // Create a default MimeMessage object.
            Message message = new MimeMessage(session);
            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));
            // Set To: header field of the header.
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to));
            // Set Subject: header field
            message.setSubject(asunto);
            // Create the message part
            BodyPart messageBodyPart = new MimeBodyPart();
            // Now set the actual message
            messageBodyPart.setText(correo);
            // Create a multipar message
            Multipart multipart = new MimeMultipart();
            // Set text message part
            multipart.addBodyPart(messageBodyPart);
            // Part two is attachment
            messageBodyPart = new MimeBodyPart();
            String filename = "";
            if(adjunto.contains("config")){
                filename = adjunto;
            }else{
                filename = FacesContext.getCurrentInstance().getExternalContext().getRealPath(adjunto);
            }
            DataSource source = new FileDataSource(filename);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(nombreAdjunto);
            multipart.addBodyPart(messageBodyPart);
            // Send the complete message parts
            message.setContent(multipart);
            // Send message
            Transport.send(message);
            FacesMessage messageConfirmacion = new FacesMessage(FacesMessage.SEVERITY_INFO, "Correo enviado correctamente", "El volante fue enviado al siguiente correo: "+para);
            RequestContext.getCurrentInstance().showMessageInDialog(messageConfirmacion);
            limpiar();
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    
    public void sendMail(String para , String asunto, String correo) throws FileNotFoundException {
        // Recipient's email ID needs to be mentioned.
        String to = para;
        // Sender's email ID needs to be mentioned
        String from = "notificaciones@ekazipper.com";
        final String username = "notificaciones@ekazipper.com";//change accordingly
        final String password = "Nto*20-03Aut";//change accordingly
        // Assuming you are sending email through relay.jangosmtp.net
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.office365.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        // Get the Session object.
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
        try {
            // Create a default MimeMessage object.
            Message message = new MimeMessage(session);
            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));
            // Set To: header field of the header.
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to));
            // Set Subject: header field
            message.setSubject(asunto);
            // Now set the actual message
            message.setText(correo);
            // Create a multipar message
            Transport.send(message);
            //FacesMessage messageConfirmacion = new FacesMessage(FacesMessage.SEVERITY_INFO, "Correo enviado correctamente", "El volante fue enviado al siguiente correo: "+para);
            //RequestContext.getCurrentInstance().showMessageInDialog(messageConfirmacion);
            limpiar();
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    private void limpiar() {
        setBody("");
        setSubject("");
        setTo("");
    }

    
    public String getBody() {
        return body;
    }

    
    public void setBody(String body) {
        this.body = body;
    }

   
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    private void addErrorMessage(String title, String msg) {
        FacesMessage facesMsg
                = new FacesMessage(FacesMessage.SEVERITY_ERROR, title, msg);
        FacesContext.getCurrentInstance().addMessage(null, facesMsg);
    }

    private void addSuccessMessage(String title, String msg) {
        FacesMessage facesMsg
                = new FacesMessage(FacesMessage.SEVERITY_INFO, title, msg);
        FacesContext.getCurrentInstance().addMessage("successInfo", facesMsg);
    }
}
