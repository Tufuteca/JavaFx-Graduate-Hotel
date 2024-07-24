package com.application.service.booking;

import com.application.model.Status;
import com.application.model.booking.Booking;
import com.application.model.room.RoomType;
import com.application.repository.booking.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BookingService {
    @Autowired
    private BookingRepository bookingRepository;

    public List<Booking> getAllBooking() {
        return bookingRepository.findAll();
    }
    public Booking getBookingById(Long id) {
        return bookingRepository.findBookingById(id);
    }

    public void addBooking(Booking booking) {
        bookingRepository.save(booking);
    }

    public List<Booking> getBookingsByRoomId(Long selectedBookingRoom) {
        return bookingRepository.findBookingsById(selectedBookingRoom);
    }

    public List<Booking> getBookingsByStatusAndCurrentDate(Status status, LocalDate currentDate) {
        return bookingRepository.findBookingsByStatusAndCurrentDateBetweenCheckInAndDeparture(status, currentDate);
    }
}
