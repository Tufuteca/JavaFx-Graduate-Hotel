package com.application.controller.input;

import com.application.model.booking.BookingType;
import com.application.service.booking.BookingService;
import com.application.service.booking.BookingTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class BookingTypeDataInputController {
    @Autowired
    private BookingTypeService bookingTypeService;


    public void addBookingType(String type,Float discount, String description){
        BookingType bookingType = new BookingType();
        bookingType.setType(type);
        bookingType.setDiscount(discount);
        bookingType.setDescription(description);
        bookingTypeService.addBookingType(bookingType);
    }

    public void changeBookingType(long selectedBookingType, String type, float discount, String description) {
        BookingType bookingType = bookingTypeService.getBookingTypeById(selectedBookingType);
        bookingType.setType(type);
        bookingType.setDiscount(discount);
        bookingType.setDescription(description);
        bookingTypeService.addBookingType(bookingType);
    }
    public void deleteBookingType(long selectedBookingType){
        BookingType bookingType = bookingTypeService.getBookingTypeById(selectedBookingType);
        bookingTypeService.delete(bookingType);
    }
}
