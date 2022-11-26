/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.mii.serverside.controller;

import co.id.mii.serverside.model.dto.request.EmailRequest;
import co.id.mii.serverside.service.JavaMailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author MSI-JO
 */
@RestController
@RequestMapping("/email")
public class EmailController {

    private JavaMailSenderService mailSenderService;

    @Autowired
    public EmailController(JavaMailSenderService mailSenderService) {
        this.mailSenderService = mailSenderService;
    }

    @PostMapping
    public EmailRequest sendSimpleEmail(@RequestBody EmailRequest emailRequest) {
        return mailSenderService.sendSimpleEmail(emailRequest);
    }
    
    
    @PostMapping("/attach")
    public EmailRequest sendEmailWithAttachment(@RequestBody EmailRequest emailRequest) {
        return mailSenderService.sendEmailWithAttachment(emailRequest);
    }

}
