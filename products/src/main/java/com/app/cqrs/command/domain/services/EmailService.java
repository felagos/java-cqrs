package com.app.cqrs.command.domain.services;

import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import com.app.cqrs.command.domain.events.ProductReservedEvent;
import com.app.cqrs.command.domain.ports.IEmailPort;

@Service
public class EmailService {

    private final Logger logger = Logger.getLogger(EmailService.class.getName());

    private final IEmailPort emailPort;

    public EmailService(IEmailPort emailPort) {
        this.emailPort = emailPort;
    }

    public void sendProductReservationEmail(ProductReservedEvent event) {
        String subject = "Product Reservation Confirmation - Order " + event.getOrderId();
        String body = buildEmailBody(event);
        String userEmail = getUserEmail(event.getUserId());

        logger.info("Sending email to: " + userEmail + " with subject: " + subject + " and body: " + body);

        emailPort.sendEmail(userEmail, subject, body);
    }

    private String buildEmailBody(ProductReservedEvent event) {
        StringBuilder bodyBuilder = new StringBuilder();
        bodyBuilder.append("Dear Customer,\n\n");
        bodyBuilder.append("Your product reservation has been confirmed!\n\n");
        bodyBuilder.append("Order Details:\n");
        bodyBuilder.append("- Order ID: ").append(event.getOrderId()).append("\n");
        bodyBuilder.append("- Product ID: ").append(event.getProductId()).append("\n");
        bodyBuilder.append("- Quantity: ").append(event.getQuantity()).append("\n");
        bodyBuilder.append("- User ID: ").append(event.getUserId()).append("\n\n");
        bodyBuilder.append("Thank you for your order!\n\n");
        bodyBuilder.append("Best regards,\n");
        bodyBuilder.append("The Product Team");

        return bodyBuilder.toString();
    }

    private String getUserEmail(String userId) {
        return "user-" + userId + "@example.com";
    }

}