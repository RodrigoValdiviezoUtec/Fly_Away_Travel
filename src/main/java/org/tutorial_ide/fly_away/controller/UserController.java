package org.tutorial_ide.fly_away.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.tutorial_ide.fly_away.dto.UserRegisterRequestDto;
import org.tutorial_ide.fly_away.dto.UserRegisterResponseDto;
import org.tutorial_ide.fly_away.service.UserService;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /** POST /users/register — Public */
    @PostMapping("/register")
    public ResponseEntity<UserRegisterResponseDto> register(
            @Valid @RequestBody UserRegisterRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userService.register(dto));
    }

    /** GET /users/{id} — Protected (generic GET by ID) */
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        var user = userService.findById(id);
        // Return a safe projection (no password)
        return ResponseEntity.ok(java.util.Map.of(
                "id", user.getId(),
                "firstName", user.getFirstName(),
                "lastName", user.getLastName(),
                "email", user.getEmail()
        ));
    }
}