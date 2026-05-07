package org.tutorial_ide.fly_away.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.tutorial_ide.fly_away.dto.LoginRequestDto;
import org.tutorial_ide.fly_away.dto.LoginResponseDto;
import org.tutorial_ide.fly_away.entity.AppUser;
import org.tutorial_ide.fly_away.exception.InvalidCredentialsException;
import org.tutorial_ide.fly_away.exception.ResourceNotFoundException;
import org.tutorial_ide.fly_away.security.JwtUtils;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    public LoginResponseDto login(LoginRequestDto dto) {
        // Distinguish between unknown email and wrong password
        AppUser user;
        try {
            user = userService.findByEmail(dto.getEmail());
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException(
                    "No account found for email: " + dto.getEmail());
        }

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Incorrect password");
        }

        String token = jwtUtils.generateToken(user.getId(), user.getEmail());
        return new LoginResponseDto(token);
    }
}