package com.todo.validator;


import com.todo.model.User;
import com.todo.service.interfaces.UserService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component("userSignUpValidator")
public class UserSignUpValidator implements Validator {

    private final UserService userService;

    public UserSignUpValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;

//        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "NotEmpty");
//        if (user.getUsername().length() < 6 || user.getUsername().length() > 32) {
//            errors.rejectValue("username", "Size.userForm.username");
//        }
//
//        if (userService.findByUsername(user.getUsername()) != null) {
//            errors.rejectValue("username", "Duplicate.userForm.username");
//        }

//        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");
//        if (user.getPassword().length() < 6 || user.getPassword().length() > 32) {
//            errors.rejectValue("password", "Size.userForm.password");
//        }
//
//        if (!user.getConfirmPassword().equals(user.getPassword())) {
//            errors.rejectValue("passwordConfirm", "Diff.userForm.passwordConfirm");
//        }
    }
}
