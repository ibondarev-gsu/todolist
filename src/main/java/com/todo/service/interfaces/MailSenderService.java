package com.todo.service.interfaces;

import com.todo.model.User;
import org.springframework.mail.SimpleMailMessage;

public interface MailSenderService {
    void sendActiveCode(User user);
    void sendPasswordResetCode(User user);
}
