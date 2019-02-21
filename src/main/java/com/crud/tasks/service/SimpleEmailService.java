package com.crud.tasks.service;

import com.crud.tasks.domain.Mail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Service
public class SimpleEmailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleMailMessage.class);

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private MailCreatorService mailCreatorService;

    private MimeMessagePreparator createMimeMessage(final Mail mail) {
        return mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setTo(mail.getMailTo());
            messageHelper.setSubject(mail.getSubject());
            messageHelper.setText(mailCreatorService.buildTrelloCardEmail(mail.getMessage()), true);
        };
    }

    private SimpleMailMessage createMailMessage(final Mail mail) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(mail.getMailTo());
        mailMessage.setSubject(mail.getSubject());
        mailMessage.setText(mailCreatorService.buildTrelloCardEmail(mail.getMessage()));
        return mailMessage;
    }


    public void send (final Mail mail){
        LOGGER.info("Starting email preparation...");
        try {
            MimeMessagePreparator mimeMessage = createMimeMessage(mail);
            //SimpleMailMessage mailMessage = createMailMessage(mail);
            LOGGER.info("Mime Message created.");
            javaMailSender.send(mimeMessage);
            LOGGER.info("Email has been sent.");
        } catch (MailException e) {
            LOGGER.error("Failed to process email sending: ", e.getMessage(), e);
        }
    }


    /*
    private SimpleMailMessage createMailMessage(final Mail mail) throws AddressException {
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        validateEmail(mail.getMailTo());
        mailMessage.setTo(mail.getMailTo());
        LOGGER.info("TO mail is correct");

        if(mail.getToCc() == null || mail.getToCc().equals("")) {
            LOGGER.info("No CC mail provided.");
        } else {
            validateEmail(mail.getToCc());
            mailMessage.setCc(mail.getToCc());
            LOGGER.info("CC mail has been set!");
        }

        mailMessage.setSubject(mail.getSubject());
        mailMessage.setText(mail.getMessage());

        LOGGER.info("Created mail:\nTo: " + mailMessage.getTo());

        return mailMessage;
    }

    private void validateEmail(String email) throws AddressException {

        InternetAddress internetAddress = new InternetAddress(email);
        internetAddress.validate();
    }
    */
}
