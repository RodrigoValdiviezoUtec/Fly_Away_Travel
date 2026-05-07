package org.tutorial_ide.fly_away.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Data
public class FlightSearchRequestDto {
    private String flightNumber;
    private String airlineName;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime estDepartureTimeFrom;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime estDepartureTimeTo;
}