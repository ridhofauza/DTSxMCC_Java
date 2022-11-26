/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.mii.serverside.service;

import co.id.mii.serverside.model.dto.request.EmailRequest;
import java.io.File;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

/**
 *
 * @author MSI-JO
 */
@Component
public class JavaMailSenderService {

    private JavaMailSender mailSender;

    @Autowired
    public JavaMailSenderService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public EmailRequest sendSimpleEmail(EmailRequest emailRequest) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(emailRequest.getToEmail());
        message.setSubject(emailRequest.getSubject());
        message.setText(emailRequest.getBody());

        mailSender.send(message);
        System.out.println("Mail Send .......");
        return emailRequest;
    }

    public EmailRequest sendEmailWithAttachment(EmailRequest emailRequest) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);

            messageHelper.setTo(emailRequest.getToEmail());
            messageHelper.setSubject(emailRequest.getSubject());
            messageHelper.setText(emailRequest.getBody());

            FileSystemResource fileSystemResource = new FileSystemResource(new File(emailRequest.getAttachment()));
            messageHelper.addAttachment(fileSystemResource.getFilename(), fileSystemResource);

            mailSender.send(mimeMessage);
            System.out.println("Send Mail With Attach");
        } catch (MessagingException e) {
            throw new IllegalStateException("Failed to send email");
        }
        return emailRequest;

    }
}
