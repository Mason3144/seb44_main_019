package com.shellwe.back.email;

import org.springframework.stereotype.Component;

@Component
public interface EmailSendable {
    void send(String[] to, String subject, String email, String templateName) throws InterruptedException;
}
