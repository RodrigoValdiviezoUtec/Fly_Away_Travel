package org.tutorial_ide.fly_away.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.tutorial_ide.fly_away.dto.*;
import org.tutorial_ide.fly_away.security.JwtAuthenticationPrincipal;
import org.tutorial_ide.fly_away.service.BookingService;
import org.tutorial_ide.fly_away.service.FlightService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FlightController {

    private final FlightService flightService;
    private final BookingService bookingService;

    /** POST /flights/create — Public */
    @PostMapping("/flights/create")
    public ResponseEntity<FlightResponseDto> createFlight(
            @Valid @RequestBody FlightRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(flightService.createFlight(dto));
    }

    /** GET /flights/search — Protected */
    @GetMapping("/flights/search")
    public ResponseEntity<List<FlightResponseDto>> searchFlights(
            @ModelAttribute FlightSearchRequestDto criteria) {
        return ResponseEntity.ok(flightService.searchFlights(criteria));
    }

    /** GET /flights/{id} — Protected (generic GET by ID) */
    @GetMapping("/flights/{id}")
    public ResponseEntity<FlightResponseDto> getFlightById(@PathVariable Long id) {
        return ResponseEntity.ok(flightService.getById(id));
    }

    /** POST /flights/book — Protected */
    @PostMapping("/flights/book")
    public ResponseEntity<BookingResponseDto> bookFlight(
            @Valid @RequestBody BookingRequestDto dto,
            @AuthenticationPrincipal JwtAuthenticationPrincipal principal) {

        return ResponseEntity.ok(bookingService.book(principal.getUserId(), dto));
    }

    @PostMapping("/flights/create-many")
    public ResponseEntity<Void> createManyFlights(@Valid @RequestBody CreateManyFlightsRequestDto request) {
        for (FlightRequestDto dto : request.getInputs()) {
            flightService.createFlight(dto);
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}