package com.epam.lab.dto;

import com.epam.lab.annotations.ValidPassword;
import lombok.Data;

@Data
public class PasswordDto {
    private String oldPassword;
    private String token;
    @ValidPassword
    private String newPassword;
}
