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

    /** GET /flight/book/{id} — Protected (alias as specified) */
    @GetMapping("/flight/book/{id}")
    public ResponseEntity<BookingResponseDto> getBookingByIdAlias(@PathVariable Long id) {
        return ResponseEntity.ok(bookingService.getById(id));
    }
}
