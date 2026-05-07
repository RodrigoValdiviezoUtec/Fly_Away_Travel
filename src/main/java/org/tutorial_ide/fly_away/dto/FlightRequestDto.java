package org.tutorial_ide.fly_away.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class FlightRequestDto {

    @NotBlank(message = "Flight number is required")
    @Pattern(regexp = "^[A-Z0-9]{1,6}$",
            message = "Flight number must be alphanumeric (A-Z, 0-9), max 6 characters")
    private String flightNumber;

    @NotBlank(message = "Airline is required")
    private String airline;

    @NotBlank(message = "Origin is required")
    private String origin;

    @NotBlank(message = "Destination is required")
    private String destination;

    @NotNull(message = "Departure time is required")
    private LocalDateTime departureTime;

    @NotNull(message = "Arrival time is required")
    private LocalDateTime arrivalTime;

    @NotNull(message = "Available seats is required")
    @Min(value = 1, message = "Available seats must be greater than 0")
    private Integer availableSeats;
}