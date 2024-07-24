package com.application.controller.output.ui;

import com.application.Main;
import com.application.controller.input.*;
import com.application.controller.output.alert.NotificationsController;
import com.application.controller.output.data.*;
import com.application.controller.output.alert.ExitConfirmationController;
import com.application.model.booking.Booking;
import com.application.model.booking.ServiceBooking;
import com.application.model.room.RoomType;
import com.application.model.room.ServiceRoomType;
import com.application.model.room.Rooms;
import com.application.model.users.Childrens;
import com.application.model.users.ClientChildren;
import com.application.model.users.Clients;
import com.application.model.users.Users;
import com.application.repository.room.ServiceRoomTypeRepository;
import com.application.service.booking.BookingService;
import com.application.service.booking.ServiceBookingService;
import com.application.service.room.RoomTypeService;
import com.application.service.room.RoomsService;
import com.application.service.room.ServiceRoomTypeService;
import com.application.service.users.ChildrensService;
import com.application.service.users.ClientChildrenService;
import com.application.service.users.ClientService;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class AdministratorView {

    // <editor-fold defaultstate="collapsed" desc="Объявление элементов на форме">
    @FXML
    private Text bookingAmount;
    @FXML
    private CheckBox dressingCheckBox;
    @FXML
    private Text username;
    @FXML
    private Pane ClientsPane;

    @FXML
    private ListView<String> ListOfParents;

    @FXML
    private ProgressBar Occupied;


    @FXML
    private Text activeBooking;

    @FXML
    private Text allTimeBooking;

    @FXML
    private Text allTimeClients;

    @FXML
    private Text parentsText;

    @FXML
    private TextField areaField;

    @FXML
    private DatePicker birthDateClientField;

    @FXML
    private TextArea bookingDescription;

    @FXML
    private Pane bookingPane;

    @FXML
    private TableView<Booking> bookingTable;

    @FXML
    private DatePicker checkInDateBooking;

    @FXML
    private TableView<Clients> clientTable;

    @FXML
    private ComboBox<String> clientsCombobox;
    @FXML
    private ComboBox<String> filterRoomType;

    @FXML
    private ComboBox<String> filterRoomPlaces;

    @FXML
    private ComboBox<String> editRoomTypeBox;
    @FXML
    private ComboBox<String> serviceRoomBox;
    @FXML
    private ComboBox<String> roomTypeBox;

    @FXML
    private Text clientsNow;

    @FXML
    private DatePicker dateIssueField;

    @FXML
    private DatePicker departureDate;

    @FXML
    private TextArea descriptionRoomField;

    @FXML
    private Text earnedToday;

    @FXML
    private Text firstParent;

    @FXML
    private Text freeRoom;

    @FXML
    private TextField issuedPasportField;
    @FXML
    private TextField numberOfRoom;

    @FXML
    private DatePicker kidDateBirth;

    @FXML
    private TextField kidName;

    @FXML
    private TextField kidPatronymic;

    @FXML
    private TextField kidSurname;

    @FXML
    private Pane kidsPane;

    @FXML
    private TableView<Childrens> kidsTable;

    @FXML
    private Pane mainPane;

    @FXML
    private TextField nameClientField;

    @FXML
    private TextField numberPassportField;

    @FXML
    private Text occupiedRoom;

    @FXML
    private ComboBox<String> parentsCombobox;

    @FXML
    private TextField patronymicClientField;

    @FXML
    private TextField phoneNumberClients;

    @FXML
    private TextField placeOfResidenceField;

    @FXML
    private Text priceBooking;

    @FXML
    private TextField priceField;

    @FXML
    private Pane roomPane;

    @FXML
    private TableView<Rooms> roomTable;

    @FXML
    private TableView<RoomType> roomTableRooms;

    @FXML
    private Text roomTotal;

    @FXML
    private TextField searchBookingInHotel;

    @FXML
    private TextField searchRoomsBooking;

    @FXML
    private Text secondParent;

    @FXML
    private TextField serialPassportField;

    @FXML
    private TextField surnameClientField;
    @FXML
    private TextField searchClientField;

    @FXML
    private Text todayBooking;

    @FXML
    private CheckBox additionalBathRoomCheckBox;

    @FXML
    private CheckBox bathCheckBox;

    @FXML
    private CheckBox conditionerCheckBox;

    @FXML
    private CheckBox hallCheckBox;

    @FXML
    private CheckBox minibarCheckBox;

    @FXML
    private CheckBox phoneCheckBox;

    @FXML
    private CheckBox showerCheckBox;

    @FXML
    private CheckBox teapotCheckBox;

    @FXML
    private CheckBox tvCheckBox;

    @FXML
    private CheckBox vaultCheckBox;

    @FXML
    private CheckBox viewOnCityCheckBox;

    @FXML
    private CheckBox viewOnCourtyardCheckBox;

    @FXML
    private CheckBox wifiCheckBox;

    @FXML
    private ComboBox<String> additionalServiceCombobox;

    @FXML
    private TextField titleRoomType;

    @FXML
    private TextField placesRoomType;

    @FXML
    private TextField titleService;

    @FXML
    private TextField priceService;
    @FXML
    private ComboBox<String> selectedAdditionalServiceCombobox;

    @FXML
    private ComboBox<String> bookingTypeComboBox;
    @Autowired
    private BookingTypeDataController bookingTypeDataController;
    @Autowired
    private RoomTypeService roomTypeService;
    //</editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Обработчики кнопок и кликов">
    @FXML
    void exit(MouseEvent event) {
        exitConfirmationController.confirmExitFromAccount(AuthorizationView.getStage());
    }
    @FXML
    void addClient(ActionEvent event) {
        if (isClientDataValid()) {
            clientDataInputController.addUser(
                    surnameClientField.getText(),
                    nameClientField.getText(),
                    patronymicClientField.getText(),
                    phoneNumberClients.getText(),
                    serialPassportField.getText(),
                    numberPassportField.getText(),
                    issuedPasportField.getText(),
                    birthDateClientField.getValue(),
                    dateIssueField.getValue(),
                    placeOfResidenceField.getText()
            );
            initialize();
            selectedClient = 0;
            clearClientFields();
            notificationsController.actionSuccessfully();
        } else {
            notificationsController.validateClientData();
        }
    }

    private boolean isClientDataValid() {
        return !surnameClientField.getText().isEmpty()
                && !nameClientField.getText().isEmpty()
                && !phoneNumberClients.getText().isEmpty()
                && !serialPassportField.getText().isEmpty()
                && !numberPassportField.getText().isEmpty()
                && !issuedPasportField.getText().isEmpty()
                && birthDateClientField.getValue() != null
                && dateIssueField.getValue() != null;
    }

    @FXML
    void addKid(ActionEvent event) {
        childrenDataInputController.addChildren(kidSurname.getText(),kidName.getText(),kidPatronymic.getText(),kidDateBirth.getValue());
        clearChildrenFields();
        initialize();
        notificationsController.actionSuccessfully();
    }

    @FXML
    void addParentToChild(ActionEvent event) {
        if(selectedChildren!=0){
            if(parentsCombobox.getSelectionModel().getSelectedIndex()!=-1) {
                String selectedFullName = parentsCombobox.getValue();
                Long selectedClientId = clientMap.get(selectedFullName);

                childrenDataInputController.addChildrenToClient(selectedClientId, selectedChildren);
                initialize();
                selectedChildren = 0;
                notificationsController.actionSuccessfully();
            }else{
                notificationsController.selectInCombobox();
            }
        }else{
            notificationsController.selectColumnInTable();
        }
    }

    @FXML
    void addRoom(ActionEvent event) {
        roomDataInputController.addRoom(numberOfRoom.getText(),roomTypeBox.getValue());
        initialize();
        clearRoomFields();
        notificationsController.actionSuccessfully();
    }

    @FXML
    void addRoomType(ActionEvent event) {
        roomTypeDataInputController.addRoomType(
                titleRoomType.getText(),
                Integer.parseInt(placesRoomType.getText()),
                descriptionRoomField.getText(),
                areaField.getText(),
                priceField.getText(),
                tvCheckBox.isSelected(),
                wifiCheckBox.isSelected(),
                minibarCheckBox.isSelected(),
                phoneCheckBox.isSelected(),
                teapotCheckBox.isSelected(),
                vaultCheckBox.isSelected(),
                showerCheckBox.isSelected(),
                bathCheckBox.isSelected(),
                conditionerCheckBox.isSelected(),
                dressingCheckBox.isSelected(),
                viewOnCityCheckBox.isSelected(),
                viewOnCourtyardCheckBox.isSelected(),
                hallCheckBox.isSelected(),
                additionalBathRoomCheckBox.isSelected());
        initialize();
        clearRoomTypeFields();
        notificationsController.actionSuccessfully();
    }

    @FXML
    void changeRoomType(ActionEvent event) {
        if(selectedRoom!=0){
            roomTypeDataInputController.changeRoomType(selectedRoom,titleRoomType.getText(),
                    Integer.parseInt(placesRoomType.getText()),
                    descriptionRoomField.getText(),
                    areaField.getText(),
                    priceField.getText(),
                    tvCheckBox.isSelected(),
                    wifiCheckBox.isSelected(),
                    minibarCheckBox.isSelected(),
                    phoneCheckBox.isSelected(),
                    teapotCheckBox.isSelected(),
                    vaultCheckBox.isSelected(),
                    showerCheckBox.isSelected(),
                    bathCheckBox.isSelected(),
                    conditionerCheckBox.isSelected(),
                    dressingCheckBox.isSelected(),
                    viewOnCityCheckBox.isSelected(),
                    viewOnCourtyardCheckBox.isSelected(),
                    hallCheckBox.isSelected(),
                    additionalBathRoomCheckBox.isSelected());
            initialize();
            clearRoomTypeFields();
            notificationsController.actionSuccessfully();
        }else{
            notificationsController.selectColumnInTable();
        }
    }

    @FXML
    void deleteRoomType(ActionEvent event) {
        if(selectedRoom!=0){
            if(notificationsController.confirmExitFromApplication()) {
                roomTypeDataInputController.deleteRoomType(selectedRoom);
                initialize();
                clearRoomTypeFields();
            }
        }else{
            notificationsController.selectColumnInTable();
        }
    }


    @FXML
    void addService(ActionEvent event) {
        serviceDataInputController.addService(titleService.getText(),Float.parseFloat(priceService.getText()));
        initialize();
        clearServiceFields();
        notificationsController.actionSuccessfully();
    }
    @FXML
    void changeService(ActionEvent event) {
        if(serviceRoomBox.getSelectionModel().getSelectedIndex()!=-1){
            serviceDataInputController.changeService(serviceRoomBox.getValue(),Float.parseFloat(priceService.getText()));
            initialize();
            clearServiceFields();
            notificationsController.actionSuccessfully();
        }else{
            notificationsController.selectInCombobox();
        }

    }
    @FXML
    void deleteService(ActionEvent event) {
        if(serviceRoomBox.getSelectionModel().getSelectedIndex()!=-1){
            if(notificationsController.confirmExitFromApplication()) {
                serviceDataInputController.deleteService(serviceRoomBox.getValue());
                initialize();
                clearServiceFields();
            }
        }else{
            notificationsController.selectInCombobox();
        }
    }

    @FXML
    void bookRoom(ActionEvent event) {
        if(selectedBookingRoom!=0 &&
                !bookingTypeComboBox.getValue().equals("") &&
                departureDate.getValue()!=null &&
                checkInDateBooking.getValue()!=null &&
                !clientsCombobox.getValue().equals("")){

            boolean check = bookingDataInputController.addBooking(userMap.get(clientsCombobox.getValue()),
                    selectedBookingRoom,
                    checkInDateBooking.getValue(),
                    departureDate.getValue(),
                    bookingDescription.getText(),
                    bookingTypeComboBox.getValue(),
                    selectedAdditionalServiceCombobox.getItems(),bookingAmount.getText());
            if(check){
                clearBookingFields();
                initialize();
                notificationsController.actionSuccessfully();
            }
        }else{
            notificationsController.checkData();
        }
    }
    @FXML
    void calculateAmount(ActionEvent event){
        if(selectedBookingRoom!=0 &&
                !bookingTypeComboBox.getValue().equals("") &&
                departureDate.getValue()!=null &&
                checkInDateBooking.getValue()!=null &&
                !clientsCombobox.getValue().equals("")){
            bookingAmount.setText("Сумма бронирования: "+bookingDataController.calculateAmount(checkInDateBooking.getValue(),
                    selectedBookingRoom,
                    departureDate.getValue(),
                    bookingTypeComboBox.getValue(),
                    selectedAdditionalServiceCombobox.getItems()));
        }else{
            notificationsController.checkData();
        }
    }

    @FXML
    void buttonSearchMouseClicked(KeyEvent event) {

    }

    @FXML
    void buttonSearchMouseClickedSecond(KeyEvent event) {

    }

    @FXML
    void cancelBooking(ActionEvent event) {
        if(selectedBooking!=0) {
            if (notificationsController.confirmExitFromApplication()){
                bookingDataInputController.cancelBooking(selectedBooking);
            clearBookingFields();
            initialize();
        }
        }else{
            notificationsController.checkData();
        }
    }
    @FXML
    void finishedBooking(ActionEvent event){
        if(selectedBooking!=0){
            if(notificationsController.confirmExitFromApplication()) {
                bookingDataInputController.finishBooking(selectedBooking);
                clearBookingFields();
                initialize();
            }
        }else{
            notificationsController.checkData();
        }
    }

    @FXML
    void changeBookingRoom(ActionEvent event) {
        if(selectedBookingRoom!=0 &&
                !bookingTypeComboBox.getValue().equals("") &&
                departureDate.getValue()!=null &&
                checkInDateBooking.getValue()!=null &&
                !clientsCombobox.getValue().equals("") &&
        selectedBooking!=0){
            if(bookingDataInputController.editBooking(selectedBooking,
                    userMap.get(clientsCombobox.getValue()),
                    selectedBookingRoom,
                    checkInDateBooking.getValue(),
                    departureDate.getValue(),
                    bookingDescription.getText(),
                    bookingTypeComboBox.getValue(),
                    selectedAdditionalServiceCombobox.getItems(),bookingAmount.getText())){
            clearBookingFields();
            initialize();
            notificationsController.actionSuccessfully();
            }
        }else{
            notificationsController.checkData();
        }
    }
    @FXML
    void changeClient(ActionEvent event) {
        if(isClientDataValid()) {
            if (selectedClient != 0) {
                clientDataInputController.editUser(selectedClient, surnameClientField.getText(),
                        nameClientField.getText(),
                        patronymicClientField.getText(),
                        phoneNumberClients.getText(),
                        serialPassportField.getText(),
                        numberPassportField.getText(),
                        issuedPasportField.getText(),
                        birthDateClientField.getValue(),
                        dateIssueField.getValue(),
                        placeOfResidenceField.getText());
                initialize();
                selectedClient = 0;
                clearClientFields();
                notificationsController.actionSuccessfully();
            } else {
                notificationsController.selectColumnInTable();
            }
        }
    }

    @FXML
    void changeKid(ActionEvent event) {
        if(selectedChildren !=0) {
            childrenDataInputController.editChild(selectedChildren,kidSurname.getText(), kidName.getText(), kidPatronymic.getText(), kidDateBirth.getValue());
            clearChildrenFields();
            selectedChildren = 0;
            initialize();
            notificationsController.actionSuccessfully();
        }else{
            notificationsController.selectColumnInTable();
        }
    }

    @FXML
    void changeRoom(ActionEvent event) {
        try {
            String roomNumberText = numberOfRoom.getText();
            if (!roomNumberText.isEmpty()) {
                int roomNumber = Integer.parseInt(roomNumberText);
                if (roomNumber != 0) {
                    roomDataInputController.changeRoom(roomNumber, roomTypeBox.getValue());
                    initialize();
                    clearRoomFields();
                    notificationsController.actionSuccessfully();
                } else {
                    notificationsController.selectInCombobox();
                }
            } else {
                notificationsController.inputRoomNumber();
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void chooseDate(ActionEvent event) {

    }

    @FXML
    void deleteClient(ActionEvent event) {
        if (selectedClient!=0) {
            if(notificationsController.confirmExitFromApplication()) {
                clientDataInputController.setDeleteUser(selectedClient);
                initialize();
                selectedClient = 0;
                clearClientFields();
            }
        }else{
            notificationsController.selectColumnInTable();
        }
    }

    @FXML
    void deleteKid(ActionEvent event) {
        if(selectedChildren !=0) {
            if(notificationsController.confirmExitFromApplication()) {
                childrenDataInputController.setDeleteChild(selectedChildren);
                clearChildrenFields();
                selectedChildren = 0;
                initialize();
            }
        }else{
            notificationsController.selectColumnInTable();
        }
    }

    @FXML
    void deleteParent(ActionEvent event) {

        if(ListOfParents.getSelectionModel().getSelectedIndex()!=-1 && selectedChildren!=0){
            if(notificationsController.confirmExitFromApplication()){
            String selectedFullName = String.valueOf(ListOfParents.getSelectionModel().getSelectedItems());
            selectedFullName = selectedFullName.substring(1,selectedFullName.length()-1);
            System.out.println(selectedFullName);
            Long selectedClientId = clientMap.get(selectedFullName);
                System.out.println(clientMap.get(selectedFullName));
            childrenDataController.deleteParent(selectedClientId, selectedChildren);
            initialize();
            clearChildrenFields();
            }
        }else{
            initialize();
            clearChildrenFields();
            notificationsController.selectColumnInTable();
        }
    }

    @FXML
    void deleteRoom(ActionEvent event) {
        try {
            String roomNumberText = numberOfRoom.getText();
            if (!roomNumberText.isEmpty()) {
                int roomNumber = Integer.parseInt(roomNumberText);
                if (roomNumber != 0) {
                    if(notificationsController.confirmExitFromApplication()){
                    roomDataInputController.deleteRoom(roomNumber);
                    initialize();
                    clearRoomFields();
                    }
                } else {
                    notificationsController.selectInCombobox();
                }
            } else {
                notificationsController.inputRoomNumber();
            }
        } catch (NumberFormatException e) {
            e.printStackTrace(); // Лучше выводить сообщение об ошибке
        }
    }

    @FXML
    void getSelectedChild(MouseEvent event) {
        SelectionModel<Childrens> selectionModel = kidsTable.getSelectionModel();
        // Получаем выбранную строку
        Childrens selectedItem = selectionModel.getSelectedItem();
        long id = 0;
        if (selectedItem != null) {
            id = selectedItem.getId();
            selectedChildren = id;
        }
        if(id!=0){
            Childrens child = childrensService.getChildrenById(id);
            child.setId(id);
            kidSurname.setText(child.getSurname());
            kidName.setText(child.getName());
            kidPatronymic.setText(child.getPatronymic());
            kidDateBirth.setValue(child.getBirthDate());
            parentsText.setVisible(true);
            ListOfParents.getItems().clear();
            List<ClientChildren> clientChildrenList = clientChildrenService.getClientChildrenByChildId(child);
            for(ClientChildren clientChildren : clientChildrenList){
                ListOfParents.getItems().add(clientChildren.getClient().getUser().getSurname()+" "
                        +clientChildren.getClient().getUser().getName()+" "
                        +clientChildren.getClient().getUser().getPatronymic());
            }
        }
    }
    @FXML
    void getSelectedClient(MouseEvent event){
        SelectionModel<Clients> selectionModel = clientTable.getSelectionModel();

        // Получаем выбранную строку
        Clients selectedItem = selectionModel.getSelectedItem();
        long id = 0;
        if (selectedItem != null) {
            id = selectedItem.getUser().getId();
            selectedClient = id;
        }
        if(id!=0){
            Users user = new Users();
            user.setId(id);
            var clients = clientService.getClientByUser(user);
            surnameClientField.setText(clients.getUser().getSurname());
            nameClientField.setText(clients.getUser().getName());
            patronymicClientField.setText(clients.getUser().getPatronymic());
            phoneNumberClients.setText(clients.getUser().getPhoneNumber());
            serialPassportField.setText(clients.getPassportSeries()+"");
            numberPassportField.setText(clients.getPassportNumber()+"");
            issuedPasportField.setText(clients.getIssuedPasport());
            try {
                birthDateClientField.setValue(clients.getUser().getDateOfBirth() );
                dateIssueField.setValue(clients.getDateIssue());
            }catch (Exception e){
                e.printStackTrace();
            }
            placeOfResidenceField.setText(clients.getRegistration());
        }
    }
    private long selectedRoom = 0;

    @FXML
    void getRoomData(MouseEvent event){
        SelectionModel<RoomType> selectionModel = roomTableRooms.getSelectionModel();

        // Получаем выбранную строку
        RoomType selectedItem = selectionModel.getSelectedItem();
        long id = 0;
        if (selectedItem != null) {
            id = selectedItem.getId();
            selectedRoom = id;
        }
        if (id != 0) {
            RoomType roomType = roomTypeService.findRoomTypeById(id);
            priceField.setText(roomType.getPrice() + "");
            areaField.setText(roomType.getArea() + "");
            descriptionRoomField.setText(roomType.getDescription());
            placesRoomType.setText(roomType.getPlaces() + "");
            titleRoomType.setText(roomType.getTitle());

            // Сбрасываем все чекбоксы
            resetAllCheckBoxes();

            List<ServiceRoomType> serviceRooms = serviceRoomService.getRoomServicesByRoom(roomType);
            for (ServiceRoomType serviceRoom : serviceRooms) {
                String serviceTitle = serviceRoom.getService().getTitle();
                switch (serviceTitle) {
                    case "Телевизор":
                        tvCheckBox.setSelected(true);
                        break;
                    case "WI-FI":
                        wifiCheckBox.setSelected(true);
                        break;
                    case "Минибар":
                        minibarCheckBox.setSelected(true);
                        break;
                    case "Телефон":
                        phoneCheckBox.setSelected(true);
                        break;
                    case "Чайник":
                        teapotCheckBox.setSelected(true);
                        break;
                    case "Душевая кабина":
                        showerCheckBox.setSelected(true);
                        break;
                    case "Ванная":
                        bathCheckBox.setSelected(true);
                        break;
                    case "Кондиционер":
                        conditionerCheckBox.setSelected(true);
                        break;
                    case "Гардеробная":
                        dressingCheckBox.setSelected(true);
                        break;
                    case "Сейф":
                        vaultCheckBox.setSelected(true);
                        break;
                    case "Вид на город":
                        viewOnCityCheckBox.setSelected(true);
                        break;
                    case "Вид на двор":
                        viewOnCourtyardCheckBox.setSelected(true);
                        break;
                    case "Гостинная":
                        hallCheckBox.setSelected(true);
                        break;
                    case "Дополнительный санузел":
                        additionalBathRoomCheckBox.setSelected(true);
                        break;
                }
            }
        }
    }

    private void resetAllCheckBoxes() {
        tvCheckBox.setSelected(false);
        wifiCheckBox.setSelected(false);
        minibarCheckBox.setSelected(false);
        phoneCheckBox.setSelected(false);
        teapotCheckBox.setSelected(false);
        showerCheckBox.setSelected(false);
        bathCheckBox.setSelected(false);
        conditionerCheckBox.setSelected(false);
        dressingCheckBox.setSelected(false);
        vaultCheckBox.setSelected(false);
        viewOnCityCheckBox.setSelected(false);
        viewOnCourtyardCheckBox.setSelected(false);
        hallCheckBox.setSelected(false);
        additionalBathRoomCheckBox.setSelected(false);
    }

    @FXML
    void getSelectedIdRoom(MouseEvent event){
        SelectionModel<Rooms> selectionModel = roomTable.getSelectionModel();

        // Получаем выбранную строку
        Rooms selectedItem = selectionModel.getSelectedItem();
        long id = 0;
        if (selectedItem != null) {
            id = selectedItem.getId();
            selectedClient = id;
        }
        if(id!=0){
            var clients = roomsService.getRoomById(id);
            selectedBookingRoom = clients.getId();
        }
    }
    @FXML
    void getSelectedBooking(MouseEvent event){
        SelectionModel<Booking> selectionModel = bookingTable.getSelectionModel();
        selectedAdditionalServiceCombobox.getItems().clear();
        // Получаем выбранную строку
        Booking selectedItem = selectionModel.getSelectedItem();
        long id = 0;
        if (selectedItem != null) {
            id = selectedItem.getId();
            selectedBooking = id;
        }
        if(id!=0){
            var booking = bookingService.getBookingById(id);
            clientsCombobox.setValue(booking.getUsers().getSurname()+" "+booking.getUsers().getName()+" "+booking.getUsers().getPatronymic());
            checkInDateBooking.setValue(booking.getCheckInDate());
            departureDate.setValue(booking.getDepartureDate());
            bookingDescription.setText(booking.getDescription());
            selectedBookingRoom = booking.getRooms().getId();
            roomTable.getSelectionModel().select(Integer.parseInt(selectedBookingRoom+""));
            List<ServiceBooking> bookingServices = serviceBookingService.findServicesByBooking(booking);
            for (ServiceBooking serviceBooking : bookingServices) {
                selectedAdditionalServiceCombobox.getItems().add(serviceBooking.getService().getTitle());
            }
            bookingTypeComboBox.setValue(booking.getBookingType().getType());
        }
    }

    private void searchListener(){

    }

    private void comboBoxListener(){
        serviceRoomBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue!=null){
                roomDataController.setSelectedService(newValue,titleService,priceService);
            }

        });
        editRoomTypeBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                roomDataController.setSelectedRoomType(newValue, numberOfRoom, roomTypeBox);
            }
        });


        FilteredList<String> filteredItems = new FilteredList<>(clientsCombobox.getItems(), p -> true);

        clientsCombobox.getEditor().textProperty().addListener((obs, oldValue, newValue) -> {
            final TextField editor = clientsCombobox.getEditor();
            final String selected = clientsCombobox.getSelectionModel().getSelectedItem();

            Platform.runLater(() -> {
                if (selected == null || !selected.equals(editor.getText())) {
                    filteredItems.setPredicate(item -> {
                        if (item.toUpperCase().contains(newValue.toUpperCase())) {
                            return true;
                        } else {
                            return false;
                        }
                    });
                }
            });
        });

        clientsCombobox.setItems(filteredItems);
    }


    @FXML
    void addAdditionalService(ActionEvent event) {
        if (additionalServiceCombobox.getSelectionModel().getSelectedIndex() != -1) {
            String selectedService = roomDataController.setServiceList(additionalServiceCombobox);
            if (!selectedAdditionalServiceCombobox.getItems().contains(selectedService)) {
                selectedAdditionalServiceCombobox.getItems().add(selectedService);
            } else {
                notificationsController.showDuplicateServiceNotification();
            }
        } else {
            notificationsController.selectInCombobox();
        }
    }


    @FXML
    void deleteAdditionalService(ActionEvent event){
        if(selectedAdditionalServiceCombobox.getSelectionModel().getSelectedIndex()!=-1){
            selectedAdditionalServiceCombobox.getItems().remove(selectedAdditionalServiceCombobox.getSelectionModel().getSelectedItem());
        }else{
            notificationsController.selectInCombobox();
        }
    }

    private void setDayCellFactoryForDatePicker(){
        checkInDateBooking.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();

                setDisable(empty || date.compareTo(today) < 0);
            }
        });

        // Добавляем слушателя изменений для checkInDateBooking
        checkInDateBooking.valueProperty().addListener((observable, oldValue, newValue) -> {
            // Устанавливаем дату checkInDateBooking + 1 день как минимально возможную дату для выбора в departureDate
            departureDate.setDayCellFactory(picker -> new DateCell() {
                @Override
                public void updateItem(LocalDate date, boolean empty) {
                    super.updateItem(date, empty);

                    setDisable(empty || date.compareTo(newValue.plusDays(1)) < 0);
                }
            });
        });
        birthDateClientField.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate eighteenYearsAgo = LocalDate.now().minusYears(18).minusDays(1);

                setDisable(empty || date.compareTo(eighteenYearsAgo) > 0);
            }
        });

        kidDateBirth.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();
                LocalDate eighteenYearsAgo = today.minusYears(18).minusDays(1);
                setDisable(empty || date.compareTo(today.minusDays(1)) > 0 || date.compareTo(eighteenYearsAgo) < 0);
            }
        });
        dateIssueField.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();
                setDisable(empty || date.compareTo(LocalDate.now()) > 0);
            }
        });
    }

    //</editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Вспомогательные методы преводящие поля к стандартным значениям(Вынести в отдельный класс)">
    public void clearClientFields(){
        surnameClientField.setText("");
        nameClientField.setText("");
        patronymicClientField.setText("");
        phoneNumberClients.setText("");
        serialPassportField.setText("");
        numberPassportField.setText("");
        issuedPasportField.setText("");
        birthDateClientField.setValue(LocalDate.now().minusYears(18).minusDays(1));
        dateIssueField.setValue(null);
        placeOfResidenceField.setText("");
    }
    public void clearBookingFields(){
        selectedBookingRoom = 0;
        checkInDateBooking.setValue(null);
        departureDate.setValue(null);
        bookingDescription.setText("");
        bookingAmount.setText("");
        selectedAdditionalServiceCombobox.getItems().clear();
    }
    public void clearChildrenFields(){
        kidSurname.setText("");
        kidName.setText("");
        kidPatronymic.setText("");
        kidDateBirth.setValue(null);
        parentsText.setVisible(false);
        ListOfParents.getItems().clear();
    }

    private void clearRoomFields() {
        numberOfRoom.setText("");
    }

    private void clearRoomTypeFields() {
        priceField.setText("");
        titleRoomType.setText("");
        placesRoomType.setText("");
        tvCheckBox.setSelected(false);
        wifiCheckBox.setSelected(false);
        minibarCheckBox.setSelected(false);
        phoneCheckBox.setSelected(false);
        teapotCheckBox.setSelected(false);
        vaultCheckBox.setSelected(false);
        showerCheckBox.setSelected(false);
        bathCheckBox.setSelected(false);
        conditionerCheckBox.setSelected(false);
        dressingCheckBox.setSelected(false);
        viewOnCityCheckBox.setSelected(false);
        viewOnCourtyardCheckBox.setSelected(false);
        hallCheckBox.setSelected(false);
        additionalBathRoomCheckBox.setSelected(false);
        descriptionRoomField.setText("");
        areaField.setText("");
        selectedRoom = 0;
    }

    private void clearServiceFields() {
        titleService.setText("");
        priceService.setText("");
    }
    //</editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Переключение панелей и вывод формы">
    @FXML
    private void bookingButtonClick(ActionEvent event) {
        setPaneVisibleWithFade(bookingPane);
    }
    @FXML
    private void mainButtonClick(ActionEvent event) {
        setPaneVisibleWithFade(mainPane);
    }
    @FXML
    private void clientButtonClick(ActionEvent event) {
        setPaneVisibleWithFade(ClientsPane);
    }
    @FXML
    private void roomButtonClick(ActionEvent event) {
        setPaneVisibleWithFade(roomPane);
    }
    @FXML
    private void kidsButtonClick(ActionEvent event) {
        setPaneVisibleWithFade(kidsPane);
    }

    public static void loadView(Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader(AuthorizationView.class.getResource("/fxml/AdminHotelPanel.fxml"));
            loader.setControllerFactory(Main.getContext()::getBean);
            AnchorPane root = loader.load();
            stage.getIcons().add(new Image("file:src/main/resources/images/hotel-svgrepo-com.png"));
            // обработчик для событий перетаскивания
            final AdministratorView.Delta dragDelta = new AdministratorView.Delta();
            root.setOnMousePressed(mouseEvent -> {
                dragDelta.x = stage.getX() - mouseEvent.getScreenX();
                dragDelta.y = stage.getY() - mouseEvent.getScreenY();
            });
            root.setOnMouseDragged(mouseEvent -> {
                stage.setX(mouseEvent.getScreenX() + dragDelta.x);
                stage.setY(mouseEvent.getScreenY() + dragDelta.y);
            });

            Scene scene = new Scene(root);
            scene.setFill(javafx.scene.paint.Color.TRANSPARENT);
            stage.setScene(scene);
            stage.show();
            stage.centerOnScreen();
            AdministratorView administratorView = Main.getContext().getBean(AdministratorView.class);
            administratorView.initialize();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Pane getCurrentVisiblePane() {
        if (mainPane.isVisible()) {
            return mainPane;
        } else if (bookingPane.isVisible()) {
            return bookingPane;
        } else if (ClientsPane.isVisible()) {
            return ClientsPane;
        } else if (roomPane.isVisible()) {
            return roomPane;
        } else {
            return kidsPane;
        }
    }
    private void setPaneVisibleWithFade(Pane paneToShow) {
        Pane currentVisiblePane = getCurrentVisiblePane();

        if (currentVisiblePane != paneToShow) {
            FadeTransition fadeOut = new FadeTransition(Duration.seconds(0.5), currentVisiblePane);
            fadeOut.setToValue(0.0);
            fadeOut.setOnFinished(e -> {
                currentVisiblePane.setVisible(false);
                paneToShow.setOpacity(0.0);
                paneToShow.setVisible(true);
                FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.5), paneToShow);
                fadeIn.setToValue(1.0);
                fadeIn.play();
            });
            fadeOut.play();
        }
    }

    //</editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Вспомогательные переменные и объявление экземпляров классов">
    // Класс для хранения смещения
    private static class Delta { double x, y; }

    private long selectedClient = 0;

    private long selectedChildren = 0;

    private long selectedBookingRoom = 0;

    private long selectedBooking = 0;
    @Autowired
    private ExitConfirmationController exitConfirmationController;

    @Autowired
    private ClientDataController clientDataController;

    @Autowired
    private ClientService clientService;

    @Autowired
    private ClientDataInputController clientDataInputController;

    @Autowired
    private ChildrenDataController childrenDataController;

    @Autowired
    private ChildrenDataInputController childrenDataInputController;

    @Autowired
    private NotificationsController notificationsController;

    @Autowired
    private RoomDataController roomDataController;

    @Autowired
    private RoomTypeDataInputController roomTypeDataInputController;

    @Autowired
    private ServiceDataInputController serviceDataInputController;

    @Autowired
    private RoomDataInputController roomDataInputController;

    @Autowired
    private ChildrensService childrensService;

    @Autowired
    private ClientChildrenService clientChildrenService;

    @Autowired
    private RoomsService roomsService;

    @Autowired
    private ServiceRoomTypeService serviceRoomService;
    @Autowired
    private BookingDataInputController bookingDataInputController;

    @Autowired
    private BookingDataController bookingDataController;

    @Autowired
    private BookingService bookingService;
    @Autowired
    private ServiceBookingService serviceBookingService;

    private Map<String, Long> clientMap = new HashMap<>();
    private Map<String, Long> userMap = new HashMap<>();
    private Map<String, Long> serviceMap = new HashMap<>();
    //</editor-fold>

    public void initialize(){
        username.setText(AuthorizationView.getFullusername());
        clientDataController.getClientDataInTable(clientTable,searchClientField);
        childrenDataController.getChildrenDataInTable(kidsTable);
        roomDataController.getRoomDataInTable(roomTableRooms);
        roomDataController.getRoomDataInTableBooking(roomTable,filterRoomPlaces,filterRoomType,searchRoomsBooking);
        clientDataController.fillComboboxUser(parentsCombobox,clientsCombobox, clientMap,userMap);
        roomDataController.fillComboboxRooms(editRoomTypeBox);
        roomDataController.fillComboboxRoomType(roomTypeBox);
        roomDataController.fillComboboxService(serviceRoomBox,additionalServiceCombobox, serviceMap);
        comboBoxListener();
        bookingDataController.getBookingDataInTable(bookingTable,searchBookingInHotel);
        bookingTypeDataController.getBookingTypes(bookingTypeComboBox);
        setDayCellFactoryForDatePicker();
        bookingDataController.getBookingReport(activeBooking,allTimeBooking,Occupied,todayBooking,earnedToday);
        clientDataController.getClientReport(clientsNow,allTimeClients);
        roomDataController.getRoomReport(roomTotal,freeRoom,occupiedRoom);
        birthDateClientField.setValue(LocalDate.now().minusYears(18).minusDays(1));
        filterRoomPlaces.getSelectionModel().selectFirst();
    }
}
