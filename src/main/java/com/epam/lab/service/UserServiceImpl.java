package com.epam.lab.service;

import com.epam.lab.dto.UserDto;
import com.epam.lab.exceptions.UserAlreadyExistException;
import com.epam.lab.model.PasswordResetToken;
import com.epam.lab.model.User;

import com.epam.lab.model.VerificationToken;
import com.epam.lab.repository.PasswordResetTokenRepository;
import com.epam.lab.repository.RoleRepository;
import com.epam.lab.repository.UserRepository;
import com.epam.lab.repository.VerificationTokenRepository;
import com.epam.lab.service.interfaces.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Optional;

@Service("userServiceImpl")
@Transactional(readOnly = true)
@AllArgsConstructor
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final VerificationTokenRepository verificationTokenRepository;
    private final PasswordResetTokenRepository passwordResetTokenRepository;

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    @Transactional
    public User registerNewUserAccount(final UserDto userDto) throws UserAlreadyExistException {

        final String userName = userDto.getUsername();

        if(findByUsername(userName).isPresent()){
            throw new UserAlreadyExistException("There is an account with that username: " + userName);
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
        VerificationToken verificationToken = new VerificationToken(token, user);
        verificationTokenRepository.save(verificationToken);
    }

    @Override
    public void createPasswordResetToken(User user, String token) {
        PasswordResetToken passwordResetToken = new PasswordResetToken(token, user);
        passwordResetTokenRepository.save(passwordResetToken);
    }

    @Override
    public User findUserByVerificationToken(final String verificationToken) {
        return verificationTokenRepository.findByToken(verificationToken).getUser();
    }

    @Override
    public VerificationToken findVerificationTokenByToken(final String token) {
        return verificationTokenRepository.findByToken(token);
    }

    @Override
    public Optional<PasswordResetToken> findPasswordResetTokenByToken(String token) {
        return Optional.ofNullable(passwordResetTokenRepository.findByToken(token));
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
    public User findUserByEmail(String userEmail) {
        return userRepository.findByEmail(userEmail);
    }

    private boolean emailExist(final String username) {
        return userRepository.findByUsername(username) != null;
    }
}
