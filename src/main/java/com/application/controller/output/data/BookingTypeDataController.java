package com.application.controller.output.data;

import com.application.model.booking.Booking;
import com.application.model.booking.BookingType;
import com.application.service.booking.BookingTypeService;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class BookingTypeDataController {

    @Autowired
    private BookingTypeService bookingTypeService;

    public void getBookingTypes(ComboBox<String> bookingTypeComboBox) {
        bookingTypeComboBox.getItems().clear();
        for(BookingType bookingType: bookingTypeService.getAllBookingTypes()){
            bookingTypeComboBox.getItems().add(bookingType.getType());
        }
    }

    public void getBookingTypesInTable(TableView<BookingType> bookingTypeTable) {
        bookingTypeTable.getColumns().clear();
        List<BookingType> bookingList = bookingTypeService.getAllBookingTypes();
        bookingTypeTable.getItems().clear();

        // Создание столбцов и установка их имен
        TableColumn<BookingType, String> idColumn = new TableColumn<>("Номер");
        TableColumn<BookingType, String> roomType = new TableColumn<>("Тип бронирования");
        TableColumn<BookingType, String> roomNumber = new TableColumn<>("Скидка");

        idColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId()+""));
        roomType.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getType()));
        roomNumber.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDiscount()+""));
        bookingTypeTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Добавление столбцов в таблицу
        bookingTypeTable.getColumns().setAll(idColumn, roomType, roomNumber);
        ObservableList<BookingType> bookingObservableList = FXCollections.observableArrayList();

        for(BookingType bookingType : bookingList){
            bookingObservableList.add(bookingType);
        }

        bookingTypeTable.setItems(bookingObservableList);

    }
}
