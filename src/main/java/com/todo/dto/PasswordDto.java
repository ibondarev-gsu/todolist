package com.todo.dto;

import com.todo.annotations.ValidPassword;
import lombok.Data;

@Data
public class PasswordDto {
    private String oldPassword;
    private String token;
    @ValidPassword
    private String newPassword;
}
