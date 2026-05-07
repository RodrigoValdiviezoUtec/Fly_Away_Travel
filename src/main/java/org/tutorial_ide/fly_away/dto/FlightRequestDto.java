package org.tutorial_ide.fly_away.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class FlightRequestDto {

    @NotBlank(message = "Flight number is required")
    @Pattern(regexp = "^[A-Z]{2,3}[0-9]{3}$",
            message = "Flight number must be 2-3 letters followed by 3 numbers")
    private String flightNumber;

    @JsonProperty("airlineName")
    @NotBlank(message = "Airline is required")
    private String airline;

    private String origin;

    private String destination;

    @JsonProperty("estDepartureTime")
    @NotNull(message = "Departure time is required")
    private LocalDateTime departureTime;

    @JsonProperty("estArrivalTime")
    @NotNull(message = "Arrival time is required")
    private LocalDateTime arrivalTime;

    @NotNull(message = "Available seats is required")
    @Min(value = 1, message = "Available seats must be greater than 0")
    private Integer availableSeats;
}