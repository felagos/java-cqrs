package com.app.cqrs.command.infrastructure.email;

import java.util.logging.Logger;

import org.springframework.stereotype.Component;

import com.app.cqrs.command.domain.ports.email.IEmailPort;

@Component
public class MockEmailSender implements IEmailPort {

    private final Logger logger = Logger.getLogger(MockEmailSender.class.getName());

    @Override
    public void sendEmail(String to, String subject, String body) {
        logger.info("Mock Email Gateway - Sending email:");
        logger.info("To: " + to);
        logger.info("Subject: " + subject);
        logger.info("Body: " + body);
        logger.info("Email sent successfully (mock implementation)");
    }
    
}