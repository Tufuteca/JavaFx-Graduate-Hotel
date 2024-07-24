package com.application.controller.output.report;

import com.application.model.booking.Booking;
import com.application.service.booking.BookingService;
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
public class ClientReport {

        @Autowired
        private BookingService bookingService;

        public void getClientReportStats(TableView<ClientStats> tableStatClients,
                                         boolean onAllTimeClients,
                                         boolean onYearClients,
                                         boolean onMonthClients,
                                         boolean onWeekClients) {
            Map<String, Float> clientPayments = new HashMap<>();
            Map<String, Integer> clientBookings = new HashMap<>();

            LocalDate now = LocalDate.now();
            LocalDate oneYearAgo = now.minusYears(1);
            LocalDate oneMonthAgo = now.minusMonths(1);
            LocalDate oneWeekAgo = now.minusWeeks(1);

            for (Booking booking : bookingService.getAllBooking()) {
                LocalDate bookingDate = booking.getBookingDate();

                if ((booking.getStatus().getTitle().equals("Заверешена") || booking.getStatus().getTitle().equals("Активна")) &&
                        (onAllTimeClients ||
                                (onYearClients && !bookingDate.isBefore(oneYearAgo)) ||
                                (onMonthClients && !bookingDate.isBefore(oneMonthAgo)) ||
                                (onWeekClients && !bookingDate.isBefore(oneWeekAgo)))) {

                    String clientName = booking.getUsers().getSurname()+" "+booking.getUsers().getName()+" "+booking.getUsers().getPatronymic(); // получаем имя клиента
                    float amount = booking.getAmount();
                    clientPayments.put(clientName, clientPayments.getOrDefault(clientName, 0f) + amount);

                    clientBookings.put(clientName, clientBookings.getOrDefault(clientName, 0) + 1);
                }
            }
            tableStatClients.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            // Создаем ObservableList для хранения данных таблицы
            ObservableList<ClientStats> data = FXCollections.observableArrayList();

            // Заполняем список данными
            for (String clientName : clientPayments.keySet()) {
                data.add(new ClientStats(clientName, clientBookings.get(clientName), clientPayments.get(clientName)));
            }

            // Создаем столбцы таблицы
            TableColumn<ClientStats, String> clientNameColumn = new TableColumn<>("ФИО клиента");
            clientNameColumn.setCellValueFactory(new PropertyValueFactory<>("clientName"));

            TableColumn<ClientStats, Integer> countColumn = new TableColumn<>("Количество броней");
            countColumn.setCellValueFactory(new PropertyValueFactory<>("count"));

            TableColumn<ClientStats, String> paymentColumn = new TableColumn<>("Итого");
            paymentColumn.setCellValueFactory(new PropertyValueFactory<>("payment"));

            // Добавляем столбцы и данные в таблицу
            tableStatClients.getColumns().setAll(clientNameColumn, countColumn, paymentColumn);
            tableStatClients.setItems(data);
        }

        public static class ClientStats {
            private final SimpleStringProperty clientName;
            private final SimpleIntegerProperty count;
            private final SimpleStringProperty payment;

            public ClientStats(String clientName, int count, float payment) {
                this.clientName = new SimpleStringProperty(clientName);
                this.count = new SimpleIntegerProperty(count);
                this.payment = new SimpleStringProperty(String.format("%.2f", payment));
            }

            public String getClientName() { return clientName.get(); }
            public int getCount() { return count.get(); }
            public String getPayment() { return payment.get(); }
        }
    }


