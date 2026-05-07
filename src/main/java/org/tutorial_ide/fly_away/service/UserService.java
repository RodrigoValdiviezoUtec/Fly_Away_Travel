package org.tutorial_ide.fly_away.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tutorial_ide.fly_away.dto.UserRegisterRequestDto;
import org.tutorial_ide.fly_away.dto.UserRegisterResponseDto;
import org.tutorial_ide.fly_away.entity.AppUser;
import org.tutorial_ide.fly_away.exception.ConflictException;
import org.tutorial_ide.fly_away.exception.ResourceNotFoundException;
import org.tutorial_ide.fly_away.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserRegisterResponseDto register(UserRegisterRequestDto dto) {
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new ConflictException(
                    "Email '" + dto.getEmail() + "' is already registered");
        }

        AppUser user = AppUser.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .password(passwordEncoder.encode(dto.getPassword()))
                .build();

        AppUser saved = userRepository.save(user);
        return new UserRegisterResponseDto(saved.getId());
    }

    @Transactional(readOnly = true)
    public AppUser findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User not found with id: " + id));
    }

    @Transactional(readOnly = true)
    public AppUser findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No account found for email: " + email));
    }
}