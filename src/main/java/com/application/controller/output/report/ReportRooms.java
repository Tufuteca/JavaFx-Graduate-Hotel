package com.application.controller.output.report;

import com.application.model.booking.Booking;
import com.application.service.booking.BookingService;
import com.application.service.room.RoomTypeService;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;


@Controller
public class ReportRooms {

    @Autowired
    private BookingService bookingService;
    @Autowired
    private RoomTypeService roomTypeService;

    public void getBookingReportStats(TableView<BookingStats> tableStatRooms,
                                      boolean onAllTimeRooms,
                                      boolean onYearRooms,
                                      boolean onMonthRooms,
                                      boolean onWeekRooms) {
        Map<String, Float> earnedBookings = new HashMap<>();
        Map<String, Integer> countBookings = new HashMap<>();

        LocalDate now = LocalDate.now();
        LocalDate oneYearAgo = now.minusYears(1);
        LocalDate oneMonthAgo = now.minusMonths(1);
        LocalDate oneWeekAgo = now.minusWeeks(1);

        for (Booking booking : bookingService.getAllBooking()) {
            LocalDate bookingDate = booking.getBookingDate();

            if ((booking.getStatus().getTitle().equals("Заверешена") || booking.getStatus().getTitle().equals("Активна")) &&
                    (onAllTimeRooms ||
                            (onYearRooms && !bookingDate.isBefore(oneYearAgo)) ||
                            (onMonthRooms && !bookingDate.isBefore(oneMonthAgo)) ||
                            (onWeekRooms && !bookingDate.isBefore(oneWeekAgo)))) {

                String roomType = booking.getRooms().getRoomType().getTitle(); // получаем тип номера
                float amount = booking.getAmount();
                earnedBookings.put(roomType, earnedBookings.getOrDefault(roomType, 0f) + amount);

                countBookings.put(roomType, countBookings.getOrDefault(roomType, 0) + 1);
            }
        }
        tableStatRooms.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        // Создаем ObservableList для хранения данных таблицы
        ObservableList<BookingStats> data = FXCollections.observableArrayList();

        // Заполняем список данными
        for (String roomType : earnedBookings.keySet()) {
            data.add(new BookingStats(roomType, countBookings.get(roomType), earnedBookings.get(roomType)));
        }

        // Создаем столбцы таблицы
        TableColumn<BookingStats, String> roomTypeColumn = new TableColumn<>("Тип номера");
        roomTypeColumn.setCellValueFactory(new PropertyValueFactory<>("roomType"));

        TableColumn<BookingStats, Integer> countColumn = new TableColumn<>("Количество броней");
        countColumn.setCellValueFactory(new PropertyValueFactory<>("count"));

        TableColumn<BookingStats, Float> earnedColumn = new TableColumn<>("Заработано");
        earnedColumn.setCellValueFactory(new PropertyValueFactory<>("earned"));

        // Добавляем столбцы и данные в таблицу
        tableStatRooms.getColumns().setAll(roomTypeColumn, countColumn, earnedColumn);
        tableStatRooms.setItems(data);
    }


    public static class BookingStats {
        private final SimpleStringProperty roomType;
        private final SimpleIntegerProperty count;
        private final SimpleStringProperty earned;

        public BookingStats(String roomType, int count, float earned) {
            this.roomType = new SimpleStringProperty(roomType);
            this.count = new SimpleIntegerProperty(count);
            this.earned = new SimpleStringProperty(String.format("%.2f", earned));
        }

        public String getRoomType() { return roomType.get(); }
        public int getCount() { return count.get(); }
        public String getEarned() { return earned.get(); }
    }

}

