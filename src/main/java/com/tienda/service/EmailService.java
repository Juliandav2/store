package com.tienda.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private static final Logger log = LoggerFactory.getLogger(EmailService.class);
    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;

    public EmailService (JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Async
    public void sendWelcomeEmail (String to, String username) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(from);
            message.setTo(to);
            message.setSubject("Welcome to Tienda API");
            message.setText("Hello " + username + ", \n\n" +
                    "Your account has been successfully created. \n\n" +
                    "Thank you for registering. \n\n" +
                    "Tienda API");
            mailSender.send(message);
            log.info("Welcome email sent to {}", to);
        } catch (Exception e) {
            log.error("Failed to send welcome email to {}: {}", to, e.getMessage());
        }
    }

    @Async
    public void sendOrderStatusEmail (String to, String orderId, String state) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(from);
            message.setTo(to);
            message.setSubject("Your order status has changed");
            message.setText("Hello, \n\n" +
                    "Your order " + orderId + " has changed to the status: " + state + " \n\n" +
                    "Thank you for your purchase. \n\n" +
                    "Tienda API");
            mailSender.send(message);
            log.info("Order status email sent to {} for order {}", to, orderId);
        } catch (Exception e) {
            log.error("Failed to send order status email to {}: {}", to, e.getMessage());
        }
    }
}
