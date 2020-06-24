package com.epam.lab.controller;

//@Slf4j
//@Controller
//@RequestMapping("/")
public class UserController {

//    private final Logger logger = LoggerFactory.getLogger(UserController.class);
//
//    private final IUserService userService;
//    private final ApplicationEventPublisher eventPublisher;
//    private final MessageSource messages;
//
//
//    public UserController(IUserService userService, ApplicationEventPublisher eventPublisher, @Qualifier("messageSource") MessageSource messages) {
//        this.userService = userService;
//        this.eventPublisher = eventPublisher;
//        this.messages = messages;
//    }
//
//    @GetMapping(value = "/registration")
//    public String showRegistrationForm(Model model) {
//        model.addAttribute("user", new UserDto());
//        return "registration";
//    }
//
//    @PostMapping(value = "/registration")
//    public String registerUserAccount(@ModelAttribute("user") @Valid UserDto userDto, BindingResult bindingResult,
//                                      Model model, HttpServletRequest request){
//        if (bindingResult.hasErrors()) {
//            return "registration";
//        }
//
//        try {
//            User registered = userService.registerNewUserAccount(userDto);
//            String appUrl = "http://"  + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
//            eventPublisher.publishEvent(new OnRegistrationCompleteEvent(registered,
//                    request.getLocale(), appUrl));
//        } catch (UserAlreadyExistException e) {
//            model.addAttribute("message", "An account for that username/email already exists.");
//            return "registration";
//        }
////        securityService.autologin(userForm.getUsername(), userForm.getPasswordConfirm());
//
//        return "redirect:/user/dashboard";
//    }
//
//    @GetMapping("/regitrationConfirm")
//    public String confirmRegistration(HttpServletRequest request, Model model, @RequestParam("token") String token) {
//
//        Locale locale = request.getLocale();
//
//        VerificationToken verificationToken = userService.findVerificationTokenByTokenName(token);
//        if (verificationToken == null) {
//            String message = messages.getMessage("auth.message.invalidToken", null, locale);
//            model.addAttribute("errorMessage", message);
//            return "redirect:/badUser" + "?lang=" + locale.getLanguage();
//        }
//
//        User user = verificationToken.getUser();
//
//
//        // TODO: 22.06.2020 исправить message на auth.message.enableUser
//        if (user.isEnabled()){
//            String message = messages.getMessage("auth.message.invalidToken", null, locale);
//            model.addAttribute("errorMessage", message);
//            return "redirect:/badUser" + "?lang=" + locale.getLanguage();
//        }
//
//
//
//        Calendar cal = Calendar.getInstance();
//        if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
//            String messageValue = messages.getMessage("auth.message.expired", null, locale);
//            model.addAttribute("message", messageValue);
//            return "redirect:/badUser" + locale.getLanguage();
//        }
//
//        user.setEnabled(true);
//        userService.saveRegisteredUser(user);
//        return "redirect:/login";
//    }

}
