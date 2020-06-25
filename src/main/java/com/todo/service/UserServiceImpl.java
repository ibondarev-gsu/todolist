package com.todo.service;

import com.todo.dto.UserDto;
import com.todo.exceptions.PasswordResetTokenNotFountException;
import com.todo.exceptions.UserAlreadyExistException;
import com.todo.exceptions.UserNotFoundException;
import com.todo.exceptions.VerificationTokenNotFountException;
import com.todo.model.PasswordResetToken;
import com.todo.model.User;

import com.todo.model.VerificationToken;
import com.todo.repository.PasswordResetTokenRepository;
import com.todo.repository.UserRepository;
import com.todo.repository.VerificationTokenRepository;
import com.todo.service.interfaces.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;

@Service("userServiceImpl")
@Transactional(readOnly = true)
@AllArgsConstructor
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final VerificationTokenRepository verificationTokenRepository;
    private final PasswordResetTokenRepository passwordResetTokenRepository;

    @Override
    public User findByUsername(String username) throws UserNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(UserNotFoundException::new);
    }

    @Override
    @Transactional
    public User registerNewUserAccount(final UserDto userDto) throws UserAlreadyExistException {

        final String email = userDto.getEmail();

        if (userRepository.findByEmail(email).isPresent()){
            throw new UserAlreadyExistException("There is an account with that email: " + email);
        }

        return userRepository.save(User.builder()
                .email(userDto.getEmail())
                .username(userDto.getUsername())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .middleName(userDto.getMiddleName())
                .roles(Arrays.asList())
                .build()
        );
    }

    @Override
    @Transactional
    public void createVerificationToken(final User user, final String token) {
        verificationTokenRepository.save(new VerificationToken(user, token));
    }

    @Override
    public void createPasswordResetToken(User user, String token) {
        PasswordResetToken passwordResetToken = new PasswordResetToken(token, user);
        passwordResetTokenRepository.save(passwordResetToken);
    }

    @Override
    public User findUserByVerificationToken(final String verificationToken) throws VerificationTokenNotFountException {
        return verificationTokenRepository
                .findByToken(verificationToken)
                .orElseThrow(VerificationTokenNotFountException::new)
                .getUser();
    }

    @Override
    public VerificationToken findVerificationTokenByToken(final String verificationToken) throws VerificationTokenNotFountException {
        return verificationTokenRepository
                .findByToken(verificationToken)
                .orElseThrow(VerificationTokenNotFountException::new);
    }

    @Override
    public PasswordResetToken findPasswordResetTokenByToken(String token) throws PasswordResetTokenNotFountException{
        return passwordResetTokenRepository.findByToken(token).orElseThrow(PasswordResetTokenNotFountException::new);
    }

    @Override
    @Transactional
    public void saveRegisteredUser(final User user) {
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void changeUserPassword(final User user, final String password) {
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }

    @Override
    public User findUserByEmail(String userEmail) throws UserNotFoundException {
        return userRepository.findByEmail(userEmail).orElseThrow(UserNotFoundException::new);
    }

}
