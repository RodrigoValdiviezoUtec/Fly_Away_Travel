package org.tutorial_ide.fly_away.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tutorial_ide.fly_away.repository.BookingRepository;
import org.tutorial_ide.fly_away.repository.FlightRepository;
import org.tutorial_ide.fly_away.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class CleanupService {

    private final BookingRepository bookingRepository;
    private final FlightRepository flightRepository;
    private final UserRepository userRepository;

    @Transactional
    public void deleteAll() {
        // Order matters due to FK constraints
        bookingRepository.deleteAll();
        flightRepository.deleteAll();
        userRepository.deleteAll();
    }
}