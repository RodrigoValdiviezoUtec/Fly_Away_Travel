package org.tutorial_ide.fly_away.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.tutorial_ide.fly_away.entity.Booking;

import java.time.LocalDateTime;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findByUserId(Long userId);

    @Query("""
        SELECT COUNT(b) > 0 FROM Booking b
        JOIN b.flight f
        WHERE b.user.id = :userId
          AND f.departureTime < :arrivalTime
          AND f.arrivalTime > :departureTime
        """)
    boolean hasScheduleConflict(
            @Param("userId") Long userId,
            @Param("departureTime") LocalDateTime departureTime,
            @Param("arrivalTime") LocalDateTime arrivalTime
    );
}
