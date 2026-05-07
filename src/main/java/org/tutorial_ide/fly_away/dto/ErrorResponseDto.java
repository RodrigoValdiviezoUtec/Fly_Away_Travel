package org.tutorial_ide.fly_away.dto;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data @Builder
public class ErrorResponseDto {
    private int status;
    private String error;
    private List<String> messages;
    private LocalDateTime timestamp;
}