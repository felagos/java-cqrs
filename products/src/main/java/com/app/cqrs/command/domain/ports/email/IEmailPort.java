package com.app.cqrs.command.domain.ports.email;

public interface IEmailPort {
    
    void sendEmail(String to, String subject, String body);
    
}