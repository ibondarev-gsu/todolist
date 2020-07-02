package com.todo.registration.listner;

import com.todo.model.User;
import com.todo.registration.OnRegistrationCompleteEvent;
import com.todo.registration.OnResetPasswordEvent;
import com.todo.service.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.event.EventListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class MessageSenderListener implements Runnable {

    protected IUserService userService;
    protected MessageSource messages;
    protected JavaMailSender mailSender;

    protected SimpleMailMessage email;


    public MessageSenderListener(IUserService service, @Qualifier("messageSource") MessageSource messages,
                                 @Qualifier("javaMailSender") JavaMailSender mailSender) {
        this.userService = service;
        this.messages = messages;
        this.mailSender = mailSender;
        this.email = new SimpleMailMessage();
    }

    // TODO: 23.06.2020 Дописать message config
    @EventListener(OnRegistrationCompleteEvent.class)
    public void confirmRegistration(OnRegistrationCompleteEvent event) {
        User user = event.getUser();

        String token = UUID.randomUUID().toString();

        userService.createVerificationToken(user, token);

        String confirmationUrl = event.getAppUrl() + "/registrationConfirm?token=" + token;


        email.setTo(user.getEmail());
        email.setSubject("Registration Confirmation");
        email.setText(confirmationUrl);

        new Thread(this).start();
    }


    // TODO: 25.06.2020 message
    @EventListener(OnResetPasswordEvent.class)
    public void resetPassword(OnResetPasswordEvent event) {
////        User user = event.getUser();
//
//        String token = UUID.randomUUID().toString();
//
//        userService.createPasswordResetToken(user, token);
//
//        String confirmationUrl = event.getAppUrl() + "/changePassword??token=" + token;
////        String message = messages.getMessage("message.regSucc", null, event.getLocale());
//
//
//        email.setTo(user.getEmail());
//        email.setSubject("Reset password");
//        email.setText(confirmationUrl);
//
//        new Thread(this).start();
    }


    /**
     * When an object implementing interface {@code Runnable} is used
     * to create a thread, starting the thread causes the object's
     * {@code run} method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method {@code run} is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        mailSender.send(email);
    }
}

