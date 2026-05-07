package org.tutorial_ide.fly_away.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.tutorial_ide.fly_away.entity.Flight;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface FlightRepository extends JpaRepository<Flight, Long> {

    Optional<Flight> findByFlightNumber(String flightNumber);

    boolean existsByFlightNumber(String flightNumber);

    @Query("""
        SELECT f FROM Flight f
        WHERE (:flightNumber IS NULL OR UPPER(f.flightNumber) LIKE UPPER(CONCAT('%', :flightNumber, '%')))
          AND (:airline IS NULL OR UPPER(f.airline) LIKE UPPER(CONCAT('%', :airline, '%')))
          AND (:from IS NULL OR f.departureTime >= :from)
          AND (:to IS NULL OR f.departureTime <= :to)
        """)
    List<Flight> search(
            @Param("flightNumber") String flightNumber,
            @Param("airline") String airline,
            @Param("from") LocalDateTime from,
            @Param("to") LocalDateTime to
    );
}