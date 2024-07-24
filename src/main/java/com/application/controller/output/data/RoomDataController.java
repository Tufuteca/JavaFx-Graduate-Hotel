package com.application.controller.output.data;

import com.application.model.Status;
import com.application.model.room.RoomType;
import com.application.model.room.Rooms;
import com.application.model.room.Service;
import com.application.service.StatusService;
import com.application.service.room.RoomTypeService;
import com.application.service.room.RoomsService;
import com.application.service.room.ServiceService;
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

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class RoomDataController {
    @Autowired
    private RoomsService roomsService;

    @Autowired
    private RoomTypeService roomTypeService;
    @Autowired
    private ServiceService serviceService;
    @Autowired
    private StatusService statusService;

    public void getRoomDataInTable(TableView<RoomType> roomTableRooms) {
                List<RoomType> roomsList = roomTypeService.getAllRoomTypes();
                roomTableRooms.getItems().clear();

                // Создание столбцов и установка их имен
                TableColumn<RoomType, String> idColumn = new TableColumn<>("Номер");
                TableColumn<RoomType, String> roomType = new TableColumn<>("Тип комнаты");
                TableColumn<RoomType, String> placesColumn = new TableColumn<>("Мест");
                TableColumn<RoomType, String> priceColumn = new TableColumn<>("Цена");
                TableColumn<RoomType, String> areaColumn = new TableColumn<>("Площадь");

                idColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId()+""));
                roomType.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitle()));
                placesColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPlaces()+""));
                priceColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPrice()+""));
                areaColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getArea()+""));
                roomTableRooms.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

                // Добавление столбцов в таблицу
                roomTableRooms.getColumns().setAll(idColumn, roomType, placesColumn, priceColumn, areaColumn);
                ObservableList<RoomType> roomsObservableList = FXCollections.observableArrayList();


                for(RoomType rooms : roomsList){
                        roomsObservableList.add(rooms);
                }

                roomTableRooms.setItems(roomsObservableList);

        }

            public void fillComboboxRooms(ComboBox<String> editRoomTypeBox) {
                List<Rooms> Rooms = roomsService.getAllRooms();
                editRoomTypeBox.getItems().clear();
                for(Rooms roomType : Rooms){
                    editRoomTypeBox.getItems().add(roomType.getRoomNumber()+"");
                }
            }

        public void fillComboboxRoomType(ComboBox<String> editRoomTypeBox) {
            List<RoomType> roomTypes = roomTypeService.getAllRoomTypes();
            editRoomTypeBox.getItems().clear();
            for(RoomType roomType : roomTypes){
                editRoomTypeBox.getItems().add(roomType.getTitle());
            }
        }
        public void fillComboboxService(ComboBox<String> editRoomTypeBox, ComboBox<String> additionalServiceCombobox, Map<String,Long> serviceMap) {
            List<Service> serviceList = serviceService.getAllServices();
            editRoomTypeBox.getItems().clear();
            additionalServiceCombobox.getItems().clear();
            serviceMap.clear();
            for(Service service : serviceList){
                if(!service.getEnabled()) {
                    editRoomTypeBox.getItems().add(service.getTitle());
                    additionalServiceCombobox.getItems().add(service.getTitle()+" - "+service.getPrice()+"руб");
                    serviceMap.put(service.getTitle(),service.getId());
                }
            }
        }
        public void setSelectedService(String title,TextField titleService,TextField priceService){
            Service service = serviceService.getServiceByTitle(title);
            titleService.setText(service.getTitle());
            priceService.setText(service.getPrice()+"");
        }

    public void setSelectedRoomType(String newValue, TextField titleRoomType, ComboBox<String> placesRoomType) {
        Rooms room = roomsService.getRoomsByRoomsNumber(Integer.parseInt(newValue));
        titleRoomType.setText(room.getRoomNumber()+"");
        placesRoomType.setValue(room.getRoomType().getTitle());
    }

    public void getRoomDataInTableBooking(TableView<Rooms> roomTable,ComboBox<String> filterRoomPlaces,ComboBox<String> filterRoomType,TextField searchRoomsBooking) {
        List<Rooms> roomsList = roomsService.getAllRooms();
        roomTable.getItems().clear();

        // Создание столбцов и установка их имен
        TableColumn<Rooms, String> idColumn = new TableColumn<>("Номер");
        TableColumn<Rooms, String> roomType = new TableColumn<>("Тип комнаты");
        TableColumn<Rooms, String> placesColumn = new TableColumn<>("Мест");
        TableColumn<Rooms, String> priceColumn = new TableColumn<>("Цена");
        TableColumn<Rooms, String> areaColumn = new TableColumn<>("Площадь");
        TableColumn<Rooms, String> roomNumber = new TableColumn<>("Номер комнаты");
        TableColumn<Rooms, String> roomStatus = new TableColumn<>("Статус");

        idColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId()+""));
        roomType.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRoomType().getTitle()));
        placesColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRoomType().getPlaces()+""));
        priceColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRoomType().getPrice()+""));
        areaColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRoomType().getArea()+""));
        roomNumber.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRoomNumber()+""));
        roomStatus.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStatus().getTitle()));
        roomTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Добавление столбцов в таблицу
        roomTable.getColumns().setAll(idColumn, roomType, placesColumn, priceColumn, areaColumn, roomNumber,roomStatus);
        ObservableList<Rooms> roomsObservableList = FXCollections.observableArrayList();


        for(Rooms rooms : roomsList){
            roomsObservableList.add(rooms);
        }
        roomTable.setItems(roomsObservableList);

        // Заполнение ComboBox данными о количестве мест в номерах
        Set<String> placesSet = roomsList.stream()
                .map(room -> room.getRoomType().getPlaces()+"")
                .collect(Collectors.toSet());
        filterRoomPlaces.getItems().clear();
        filterRoomPlaces.getItems().add("Мест в номере");
        filterRoomPlaces.getItems().addAll(placesSet);

