package com.todo.service.interfaces;

import com.todo.dto.UserDto;
import com.todo.exceptions.PasswordResetTokenNotFountException;
import com.todo.exceptions.UserAlreadyExistException;
import com.todo.exceptions.UserNotFoundException;
import com.todo.exceptions.VerificationTokenNotFountException;
import com.todo.model.PasswordResetToken;
import com.todo.model.User;
import com.todo.model.VerificationToken;
import org.springframework.security.core.userdetails.UserDetailsService;


public interface UserService extends UserDetailsService{

    void saveVerificationToken(final User user, final String token);
    void savePasswordResetToken(final User user, final String token);

    User findUserByUsername(final String username) throws UserNotFoundException;
    User findUserByEmail(final String userEmail) throws UserNotFoundException;
    User createAccount(final UserDto userDto) throws UserAlreadyExistException;

    VerificationToken findVerificationTokenByToken(final String token) throws VerificationTokenNotFountException;
    PasswordResetToken findPasswordResetTokenByToken(final String token) throws PasswordResetTokenNotFountException;

    void saveUser(final User user);
    void saveUserPassword(final User user, final String password);
    void deleteUserAndVerificationToken(final User user, final VerificationToken token);
    void deletePasswordResetToken(final PasswordResetToken token);
}
