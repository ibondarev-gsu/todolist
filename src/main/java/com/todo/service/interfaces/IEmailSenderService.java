package com.todo.service.interfaces;

import org.springframework.mail.SimpleMailMessage;

public interface IEmailSenderService {
    void sendEmail(SimpleMailMessage email);
}
