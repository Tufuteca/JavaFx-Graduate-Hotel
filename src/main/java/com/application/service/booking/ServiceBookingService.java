package com.application.service.booking;

import com.application.model.booking.Booking;
import com.application.model.booking.ServiceBooking;
import com.application.repository.booking.ServiceBookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceBookingService {
    @Autowired
    private ServiceBookingRepository serviceBookingRepository;

    public List<ServiceBooking> findAll() {
        return serviceBookingRepository.findAll();
    }
    public List<ServiceBooking> findServicesByBooking(Booking booking) {
        return serviceBookingRepository.findServiceBookingByBooking(booking);
    }
    public ServiceBooking save(ServiceBooking serviceBooking) {
        return serviceBookingRepository.save(serviceBooking);
    }

    public List<ServiceBooking> getServiceByBooking(Booking booking) {
        return serviceBookingRepository.findServiceBookingByBooking(booking);
    }

    public void delete(ServiceBooking serviceBooking) {
        serviceBookingRepository.delete(serviceBooking);
    }
}
