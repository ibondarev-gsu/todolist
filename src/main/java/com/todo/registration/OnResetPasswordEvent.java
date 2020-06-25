package com.todo.registration;

import com.todo.model.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

import java.util.Locale;

@Getter
@Setter
public class OnResetPasswordEvent extends ApplicationEvent {
    private final String userEmail;
    private final Locale locale;
    private final String appUrl;

    /**
     * Create a new {@code ApplicationEvent}.
     *
     * @param userEmail the object on which the event initially occurred or with
     *               which the event is associated (never {@code null})

     * @param locale
     * @param appUrl
     */
    public OnResetPasswordEvent(String userEmail, Locale locale, String appUrl) {
        super(userEmail);
        this.userEmail = userEmail;
        this.locale = locale;
        this.appUrl = appUrl;
    }
}
