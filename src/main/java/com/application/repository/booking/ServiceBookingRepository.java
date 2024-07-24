package com.application.repository.booking;

import com.application.model.booking.Booking;
import com.application.model.booking.ServiceBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceBookingRepository extends JpaRepository<ServiceBooking, Long> {
    List<ServiceBooking> findServiceBookingByBooking(Booking booking);
}
