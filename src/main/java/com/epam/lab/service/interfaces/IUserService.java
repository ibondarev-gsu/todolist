package com.epam.lab.service.interfaces;

import com.epam.lab.dto.UserDto;
import com.epam.lab.exceptions.UserAlreadyExistException;
import com.epam.lab.model.PasswordResetToken;
import com.epam.lab.model.User;
import com.epam.lab.model.VerificationToken;

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
