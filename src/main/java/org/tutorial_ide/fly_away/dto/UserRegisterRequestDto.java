package org.tutorial_ide.fly_away.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class UserRegisterRequestDto {

    @NotBlank(message = "First name is required")
    @Pattern(regexp = ".*[A-Z].*",
            message = "First name must contain at least one uppercase letter")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Pattern(regexp = ".*[A-Z].*",
            message = "Last name must contain at least one uppercase letter")
    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d).+$",
            message = "Password must contain at least one letter and one number")
    private String password;
}