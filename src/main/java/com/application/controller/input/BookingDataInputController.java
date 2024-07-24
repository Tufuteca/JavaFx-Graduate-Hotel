package com.application.controller.input;

import com.application.controller.output.alert.NotificationsController;
import com.application.model.Status;
import com.application.model.booking.Booking;
import com.application.model.booking.BookingType;
import com.application.model.booking.ServiceBooking;
import com.application.model.room.Rooms;
import com.application.model.room.Service;
import com.application.model.users.Users;
import com.application.service.StatusService;
import com.application.service.booking.BookingService;
import com.application.service.booking.BookingTypeService;
import com.application.service.booking.ServiceBookingService;
import com.application.service.room.RoomsService;
import com.application.service.room.ServiceService;
import com.application.service.users.ClientService;
import com.application.service.users.UsersService;
import javafx.collections.ObservableList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;
import java.util.List;

@Controller
public class BookingDataInputController {
    @Autowired
    private BookingService bookingService;
    @Autowired
    private BookingTypeService bookingTypeService;
    @Autowired
    private UsersService usersService;
    @Autowired
    private ClientService clientService;
    @Autowired
    private RoomsService roomsService;
    @Autowired
    private ServiceService serviceService;
    @Autowired
    private ServiceBookingService serviceBookingService;
    @Autowired
    private StatusService statusService;
    @Autowired
    private NotificationsController notificationsController;

    public boolean addBooking(Long clientId,
                              Long selectedBookingRoom,
                              LocalDate checkIn,
                              LocalDate departureDate,
                              String bookingDescription,
                              String bookingTypeComboBox,
                              ObservableList<String> items,
                              String bookingAmount) {
        Booking booking = new Booking();
        BookingType bookingType = bookingTypeService.getBookingTypeByType(bookingTypeComboBox);
        Users user = usersService.findUserById(clientId);
        Rooms rooms = roomsService.getRoomById(selectedBookingRoom);
        List<Booking> existingBookings = bookingService.getBookingsByRoomId(selectedBookingRoom);

        for (Booking existingBooking : existingBookings) {
            // Если даты нового бронирования пересекаются с датами существующего бронирования и статус бронирования не "Отменена", возвращаем false
            if ((checkIn.isAfter(existingBooking.getCheckInDate()) && checkIn.isBefore(existingBooking.getDepartureDate())) ||
                    (departureDate.isAfter(existingBooking.getCheckInDate()) && departureDate.isBefore(existingBooking.getDepartureDate())) ||
                    (checkIn.isEqual(existingBooking.getCheckInDate()) || departureDate.isEqual(existingBooking.getDepartureDate()))) {

                Status status = existingBooking.getStatus();
                if (status != null && !status.getTitle().equals("Отменена")) {
                    notificationsController.chooseAnotherDate();
                    return false;
                }
            }
        }


        booking.setCheckInDate(checkIn);
        booking.setDepartureDate(departureDate);
        booking.setBookingType(bookingType);
        booking.setDescription(bookingDescription);
        booking.setUsers(user);
        booking.setBookingDate(LocalDate.now());
        String[] amount = bookingAmount.split(": ");
        try{
        booking.setAmount(Float.parseFloat(amount[1]));
        booking.setRooms(rooms);
        Status statusForRooms = statusService.getStatusFromTitle("Забронирован");
        rooms.setStatus(statusForRooms);
        roomsService.addRooms(rooms);
        Status statusForBooking = statusService.getStatusFromTitle("Активна");
        booking.setStatus(statusForBooking);
        bookingService.addBooking(booking);

        for(int i = 0; i < items.size(); i++){
            Service service = serviceService.getServiceByTitle(items.get(i));
            ServiceBooking serviceBooking = new ServiceBooking();
            serviceBooking.setBooking(booking);
            serviceBooking.setService(service);
            serviceBookingService.save(serviceBooking);
        }
            return true;
        }catch (NumberFormatException | ArrayIndexOutOfBoundsException e){
            e.printStackTrace();
            notificationsController.pressTheButton();
        }
        return false;
    }

