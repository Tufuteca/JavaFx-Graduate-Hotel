package com.application.service.booking;

import com.application.model.booking.BookingType;
import com.application.repository.booking.BookingTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingTypeService {
    @Autowired
    private BookingTypeRepository bookingTypeRepository;

    public BookingType getBookingTypeByType(String bookingType) {
        return bookingTypeRepository.findBookingTypeByType(bookingType);
    }

    public List<BookingType> getAllBookingTypes() {
        return bookingTypeRepository.findAll();
    }

    public void addBookingType(BookingType bookingType) {
        bookingTypeRepository.save(bookingType);
    }

    public BookingType getBookingTypeById(long selectedBookingType) {
        return bookingTypeRepository.findBookingTypeById(selectedBookingType);
    }

    public void delete(BookingType bookingType) {
        bookingTypeRepository.delete(bookingType);
    }
}
