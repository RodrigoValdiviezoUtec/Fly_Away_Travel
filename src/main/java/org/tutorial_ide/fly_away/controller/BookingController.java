package org.tutorial_ide.fly_away.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.tutorial_ide.fly_away.dto.BookingResponseDto;
import org.tutorial_ide.fly_away.service.BookingService;

@RestController
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    /** GET /bookings/{id} — Protected (standard REST) */
    @GetMapping("/bookings/{id}")
    public ResponseEntity<BookingResponseDto> getBookingById(@PathVariable Long id) {
        return ResponseEntity.ok(bookingService.getById(id));
    }

    @GetMapping("/flights/book/{id}")
    public ResponseEntity<BookingResponseDto> getBooking(@PathVariable Long id) {
        return ResponseEntity.ok(bookingService.getById(id));
    }


}
