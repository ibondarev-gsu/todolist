package com.todo.registration;

import com.todo.model.User;
import lombok.*;
import org.springframework.context.ApplicationEvent;

import java.util.Locale;

@Getter
@Setter
public class OnRegistrationCompleteEvent extends ApplicationEvent {

    private final User user;
    private final Locale locale;
    private final String appUrl;

    /**
     * Create a new {@code ApplicationEvent}.
     *
     * @param user the object on which the event initially occurred or with
     *               which the event is associated (never {@code null})

     * @param locale
     * @param appUrl
     */
    public OnRegistrationCompleteEvent(User user, Locale locale, String appUrl) {
        super(user);
        this.user = user;
        this.locale = locale;
        this.appUrl = appUrl;
    }
}
