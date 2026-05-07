package org.tutorial_ide.fly_away.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tutorial_ide.fly_away.dto.BookingRequestDto;
import org.tutorial_ide.fly_away.dto.BookingResponseDto;
import org.tutorial_ide.fly_away.entity.AppUser;
import org.tutorial_ide.fly_away.entity.Booking;
import org.tutorial_ide.fly_away.entity.Flight;
import org.tutorial_ide.fly_away.exception.BusinessException;
import org.tutorial_ide.fly_away.exception.ResourceNotFoundException;
import org.tutorial_ide.fly_away.repository.BookingRepository;

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final FlightService flightService;
    private final UserService userService;

    @Value("${booking.email.directory}")
    private String emailDirectory;

    private static final DateTimeFormatter ISO = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    @Transactional
    public BookingResponseDto book(Long userId, BookingRequestDto dto) {
        Flight flight = flightService.findById(dto.getFlightId());
        AppUser user = userService.findById(userId);

        LocalDateTime now = LocalDateTime.now();

        // Rule: Cannot book past flights or flights currently in transit
        if (!flight.getDepartureTime().isAfter(now)) {
            throw new BusinessException(
                    "Cannot book flight '" + flight.getFlightNumber() +
                            "': departure time has already passed or flight is in transit");
        }

        // Rule: No overselling
        if (flight.getAvailableSeats() <= 0) {
            throw new BusinessException(
                    "No available seats on flight " + flight.getFlightNumber());
        }

        // Rule: No schedule conflicts
        boolean hasConflict = bookingRepository.hasScheduleConflict(
                userId,
                flight.getDepartureTime(),
                flight.getArrivalTime()
        );
        if (hasConflict) {
            throw new BusinessException(
                    "You already have a booking that conflicts with this flight's schedule");
        }

        // Reduce available seats
        flight.setAvailableSeats(flight.getAvailableSeats() - 1);

        Booking booking = Booking.builder()
                .user(user)
                .flight(flight)
                .bookingDate(now)
                .build();

        Booking saved = bookingRepository.save(booking);

        // Generate confirmation file asynchronously (but within same TX for now)
        generateBookingEmailFile(saved, user, flight);

        return toDto(saved);
    }

    @Transactional(readOnly = true)
    public BookingResponseDto getById(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Booking not found with id: " + id));
        return toDto(booking);
    }

    private void generateBookingEmailFile(Booking booking, AppUser user, Flight flight) {
        try {
            Path dir = Paths.get(emailDirectory);
            Files.createDirectories(dir);

            String filename = "flight_booking_email_" + booking.getId() + ".txt";
            Path filePath = dir.resolve(filename);

            String content = buildEmailContent(booking, user, flight);
            Files.writeString(filePath, content, StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING);

            log.info("Booking confirmation file created: {}", filePath.toAbsolutePath());
        } catch (IOException e) {
            // Non-fatal: log the error but don't roll back the booking
            log.error("Failed to create booking email file for booking id {}: {}",
                    booking.getId(), e.getMessage());
        }
    }

    private String buildEmailContent(Booking booking, AppUser user, Flight flight) {
        return """
                =============================================
                   FLY AWAY TRAVEL — BOOKING CONFIRMATION
                =============================================
                Booking ID      : %d
                Booking Date    : %s

                --- PASSENGER INFORMATION ---
                Full Name       : %s %s

                --- FLIGHT DETAILS ---
                Flight Number   : %s
                Airline         : %s
                From            : %s
                To              : %s
                Departure       : %s
                Arrival         : %s
                =============================================
                """.formatted(
                booking.getId(),
                booking.getBookingDate().format(ISO),
                user.getFirstName(),
                user.getLastName(),
                flight.getFlightNumber(),
                flight.getAirline(),
                flight.getOrigin(),
                flight.getDestination(),
                flight.getDepartureTime().format(ISO),
                flight.getArrivalTime().format(ISO)
        );
    }

    public BookingResponseDto toDto(Booking booking) {
        AppUser u = booking.getUser();
        Flight f = booking.getFlight();
        return BookingResponseDto.builder()
                .id(booking.getId())
                .userId(u.getId())
                .userFullName(u.getFirstName() + " " + u.getLastName())
                .flightId(f.getId())
                .flightNumber(f.getFlightNumber())
                .airline(f.getAirline())
                .origin(f.getOrigin())
                .destination(f.getDestination())
                .departureTime(f.getDepartureTime())
                .arrivalTime(f.getArrivalTime())
                .bookingDate(booking.getBookingDate())
                .build();
    }
}
