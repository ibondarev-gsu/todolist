package com.todo.registration.listner;

import com.todo.model.User;
import com.todo.registration.OnRegistrationCompleteEvent;
import com.todo.service.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.event.EventListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.UUID;

//@Component("resetPasswordListener")
public class MessageSenderListener implements Runnable{
//        implements
//        ApplicationListener<MessageSendEvent>, Runnable
        protected IUserService service;
        protected MessageSource messages;
        protected JavaMailSender mailSender;

        protected SimpleMailMessage email;

        public MessageSenderListener(IUserService service, @Qualifier("messageSource") MessageSource messages,
                                    @Qualifier("javaMailSender") JavaMailSender mailSender) {
            this.service = service;
            this.messages = messages;
            this.mailSender = mailSender;
            email = new SimpleMailMessage();
        }

    // TODO: 23.06.2020 Дописать message config
    @EventListener(OnRegistrationCompleteEvent.class)
    public void confirmRegistration(OnRegistrationCompleteEvent event) {
        User user = event.getUser();
        String token = UUID.randomUUID().toString();
        service.createVerificationToken(user, token);

        String recipientAddress = user.getEmail();
        String subject = "Registration Confirmation";
        String confirmationUrl
                = event.getAppUrl() + "/regitrationConfirm?token=" + token;
//        String message = messages.getMessage("message.regSucc", null, event.getLocale());


        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setText("<a>" + confirmationUrl + "</a>");

//        mailSender.send(email);
        new Thread(this).start();
    }

//    protected IUserService userService;
//    protected MessageSource messages;
//    protected JavaMailSender mailSender;
//
//    protected SimpleMailMessage email;
//
//    public ResetPasswordListener(IUserService service, @Qualifier("messageSource") MessageSource messages,
//                                 @Qualifier("javaMailSender") JavaMailSender mailSender) {
//        this.userService = service;
//        this.messages = messages;
//        this.mailSender = mailSender;
//        email = new SimpleMailMessage();
//    }
//
//    @Override
//    public void onApplicationEvent(MessageSendEvent event) {
//        confirmRegistration(event);
//    }
//
//    protected void confirmRegistration(MessageSendEvent event) {
//        User user = event.getUser();
//
//        String token = UUID.randomUUID().toString();
//        userService.createPasswordResetToken(user, token);
//
//        String recipientAddress = user.getEmail();
//        String subject = "Reset token";
//        String confirmationUrl = event.getAppUrl() + "/changePassword??token=" + token;
////        String message = messages.getMessage("message.regSucc", null, event.getLocale());
//
//
//        email.setTo(recipientAddress);
//        email.setSubject(subject);
//        email.setText(confirmationUrl);
//
////        mailSender.send(email);
//        new Thread(this).start();
//    }
//
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

