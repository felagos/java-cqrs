package com.app.cqrs.command.infrastructure.adapters.email;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Component;

import com.app.cqrs.command.domain.ports.email.IEmailPort;

@Component
public class MockEmailSender implements IEmailPort {

    private static final Logger logger = LoggerFactory.getLogger(MockEmailSender.class);

    @Override
    public void sendEmail(String to, String subject, String body) {
        logger.info("Mock Email Gateway - Sending email:");
        logger.info("To: " + to);
        logger.info("Subject: " + subject);
        logger.info("Body: " + body);
        logger.info("Email sent successfully (mock implementation)");
    }
    
}