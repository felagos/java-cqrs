package com.app.cqrs.command.domain.ports.shared;

public interface IEmailPort {
    
    void sendEmail(String to, String subject, String body);
    
}