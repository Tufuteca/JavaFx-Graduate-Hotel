package com.application.controller.output.data;

import com.application.model.Status;
import com.application.model.booking.Booking;
import com.application.model.booking.BookingType;
import com.application.model.room.RoomType;
import com.application.model.room.Rooms;
import com.application.model.room.Service;
import com.application.service.StatusService;
import com.application.service.booking.BookingService;
import com.application.service.booking.BookingTypeService;
import com.application.service.room.RoomsService;
import com.application.service.room.ServiceService;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class BookingDataController {

    @Autowired
    private BookingService bookingService;
    @Autowired
    private BookingTypeService bookingTypeService;
    @Autowired
    private RoomsService roomsService;
    @Autowired
    private ServiceService serviceService;
    @Autowired
    private StatusService statusService;

    public void getBookingDataInTable(TableView<Booking> bookingTable, TextField searchBookingInHotel) {
        List<Booking> bookingList = bookingService.getAllBooking();
        bookingTable.getItems().clear();

        // Создание столбцов и установка их имен
        TableColumn<Booking, String> idColumn = new TableColumn<>("Номер");
        TableColumn<Booking, String> roomType = new TableColumn<>("Тип комнаты");
        TableColumn<Booking, String> roomNumber = new TableColumn<>("Номер комнаты");
        TableColumn<Booking, String> dateBooking = new TableColumn<>("Дата бронирования");
        TableColumn<Booking, String> checkInDate = new TableColumn<>("Дата заезда");
        TableColumn<Booking, String> departureDate = new TableColumn<>("Дата выезда");
        TableColumn<Booking, String> bookingType = new TableColumn<>("Тип бронирования");
        TableColumn<Booking, String> statusBooking = new TableColumn<>("Статус бронирования");

        idColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId()+""));
        roomType.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRooms().getRoomType().getTitle()));
        roomNumber.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRooms().getRoomNumber()+""));
        dateBooking.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getBookingDate()+""));
        checkInDate.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCheckInDate()+""));
        departureDate.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDepartureDate()+""));
        bookingType.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getBookingType().getType()));
        statusBooking.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStatus().getTitle()));
        bookingTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Добавление столбцов в таблицу
        bookingTable.getColumns().setAll(idColumn, roomType, roomNumber, dateBooking, checkInDate, departureDate,bookingType,statusBooking);
        ObservableList<Booking> bookingObservableList = FXCollections.observableArrayList();

        for(Booking booking : bookingList){
            bookingObservableList.add(booking);
        }

        bookingTable.setItems(bookingObservableList);

        // Добавление слушателя изменений для searchBookingInHotel
        searchBookingInHotel.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && !newValue.isEmpty()) {
                String searchText = newValue.toLowerCase();
                ObservableList<Booking> filteredList = bookingObservableList.stream()
                        .filter(booking -> booking.getRooms().getRoomType().getTitle().toLowerCase().contains(searchText)
                                || String.valueOf(booking.getRooms().getRoomNumber()).contains(searchText)
                                || booking.getBookingType().getType().toLowerCase().contains(searchText)
                                || booking.getStatus().getTitle().toLowerCase().contains(searchText))
                        .collect(Collectors.toCollection(FXCollections::observableArrayList));
                bookingTable.setItems(filteredList);
            } else {
                bookingTable.setItems(bookingObservableList);
            }
        });

    }


    public float calculateAmount(LocalDate checkInDateBooking, Long selectedBookingRoom, LocalDate departureDate, String bookingTypeComboBox, ObservableList<String> items) {
        BookingType bookingType = bookingTypeService.getBookingTypeByType(bookingTypeComboBox);
        float amount = 0;
        Long days = ChronoUnit.DAYS.between(checkInDateBooking, departureDate);
        Rooms rooms = roomsService.getRoomById(selectedBookingRoom);
        for(int i = 0; i < items.size(); i++){
            Service service = serviceService.getServiceByTitle(items.get(i));
            amount += service.getPrice();
        }
        amount += rooms.getRoomType().getPrice();
        amount *= days;
        amount = (amount / 100 * bookingType.getDiscount()) + amount;
        return amount;
    }




    public void getBookingReport(Text activeBooking, Text allTimeBooking, ProgressBar occupied,Text todayBooking,Text earnedToday) {
        Status status = statusService.getStatusFromTitle("Активна");
        List<Booking> bookingList = bookingService.getBookingsByStatusAndCurrentDate(status, LocalDate.now());
        activeBooking.setText("Активных: "+bookingList.size());
        List<Booking> bookings = bookingService.getAllBooking();
        allTimeBooking.setText("За все время: "+bookings.size());
        List<Rooms> rooms = roomsService.getAllRooms();

        // Вычисление заполненности
        double occupancy = (double) bookingList.size() / rooms.size();

        // Установка цвета ProgressBar в зависимости от заполненности
        if (occupancy < 0.2) {
            occupied.setStyle("-fx-accent: #0598ff;");
        } else if (occupancy < 0.5) {
            occupied.setStyle("-fx-accent: #43fa43;");
        } else if (occupancy < 0.8) {
            occupied.setStyle("-fx-accent: orange;");
        } else {
            occupied.setStyle("-fx-accent: red;");
        }

        occupied.setProgress(occupancy);
        todayBooking.setText("Броней за сегодня: "+bookingList.size());
        float amount = 0;
        for(Booking booking: bookingList){
            amount+=booking.getAmount();
        }
        earnedToday.setText("Заработано: "+amount);
    }






}
