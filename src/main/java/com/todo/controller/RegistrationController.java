package com.todo.controller;

import com.todo.dto.UserDto;
import com.todo.exceptions.PasswordResetTokenNotFountException;
import com.todo.exceptions.UserAlreadyExistException;
import com.todo.exceptions.UserNotFoundException;
import com.todo.exceptions.VerificationTokenNotFountException;
import com.todo.model.PasswordResetToken;
import com.todo.model.User;
import com.todo.model.VerificationToken;
import com.todo.service.interfaces.MailSenderService;
import com.todo.service.interfaces.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Locale;

@Slf4j
@Controller
@AllArgsConstructor
public class RegistrationController {

    private final UserService userService;
    private final MessageSource messageSource;
    private final MailSenderService mailSenderService;

    @GetMapping
    public String home(){
        return "redirect:/user/home";
    }

    @GetMapping(value = "/registration")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new UserDto());
        return "registration";
    }

    // TODO: 05.07.2020 Messages
    @PostMapping(value = "/registration")
    public String registerUserAccount(final @ModelAttribute("user") @Valid UserDto userDto,
                                      final BindingResult bindingResult,
                                      final RedirectAttributes redirectAttributes,
                                      final ModelAndView modelAndView,
                                      final Locale locale){

        if (bindingResult.hasErrors()) {
            return "registration";
        }

        try {
            User user = userService.createAccount(userDto);
            mailSenderService.sendActiveCode(user);
            redirectAttributes.addFlashAttribute("message", "Check your email bro");
            return "redirect:/login";
        } catch (UserAlreadyExistException e) {
            modelAndView.addObject("errorMessage", "An account for that username/email already exists.");
            return "registration";
        }
    }

    @GetMapping("/active")
    public String confirmRegistration(final Locale locale,
                                      final RedirectAttributes redirectAttributes,
                                      final @RequestParam("code") String activeCode) {

        try {
            VerificationToken verificationToken = userService.findVerificationTokenByToken(activeCode);

            User user = verificationToken.getUser();

            if (user.isEnabled()){
                redirectAttributes.addFlashAttribute("errorMessage",
                        messageSource.getMessage("auth.message.invalidToken", null, locale));
                return "redirect:/login";
            }

            if (verificationToken.isExpired()) {
                redirectAttributes.addFlashAttribute("errorMessage",
                        messageSource.getMessage("auth.message.expired", null, locale));
                userService.deleteUserAndVerificationToken(user, verificationToken);
                return "redirect:/login";
            }

            user.setEnabled(true);
            userService.saveUser(user);

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
    public String resetPassword(final HttpServletRequest request,
                                final RedirectAttributes redirectAttributes,
                                @RequestParam("email") String userEmail) {

        try {


            mailSenderService.sendPasswordResetCode(userService.findUserByEmail(userEmail));

//            redirectAttributes.addFlashAttribute("message",
//                    messageSource.getMessage("message.resetPasswordEmail", null, request.getLocale()));
        } catch (UserNotFoundException e) {

        }

        redirectAttributes.addFlashAttribute("message",
                messageSource.getMessage("message.resetPasswordEmail", null, request.getLocale()));
        return "redirect:/login";
    }

    @GetMapping("/changePassword")
    public String changePassword(final RedirectAttributes redirectAttributes,
                                 final @RequestParam("code") String resetCode,
                                 final Locale locale) {

        try {
            PasswordResetToken passwordResetToken = userService.findPasswordResetTokenByToken(resetCode);

            User user = passwordResetToken.getUser();

            if (passwordResetToken.isExpired()){
                userService.deletePasswordResetToken(passwordResetToken);
                redirectAttributes.addFlashAttribute("errorMessage",
                        messageSource.getMessage("auth.message.invalidToken", null, locale));
                return "redirect:/login";
            }

            if (!user.isEnabled()){
                user.setEnabled(true);
                userService.saveUser(user);
            }

            final Authentication auth = new UsernamePasswordAuthenticationToken(user, null,
                    userService.loadUserByUsername(user.getEmail()).getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(auth);

            return "updatePassword";
        } catch (PasswordResetTokenNotFountException e) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    messageSource.getMessage("auth.message.invalidToken", null, locale));
            return "redirect:/login";
        }
    }

//    // TODO: 04.07.2020 prop
//    @GetMapping("/updatePassword")
////    @PreAuthorize("hasRole('READ_PRIVILEGE')")
//    public String getUpdatePassword(final Locale locale,
//                               final @AuthenticationPrincipal User user,
//                               final RedirectAttributes redirectAttributes,
//                               final @RequestParam("password")  String password) {
//
//        userService.saveUserPassword(user, password);
//        redirectAttributes.addAttribute("message",
//                messageSource.getMessage("message.resetPasswordSuc", null, locale));
//        return "redirect:/";
//    }

    // TODO: 04.07.2020 prop

//    @PreAuthorize("hasRole('READ_PRIVILEGE')")
    @PostMapping(value = "/updatePassword")
    public String savePassword(
            final Locale locale,
                               final @AuthenticationPrincipal User user,
                               final RedirectAttributes redirectAttributes,
                               final @RequestParam("password")  String password
    ) {

//        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        userService.saveUserPassword(user, password);
        redirectAttributes.addFlashAttribute("message",
                messageSource.getMessage("message.resetPasswordSuc", null, locale));
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
