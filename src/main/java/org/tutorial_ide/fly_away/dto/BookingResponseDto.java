package org.tutorial_ide.fly_away.dto;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

@Data @Builder
public class BookingResponseDto {
    private Long id;

    @JsonProperty("customerId")
    private Long userId;

    private String customerFirstName;
    private String customerLastName;

    private Long flightId;
    private String flightNumber;
    private String airline;
    private String origin;
    private String destination;

    @JsonProperty("estDepartureTime")
    private LocalDateTime departureTime;

    @JsonProperty("estArrivalTime")
    private LocalDateTime arrivalTime;

    private LocalDateTime bookingDate;
}