package org.tutorial_ide.fly_away.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data @AllArgsConstructor
public class LoginResponseDto {
    private String token;
}