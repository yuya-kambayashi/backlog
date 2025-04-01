package com.bluejeanssystems.backlog.service;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MailService {
    private final JavaMailSender mailSender;

    public void sendEmail(String to, String subject, String body) throws MessagingException {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("admin@bluejeanssystems.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
    }
}
