package com.todo.service.interfaces;

import com.todo.dto.UserDto;
import com.todo.exceptions.UserAlreadyExistException;
import com.todo.model.PasswordResetToken;
import com.todo.model.User;
import com.todo.model.VerificationToken;

import java.util.Optional;


public interface IUserService {
    Optional<User> findByUsername(String username);
    User registerNewUserAccount(UserDto userDto) throws UserAlreadyExistException;
    void createVerificationToken(User user, String token);
    void createPasswordResetToken(User user, String token);
    User findUserByVerificationToken(String verificationToken);
    VerificationToken findVerificationTokenByToken(String token);
    Optional<PasswordResetToken> findPasswordResetTokenByToken(String token);
    void saveRegisteredUser(User user);
    void changeUserPassword(final User user, final String password);
    User findUserByEmail(String userEmail);
}
