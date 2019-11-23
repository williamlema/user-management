package com.user.management.app.service.impl;

import com.user.management.app.service.api.IEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * Implementation class for email operations
 *
 * @author <a href="weleonm@gmail.com">William Leon</a>
 * @version 1.0
 * @since 1.0
 */
@Service
public class EmailServiceImpl implements IEmailService {

    private final JavaMailSender mailSender;

    @Value( "${spring.mail.username}" )
    private String email;

    @Autowired
    public EmailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    /**
     * Send mail to given email with specific subject and message
     *
     * @param toEmail
     * @param subject
     * @param message
     */
    @Override
    public void sendMail(String toEmail, String subject, String message) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(toEmail);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);
        mailMessage.setFrom(email);
        mailSender.send(mailMessage);
    }
}
