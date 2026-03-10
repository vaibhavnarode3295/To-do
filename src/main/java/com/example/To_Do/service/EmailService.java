package com.example.To_Do.service;

import com.resend.*;
import com.resend.services.emails.model.CreateEmailOptions;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    public void sendEmail(String to, String subject, String content) {
        Resend resend = new Resend("re_G1nsHcEC_7WLMn1WneZSByFuYKwHbbBdY");

        CreateEmailOptions params = CreateEmailOptions.builder()
                .from("onboarding@resend.dev") // Use this for testing
                .to(to)
                .subject(subject)
                .html("<strong>" + content + "</strong>")
                .build();

        try {
            resend.emails().send(params);
            System.out.println("Email sent successfully!");
        } catch (Exception e) {
            System.err.println("Failed to send email: " + e.getMessage());
        }
    }
}
