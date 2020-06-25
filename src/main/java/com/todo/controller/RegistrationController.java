package com.todo.controller;

import com.todo.dto.UserDto;
import com.todo.exceptions.PasswordResetTokenNotFountException;
import com.todo.exceptions.UserAlreadyExistException;
import com.todo.exceptions.UserNotFoundException;
import com.todo.exceptions.VerificationTokenNotFountException;
import com.todo.model.PasswordResetToken;
import com.todo.model.User;
import com.todo.model.VerificationToken;
import com.todo.registration.OnRegistrationCompleteEvent;
import com.todo.service.interfaces.IUserService;
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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Locale;
import java.util.Optional;
import java.util.StringJoiner;

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
                                      ModelAndView model, HttpServletRequest request){

        if (bindingResult.hasErrors()) {
            return "registration";
        }

        try {
            User user = userService.registerNewUserAccount(userDto);

            eventPublisher.publishEvent(new OnRegistrationCompleteEvent(user, request.getLocale(), createAppUrl(request)));

        } catch (UserAlreadyExistException e) {
            model.addObject("errorMessage", "An account for that username/email already exists.");
            return "registration";
        }

//        securityService.autologin(userForm.getUsername(), userForm.getPasswordConfirm());

        return "redirect:/user/";
    }




    @GetMapping("/registrationConfirm")
    public String confirmRegistration(final HttpServletRequest request, final RedirectAttributes redirectAttributes,
                                      final @RequestParam("token") String token) {

        Locale locale = request.getLocale();

        try {
            VerificationToken verificationToken = userService.findVerificationTokenByToken(token);

            User user = verificationToken.getUser();

            if (user.isEnabled()){
                redirectAttributes.addFlashAttribute("errorMessage",
                        messageSource.getMessage("auth.message.invalidToken", null, locale));
                return "redirect:/login";
            }

            if (verificationToken.getExpiryDate().compareTo(Date.valueOf(LocalDate.now())) <= 0) {
                redirectAttributes.addFlashAttribute("errorMessage",
                        messageSource.getMessage("auth.message.expired", null, locale));
                return "redirect:/login";
            }

            user.setEnabled(true);
            userService.saveRegisteredUser(user);

            redirectAttributes.addFlashAttribute("message",
                    messageSource.getMessage("message.account.enable", null, locale));
            return "redirect:/login";

        } catch (VerificationTokenNotFountException e) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    messageSource.getMessage("auth.message.invalidToken", null, locale));
            return "redirect:/login";
        }
    }

    // TODO: 23.06.2020 Дописать message
    @PostMapping(value = "/resetPassword")
    public String resetPassword(final HttpServletRequest request, final RedirectAttributes redirectAttributes,
                                @RequestParam("email") final String userEmail) {



        eventPublisher.publishEvent(new OnRegistrationCompleteEvent(userEmail, request.getLocale(), createAppUrl(request)));

        redirectAttributes.addFlashAttribute("message",
                messageSource.getMessage("message.resetPasswordEmail", null, request.getLocale()));

        return "redirect:/login";

    }

    @GetMapping("/changePassword")
    // TODO: 23.06.2020 Исправить сообщения
    public String changePassword(HttpServletRequest request, Model model, @RequestParam("token") String token) {

        Locale locale = request.getLocale();

        PasswordResetToken passwordResetTokenByToken = null;
        try {
            passwordResetTokenByToken = userService.findPasswordResetTokenByToken(token);

            User user = passwordResetTokenByToken.getUser();

            final Authentication auth = new UsernamePasswordAuthenticationToken(user, null,
                    userDetailsService.loadUserByUsername(user.getUsername()).getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(auth);

            return "redirect:/updatePassword";
        } catch (PasswordResetTokenNotFountException e) {
            String message = messageSource.getMessage("auth.message.invalidToken", null, locale);
            model.addAttribute("errorMessage", message);
            return "redirect:/badUser" + "?lang=" + locale.getLanguage();
        }





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

    private String createAppUrl(final HttpServletRequest request){
        return new StringBuilder()
                .append("http://")
                .append(request.getServerName())
                .append(":")
                .append(request.getServerPort())
                .append(request.getContextPath())
                .toString();
    }

}
