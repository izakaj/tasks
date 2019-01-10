package com.crud.tasks.service;

import com.crud.tasks.domain.Mail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import sun.java2d.pipe.SpanShapeRenderer;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

@Service
public class SimpleEmailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleMailMessage.class);

    @Autowired
    private JavaMailSender javaMailSender;

    public void send (final Mail mail){
        LOGGER.info("Starting email preparation...");
        try {
            SimpleMailMessage mailMessage = createMailMessage(mail);
            javaMailSender.send(mailMessage);
            LOGGER.info("Email has been sent.");
        } catch (MailException | AddressException e) {
            LOGGER.error("Failed to process email sending: ", e.getMessage(), e);
        }
    }

    private SimpleMailMessage createMailMessage(final Mail mail) throws AddressException {
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        if(validateEmail(mail.getMailTo())) {
            mailMessage.setTo(mail.getMailTo());
            LOGGER.info("TO mail is correct");
        } else {
            LOGGER.error("TO mail is incorrect.");
            throw new AddressException("TO - mail is incorrect.");
        }

        if(mail.getToCc() == null || mail.getToCc() == "") {
            LOGGER.info("No CC mail provided.");
        } else if (validateEmail(mail.getToCc())){
            mailMessage.setCc(mail.getToCc());
            LOGGER.info("CC mail has been set!");
        } else {
            LOGGER.error("CC mail is incorrect.");
            throw new AddressException("CC mail is incorrect");
        }

        mailMessage.setSubject(mail.getSubject());
        mailMessage.setText(mail.getMessage());

        LOGGER.info("Created mail:\nTo: " + mailMessage.getTo());

        return mailMessage;
    }

    private boolean validateEmail(String email) {
        boolean isValid = false;
        try {
            InternetAddress internetAddress = new InternetAddress(email);
            internetAddress.validate();
            isValid = true;
        } catch (AddressException e) {
            System.out.println("The address " + email + " is incorrect.");
        }
        return isValid;
    }
}
