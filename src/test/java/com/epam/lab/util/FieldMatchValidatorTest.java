package com.epam.lab.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

class FieldMatchValidatorTest {

    private static Validator validator;

    @BeforeEach
    public static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

//    @Test
//    public void testValidPasswords() {
//        PasswordResetDto passwordReset = new PasswordResetDto();
//        passwordReset.setPassword("password");
//        passwordReset.setConfirmPassword("password");
//
//        Set<ConstraintViolation<PasswordResetDto>> constraintViolations = validator.validate(passwordReset);
////        assertEquals(constraintViolations.size(), 0);
//    }
//
//    @Test
//    public void testInvalidPassword() {
//        PasswordResetDto passwordReset = new PasswordResetDto();
//        passwordReset.setPassword("password");
//        passwordReset.setConfirmPassword("invalid-password");
//
//        Set<ConstraintViolation<PasswordResetDto>> constraintViolations = validator.validate(passwordReset);
//
//        assertEquals(constraintViolations.size(), 1);
//    }
}