package org.tutorial_ide.fly_away.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BookingRequestDto {

    @NotNull(message = "flight_id is required")
    private Long flightId;
}