package org.tutorial_ide.fly_away.security;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class JwtAuthenticationPrincipal {
    private final Long userId;
    private final String email;
}