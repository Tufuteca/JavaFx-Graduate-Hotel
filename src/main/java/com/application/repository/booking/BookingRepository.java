package com.application.repository.booking;

import com.application.model.Status;
import com.application.model.booking.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    Booking findBookingById(Long bookingId);
    List<Booking> findBookingsById(Long bookingid);
    List<Booking> findBookingsByStatus(Status status);
    @Query("SELECT b FROM Booking b WHERE :currentDate BETWEEN b.checkInDate AND b.departureDate AND b.status = :status")
    List<Booking> findBookingsByStatusAndCurrentDateBetweenCheckInAndDeparture(@Param("status") Status status, @Param("currentDate") LocalDate currentDate);
}

