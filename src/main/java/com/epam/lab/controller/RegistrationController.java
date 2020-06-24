package com.epam.lab.controller;

import com.epam.lab.dto.UserDto;
import com.epam.lab.exceptions.UserAlreadyExistException;
import com.epam.lab.model.PasswordResetToken;
import com.epam.lab.model.User;
import com.epam.lab.model.VerificationToken;
import com.epam.lab.registration.OnRegistrationCompleteEvent;
import com.epam.lab.service.interfaces.IUserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Calendar;
import java.util.Locale;
import java.util.Optional;

@Slf4j
@Controller
@AllArgsConstructor
@RequestMapping("/")
public class RegistrationController {

    private final IUserService userService;
    private final MessageSource messageSource;
    private final UserDetailsService userDetailsService;
    private final ApplicationEventPublisher eventPublisher;

    @GetMapping(value = "/registration")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new UserDto());
        return "registration";
    }

    @PostMapping(value = "/registration")
    public String registerUserAccount(@ModelAttribute("user") @Valid UserDto userDto, BindingResult bindingResult,
                                      Model model, HttpServletRequest request){

        if (bindingResult.hasErrors()) {
            return "registration";
        }

        try {
            User registered = userService.registerNewUserAccount(userDto);
            String appUrl = "http://"  + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
            eventPublisher.publishEvent(new OnRegistrationCompleteEvent(registered, request.getLocale(), appUrl));

        } catch (UserAlreadyExistException e) {
            model.addAttribute("message", "An account for that username/email already exists.");
            return "registration";

        }

//        securityService.autologin(userForm.getUsername(), userForm.getPasswordConfirm());

        return "redirect:/user/dashboard";
    }

    @GetMapping("/regitrationConfirm")
    public String confirmRegistration(HttpServletRequest request, Model model, @RequestParam("token") String token) {

        Locale locale = request.getLocale();

        VerificationToken verificationToken = userService.findVerificationTokenByToken(token);
        if (verificationToken == null) {
            String message = messageSource.getMessage("auth.message.invalidToken", null, locale);
            model.addAttribute("errorMessage", message);
            return "redirect:/badUser" + "?lang=" + locale.getLanguage();
        }

        User user = verificationToken.getUser();


        // TODO: 22.06.2020 исправить message на auth.message.enableUser
        if (user.isEnabled()){
            String message = messageSource.getMessage("auth.message.invalidToken", null, locale);
            model.addAttribute("errorMessage", message);
            return "redirect:/badUser" + "?lang=" + locale.getLanguage();
        }



        Calendar cal = Calendar.getInstance();
        if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            String messageValue = messageSource.getMessage("auth.message.expired", null, locale);
            model.addAttribute("message", messageValue);
            return "redirect:/badUser" + locale.getLanguage();
        }

        user.setEnabled(true);
        userService.saveRegisteredUser(user);
        return "redirect:/login";
    }

    // TODO: 23.06.2020 Дописать message
    @PostMapping(value = "/resetPassword")
    public String resetPassword(final HttpServletRequest request, final Model model, @RequestParam("email") final String userEmail) {
        final User user = userService.findUserByEmail(userEmail);

//        if (user == null) {
//            model.addAttribute("message", messages.getMessage("message.userNotFound", null, request.getLocale()));
//            return "redirect:/login?lang=" + request.getLocale().getLanguage();
//        }

        String appUrl = "http://"  + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
        eventPublisher.publishEvent(new OnRegistrationCompleteEvent(user, request.getLocale(), appUrl));

//        model.addAttribute("message", messages.getMessage("message.resetPasswordEmail", null, request.getLocale()));
        return "redirect:/login?lang=" + request.getLocale().getLanguage();
    }

    @GetMapping("/changePassword")
    // TODO: 23.06.2020 Исправить сообщения
    public String changePassword(HttpServletRequest request, Model model, @RequestParam("token") String token) {

        Locale locale = request.getLocale();

        Optional<PasswordResetToken> passwordResetTokenByToken = userService.findPasswordResetTokenByToken(token);

        if (passwordResetTokenByToken.isPresent()) {
            String message = messageSource.getMessage("auth.message.invalidToken", null, locale);
            model.addAttribute("errorMessage", message);
            return "redirect:/badUser" + "?lang=" + locale.getLanguage();
        }



        User user = passwordResetTokenByToken.get().getUser();


        // TODO: 22.06.2020 исправить message на auth.message.enableUser
//        if (user.isEnabled()){
//            String message = messages.getMessage("auth.message.invalidToken", null, locale);
//            model.addAttribute("errorMessage", message);
//            return "redirect:/badUser" + "?lang=" + locale.getLanguage();
//        }
//        Calendar cal = Calendar.getInstance();
//        if ((passwordResetTokenByToken.get().getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
//            String messageValue = messages.getMessage("auth.message.expired", null, locale);
//            model.addAttribute("message", messageValue);
//            return "redirect:/badUser" + locale.getLanguage();
//        }

        final Authentication auth = new UsernamePasswordAuthenticationToken(user, null,
                userDetailsService.loadUserByUsername(user.getUsername()).getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);

        return "redirect:/updatePassword";
    }

    @PostMapping("/savePassword")
//    @PreAuthorize("hasRole('READ_PRIVILEGE')")
    public String savePassword(final HttpServletRequest request, final Model model, @RequestParam("password") final String password) {
        final Locale locale = request.getLocale();

        final User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        userService.changeUserPassword(user, password);
//        model.addAttribute("message", messages.getMessage("message.resetPasswordSuc", null, locale));
        return "redirect:/login";
    }
}