    public boolean editBooking(Long bookingId,
                               Long clientId,
                               Long selectedBookingRoom,
                               LocalDate checkIn,
                               LocalDate departureDate,
                               String bookingDescription,
                               String bookingTypeComboBox,
                               ObservableList<String> items,
                               String bookingAmount) {
        Booking booking = bookingService.getBookingById(bookingId);
        BookingType bookingType = bookingTypeService.getBookingTypeByType(bookingTypeComboBox);
        Users user = usersService.findUserById(clientId);
        Rooms rooms = roomsService.getRoomById(selectedBookingRoom);
        List<Booking> existingBookings = bookingService.getBookingsByRoomId(selectedBookingRoom);

        for (Booking existingBooking : existingBookings) {
            // Если текущее бронирование является тем же бронированием, что и редактируемое бронирование, пропускаем итерацию
            if (existingBooking.getId().equals(booking.getId())) {
                continue;
            }

            // Если даты нового бронирования пересекаются с датами существующего бронирования и статус бронирования не "Отменена", возвращаем false
            if ((checkIn.isAfter(existingBooking.getCheckInDate()) && checkIn.isBefore(existingBooking.getDepartureDate())) ||
                    (departureDate.isAfter(existingBooking.getCheckInDate()) && departureDate.isBefore(existingBooking.getDepartureDate())) ||
                    (checkIn.isEqual(existingBooking.getCheckInDate()) || departureDate.isEqual(existingBooking.getDepartureDate()))) {

                Status status = existingBooking.getStatus();
                if (status != null && !status.getTitle().equals("Отменена")) {
                    notificationsController.chooseAnotherDate();
                    return false;
                }
            }
        }


        booking.setCheckInDate(checkIn);
        booking.setDepartureDate(departureDate);
        booking.setBookingType(bookingType);
        booking.setDescription(bookingDescription);
        booking.setUsers(user);
        booking.setBookingDate(LocalDate.now());
        String[] amount = bookingAmount.split(": ");
        try {
            booking.setAmount(Float.parseFloat(amount[1]));
            booking.setRooms(rooms);
            Status statusForRooms = statusService.getStatusFromTitle("Забронирован");
            rooms.setStatus(statusForRooms);
            roomsService.addRooms(rooms);
            Status statusForBooking = statusService.getStatusFromTitle("Активна");
            booking.setStatus(statusForBooking);
            bookingService.addBooking(booking);

            List<ServiceBooking> existingServices = serviceBookingService.getServiceByBooking(booking);
            for (ServiceBooking serviceBooking : existingServices) {
                serviceBookingService.delete(serviceBooking);
            }
            for(int i = 0; i < items.size(); i++){
                Service service = serviceService.getServiceByTitle(items.get(i));
                ServiceBooking serviceBooking = new ServiceBooking();
                serviceBooking.setBooking(booking);
                serviceBooking.setService(service);
                serviceBookingService.save(serviceBooking);
            }
            return true;
        }catch (NumberFormatException | ArrayIndexOutOfBoundsException e){
            notificationsController.pressTheButton();
            e.printStackTrace();
            return false;
        }

    }

    public void cancelBooking(Long selectedBookingRoom) {
        Booking booking = bookingService.getBookingById(selectedBookingRoom);
        if(booking.getStatus().getTitle().equals("Активна")) {
            Status status = statusService.getStatusFromTitle("Отменена");
            booking.setStatus(status);
            bookingService.addBooking(booking);
            Rooms rooms = booking.getRooms();
            Status status1 = statusService.getStatusFromTitle("Свободен");
            rooms.setStatus(status1);
            roomsService.addRooms(rooms);
        }else{
            notificationsController.errorMessage("Бронь не является активной, статус не изменить");
        }
    }

    public void finishBooking(Long selectedBooking) {
        Booking booking = bookingService.getBookingById(selectedBooking);
        if(booking.getStatus().getTitle().equals("Активна")) {
            Status status = statusService.getStatusFromTitle("Завершена");
            booking.setStatus(status);
            bookingService.addBooking(booking);
            Rooms rooms = booking.getRooms();
            Status status1 = statusService.getStatusFromTitle("Свободен");
            rooms.setStatus(status1);
            roomsService.addRooms(rooms);
            notificationsController.actionSuccessfully();
        }else{
            notificationsController.errorMessage("Бронь не является активной, статус не изменить");
        }
    }
}
