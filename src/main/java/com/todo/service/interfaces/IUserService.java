package com.todo.service.interfaces;

import com.todo.dto.UserDto;
import com.todo.exceptions.PasswordResetTokenNotFountException;
import com.todo.exceptions.UserAlreadyExistException;
import com.todo.exceptions.UserNotFoundException;
import com.todo.exceptions.VerificationTokenNotFountException;
import com.todo.model.PasswordResetToken;
import com.todo.model.User;
import com.todo.model.VerificationToken;


public interface IUserService {


    void createVerificationToken(User user, String token);
    void createPasswordResetToken(User user, String token);

    User findByUsername(String username) throws UserNotFoundException;
    User findUserByEmail(String userEmail) throws UserNotFoundException;
    User registerNewUserAccount(UserDto userDto) throws UserAlreadyExistException;
    User findUserByVerificationToken(String verificationToken) throws VerificationTokenNotFountException;

    VerificationToken findVerificationTokenByToken(String token) throws VerificationTokenNotFountException;
    PasswordResetToken findPasswordResetTokenByToken(String token) throws PasswordResetTokenNotFountException;

    void saveRegisteredUser(User user);
    void changeUserPassword(final User user, final String password);

}
