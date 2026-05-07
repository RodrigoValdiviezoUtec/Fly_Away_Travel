package org.tutorial_ide.fly_away.dto;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data @Builder
public class BookingResponseDto {
    private Long id;
    private Long userId;
    private String userFullName;
    private Long flightId;
    private String flightNumber;
    private String airline;
    private String origin;
    private String destination;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private LocalDateTime bookingDate;
}