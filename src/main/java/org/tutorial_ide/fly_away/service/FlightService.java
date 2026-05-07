package org.tutorial_ide.fly_away.service;

import org.tutorial_ide.fly_away.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tutorial_ide.fly_away.entity.Flight;
import org.tutorial_ide.fly_away.exception.BusinessException;
import org.tutorial_ide.fly_away.exception.ConflictException;
import org.tutorial_ide.fly_away.exception.ResourceNotFoundException;
import org.tutorial_ide.fly_away.repository.FlightRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FlightService {

    private final FlightRepository flightRepository;

    @Transactional
    public FlightResponseDto createFlight(FlightRequestDto dto) {
        // Business rule: departureTime must be strictly before arrivalTime
        if (!dto.getDepartureTime().isBefore(dto.getArrivalTime())) {
            throw new BusinessException(
                    "Departure time must be strictly before arrival time");
        }
        // Business rule: flight number must be unique
        if (flightRepository.existsByFlightNumber(dto.getFlightNumber())) {
            throw new ConflictException(
                    "Flight number '" + dto.getFlightNumber() + "' already exists");
        }

        Flight flight = Flight.builder()
                .flightNumber(dto.getFlightNumber())
                .airline(dto.getAirline())
                .origin(dto.getOrigin())
                .destination(dto.getDestination())
                .departureTime(dto.getDepartureTime())
                .arrivalTime(dto.getArrivalTime())
                .availableSeats(dto.getAvailableSeats())
                .build();

        return toDto(flightRepository.save(flight));
    }

    @Transactional(readOnly = true)
    public List<FlightResponseDto> searchFlights(FlightSearchRequestDto criteria) {
        return flightRepository.search(
                criteria.getFlightNumber(),
                criteria.getAirline(),
                criteria.getDepartureFrom(),
                criteria.getDepartureTo()
        ).stream().map(this::toDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public FlightResponseDto getById(Long id) {
        return toDto(findById(id));
    }

    public Flight findById(Long id) {
        return flightRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Flight not found with id: " + id));
    }

    public FlightResponseDto toDto(Flight flight) {
        return FlightResponseDto.builder()
                .id(flight.getId())
                .flightNumber(flight.getFlightNumber())
                .airline(flight.getAirline())
                .origin(flight.getOrigin())
                .destination(flight.getDestination())
                .departureTime(flight.getDepartureTime())
                .arrivalTime(flight.getArrivalTime())
                .availableSeats(flight.getAvailableSeats())
                .build();
    }
}