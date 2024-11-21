package org.luismore.hlvsapi.controllers;

import org.luismore.hlvsapi.services.impls.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/email")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/send-email")
    public String sendEmail(
            @RequestParam String to,
            @RequestParam String subject,
            @RequestParam String body) {
        emailService.sendSimpleEmail(to, subject, body);
        return "Email enviado exitosamente a: " + to;
    }
}