// Заполнение ComboBox данными о типах комнат
        Set<String> roomTypeSet = roomsList.stream()
                .map(room -> room.getRoomType().getTitle())
                .collect(Collectors.toSet());
        filterRoomType.getItems().clear();
        filterRoomType.getItems().add("Тип номера");
        filterRoomType.getItems().addAll(roomTypeSet);

        // Добавление слушателя изменений для filterRoomPlaces
        filterRoomPlaces.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            if (newValue != null && !newValue.equals("Мест в номере")) {
                int selectedPlaces = Integer.parseInt(newValue);
                ObservableList<Rooms> filteredList = roomsObservableList.stream()
                        .filter(room -> room.getRoomType().getPlaces() == selectedPlaces)
                        .collect(Collectors.toCollection(FXCollections::observableArrayList));
                roomTable.setItems(filteredList);
            } else {
                roomTable.setItems(roomsObservableList);
            }
        });

// Добавление слушателя изменений для filterRoomType
        filterRoomType.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            if (newValue != null && !newValue.equals("Тип номера")) {
                String selectedRoomType = newValue;
                ObservableList<Rooms> filteredList = roomsObservableList.stream()
                        .filter(room -> room.getRoomType().getTitle().equals(selectedRoomType))
                        .collect(Collectors.toCollection(FXCollections::observableArrayList));
                roomTable.setItems(filteredList);
            } else {
                roomTable.setItems(roomsObservableList);
            }
        });

        // Добавление слушателя изменений для searchRoomsBooking
        searchRoomsBooking.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && !newValue.isEmpty()) {
                String searchText = newValue.toLowerCase();
                ObservableList<Rooms> filteredList = roomsObservableList.stream()
                        .filter(room -> room.getRoomType().getTitle().toLowerCase().contains(searchText)
                                || String.valueOf(room.getRoomType().getPlaces()).contains(searchText)
                                || String.valueOf(room.getRoomNumber()).contains(searchText)
                                || room.getStatus().getTitle().toLowerCase().contains(searchText))
                        .collect(Collectors.toCollection(FXCollections::observableArrayList));
                roomTable.setItems(filteredList);
            } else {
                roomTable.setItems(roomsObservableList);
            }
        });




    }
    public String setServiceList(ComboBox<String> additionalServiceCombobox) {
            String[] serviceTitle = additionalServiceCombobox.getValue().split(" - ");
            Service service = serviceService.getServiceByTitle(serviceTitle[0]);
            return service.getTitle();
    }

    public void getRoomReport(Text roomTotal, Text freeRoom, Text occupiedRoom) {
            List<Rooms> roomsList = roomsService.getAllRooms();
            roomTotal.setText("Всего: "+roomsList.size());
            Status status = statusService.getStatusFromTitle("Свободен");
            roomsList = roomsService.getRoomsByStatus(status);
            freeRoom.setText("Свободно :"+roomsList.size());
            status = statusService.getStatusFromTitle("Забронирован");
            roomsList = roomsService.getRoomsByStatus(status);
            freeRoom.setText("Занято :"+roomsList.size());
    }
}
