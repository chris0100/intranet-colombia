/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eurekaintranet.sessions;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.naming.NamingException;

/**
 *
 * @author 
 */
@Stateless
public class EmailSessionBean {

    @Resource(name = "mail/newsession")
    private Session mailSession;

    public void sendMail(String email, String subject, String body) throws NamingException, MessagingException {
        Message message = new MimeMessage(mailSession);
        //message.setFrom(new InternetAddress(mailSession.getProperty("mail.from")));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email, false));
        message.setSubject(subject);
        message.setText(body);
        //message.setSentDate(new Date());
        Transport.send(message);
    }
}
