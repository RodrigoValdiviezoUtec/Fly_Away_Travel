package org.tutorial_ide.fly_away.exception;

import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.tutorial_ide.fly_away.dto.ErrorResponseDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handleValidation(
            MethodArgumentNotValidException ex) {

        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fe -> fe.getField() + ": " + fe.getDefaultMessage())
                .collect(Collectors.toList());

        return build(HttpStatus.BAD_REQUEST, "Validation Failed", errors);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponseDto> handleBusiness(BusinessException ex) {
        return build(HttpStatus.BAD_REQUEST, "Business Rule Violation",
                List.of(ex.getMessage()));
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ErrorResponseDto> handleConflict(ConflictException ex) {
        return build(HttpStatus.BAD_REQUEST, "Conflict", List.of(ex.getMessage()));
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleNotFound(
            ResourceNotFoundException ex) {
        return build(HttpStatus.NOT_FOUND, "Not Found", List.of(ex.getMessage()));
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ErrorResponseDto> handleInvalidCredentials(
            InvalidCredentialsException ex) {
        return build(HttpStatus.BAD_REQUEST, "Unauthorized", List.of(ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleGeneric(Exception ex) {
        return build(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error",
                List.of(ex.getMessage()));
    }

    private ResponseEntity<ErrorResponseDto> build(
            HttpStatus status, String error, List<String> messages) {

        ErrorResponseDto body = ErrorResponseDto.builder()
                .status(status.value())
                .error(error)
                .messages(messages)
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(status).body(body);
    }
}