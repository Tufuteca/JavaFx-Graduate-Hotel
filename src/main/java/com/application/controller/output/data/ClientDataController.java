package com.application.controller.output.data;

import com.application.model.Status;
import com.application.model.booking.Booking;
import com.application.model.users.Clients;
import com.application.service.StatusService;
import com.application.service.booking.BookingService;
import com.application.service.users.ClientService;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class ClientDataController {

    @Autowired
    ClientService clientService;
    @Autowired
    private StatusService statusService;
    @Autowired
    private BookingService bookingService;

    public void getClientDataInTable(TableView<Clients> clientTable, TextField searchClientField) {
        List<Clients> clientsList = clientService.getClientsData();
        clientTable.getItems().clear();

        // Создание столбцов и установка их имен
        TableColumn<Clients, String> idColumn = new TableColumn<>("Номер");
        TableColumn<Clients, String> surnameColumn = new TableColumn<>("Фамилия");
        TableColumn<Clients, String> nameColumn = new TableColumn<>("Имя");
        TableColumn<Clients, String> patronymicColumn = new TableColumn<>("Отчество");
        TableColumn<Clients, String> dateOfBirthColumn = new TableColumn<>("Дата рождения");
        TableColumn<Clients, String> emailColumn = new TableColumn<>("Почта");
        TableColumn<Clients, String> phoneNumberColumn = new TableColumn<>("Номер телефона");

        idColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId()+""));
        surnameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUser().getSurname()));
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUser().getName()));
        patronymicColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUser().getPatronymic()));
        dateOfBirthColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUser().getDateOfBirth()+""));
        emailColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUser().getEmail()));
        phoneNumberColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUser().getPhoneNumber()));
        clientTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Добавление столбцов в таблицу
        clientTable.getColumns().setAll(idColumn, surnameColumn, nameColumn, patronymicColumn, dateOfBirthColumn, emailColumn, phoneNumberColumn); // Используйте столбец postColumn

        ObservableList<Clients> clientsObservableList = FXCollections.observableArrayList();
        for(Clients clients : clientsList){
            if(!clients.getUser().getDeleted()){
                clientsObservableList.add(clients);
            }
        }
        clientTable.setItems(clientsObservableList);

        // Добавление слушателя изменений для searchClientField
        searchClientField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && !newValue.isEmpty()) {
                String searchText = newValue.toLowerCase();
                ObservableList<Clients> filteredList = clientsObservableList.stream()
                        .filter(client -> client.getUser().getSurname().toLowerCase().contains(searchText)
                                || client.getUser().getName().toLowerCase().contains(searchText)
                                || client.getUser().getPatronymic().toLowerCase().contains(searchText)
                                || (client.getUser().getEmail() != null && client.getUser().getEmail().toLowerCase().contains(searchText))
                                || client.getUser().getPhoneNumber().contains(searchText))
                        .collect(Collectors.toCollection(FXCollections::observableArrayList));
                clientTable.setItems(filteredList);
            } else {
                clientTable.setItems(clientsObservableList);
            }
        });


    }

    public void fillComboboxUser(ComboBox<String> parentsCombobox, ComboBox<String> clientsCombobox, Map<String, Long> clientMap, Map<String, Long> userMap) {
        List<Clients> clientsList = clientService.getClientsData();
        ObservableList<String> parentsItems = FXCollections.observableArrayList();
        ObservableList<String> clientsItems = FXCollections.observableArrayList();
        clientMap.clear();
        userMap.clear();
        for(Clients client : clientsList){
            if(!client.getUser().getDeleted()){
                // Получаем полное имя клиента
                String fullName = client.getUser().getSurname()+" "+client.getUser().getName()+" "+client.getUser().getPatronymic();
                // Добавляем полное имя клиента и его идентификатор в map
                clientMap.put(fullName, client.getId());
                userMap.put(fullName, client.getUser().getId());
                // Добавляем полное имя клиента в комбо-бокс
                parentsItems.add(fullName);
                clientsItems.add(fullName);
            }
        }
        parentsCombobox.setItems(parentsItems);
        clientsCombobox.setItems(clientsItems);
    }

    public void getClientReport(Text clientsNow, Text allTimeClients) {
        Status status = statusService.getStatusFromTitle("Активна");
        List<Booking> bookingList = bookingService.getBookingsByStatusAndCurrentDate(status, LocalDate.now());
        clientsNow.setText("Сейчас: "+bookingList.size());
        List<Clients> clientsList = clientService.getClientsData();
        allTimeClients.setText("За все время: "+clientsList.size());
    }
}
