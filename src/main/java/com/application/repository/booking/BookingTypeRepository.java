package com.application.repository.booking;

import com.application.model.booking.BookingType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingTypeRepository extends JpaRepository<BookingType, Long> {
    BookingType findBookingTypeByType(String bookingTypeType);
    BookingType findBookingTypeById(Long id);
}
