package com.todo.service;

import com.todo.dto.UserDto;
import com.todo.exceptions.PasswordResetTokenNotFountException;
import com.todo.exceptions.UserAlreadyExistException;
import com.todo.exceptions.UserNotFoundException;
import com.todo.exceptions.VerificationTokenNotFountException;
import com.todo.model.PasswordResetToken;
import com.todo.model.Role;
import com.todo.model.User;

import com.todo.model.VerificationToken;
import com.todo.repository.PasswordResetTokenRepository;
import com.todo.repository.UserRepository;
import com.todo.repository.VerificationTokenRepository;
import com.todo.service.interfaces.UserService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.DependsOn;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final VerificationTokenRepository verificationTokenRepository;
    private final PasswordResetTokenRepository passwordResetTokenRepository;

    @Override
    public User findUserByUsername(String username) throws UserNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(UserNotFoundException::new);
    }

    public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException("No user found with username: " + email));
    }

    @Override
    @Transactional
    public User createAccount(final UserDto userDto) throws UserAlreadyExistException {

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
                .roles(Collections.singleton(Role.USER))
                .build()
        );
    }

    @Override
    @Transactional
    public void saveVerificationToken(final User user, final String token) {
        user.setVerificationToken(VerificationToken.builder().token(token).build());
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void savePasswordResetToken(User user, String token) {
        user.setPasswordResetToken(PasswordResetToken.builder().token(token).build());
        userRepository.save(user);
    }

    @Override
    public VerificationToken findVerificationTokenByToken(final String verificationToken)
            throws VerificationTokenNotFountException {
        return verificationTokenRepository
                .findByToken(verificationToken)
                .orElseThrow(VerificationTokenNotFountException::new);
    }

    @Override
    public PasswordResetToken findPasswordResetTokenByToken(final String passwordResetToken)
            throws PasswordResetTokenNotFountException{
        return passwordResetTokenRepository
                .findByToken(passwordResetToken)
                .orElseThrow(PasswordResetTokenNotFountException::new);
    }

    @Override
    @Transactional
    public void saveUser(final User user) {
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void saveUserPassword(final User user, final String password) {
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void deleteUserAndVerificationToken(User user, VerificationToken token) {
        verificationTokenRepository.delete(token);
        userRepository.delete(user);
    }

    @Override
    @Transactional
    public void deletePasswordResetToken(PasswordResetToken token) {
        passwordResetTokenRepository.delete(token);
    }

    @Override
    public User findUserByEmail(String userEmail) throws UserNotFoundException {
        return userRepository.findByEmail(userEmail).orElseThrow(UserNotFoundException::new);
    }

}
