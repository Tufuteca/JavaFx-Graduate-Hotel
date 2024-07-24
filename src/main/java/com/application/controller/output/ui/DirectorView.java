package com.application.controller.output.ui;

import com.application.Main;
import com.application.controller.input.BookingTypeDataInputController;
import com.application.controller.output.alert.ExitConfirmationController;
import com.application.controller.output.alert.NotificationsController;
import com.application.controller.output.data.BookingDataController;
import com.application.controller.output.data.BookingTypeDataController;
import com.application.controller.output.report.ClientReport;
import com.application.model.booking.Booking;
import com.application.model.booking.BookingType;
import com.application.service.booking.BookingTypeService;
import com.application.controller.output.report.ReportRooms;
import com.application.util.PrintReport;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.IOException;

@Controller
public class DirectorView {



    @FXML
    private TextArea bookingTypeDescription;

    @FXML
    private Pane bookingTypePane;

    @FXML
    private TableView<BookingType> bookingTypeTable;

    @FXML
    private Pane clientsPane;

    @FXML
    private CheckBox countOfBookingClient;

    @FXML
    private TextField discountBookingType;

    @FXML
    private CheckBox forPrice;

    @FXML
    private TextField maxBook;

    @FXML
    private TextField maxPrice;

    @FXML
    private TextField maxPrice11;

    @FXML
    private TextField minBook;

    @FXML
    private TextField minPrice;

    @FXML
    private TextField minPrice11;

    @FXML
    private RadioButton onAllTimeRooms;

    @FXML
    private RadioButton onYearRooms;

    @FXML
    private RadioButton onMonthRooms;

    @FXML
    private RadioButton onWeekRooms;

    @FXML
    private TextField minCountRooms;

    @FXML
    private TextField maxCountRooms;

    @FXML
    private RadioButton onAllTime;

    @FXML
    private RadioButton onAllTimeClients;

    @FXML
    private RadioButton onMonthBooking;

    @FXML
    private RadioButton onMonthClients;

    @FXML
    private RadioButton onWeekBooking;

    @FXML
    private RadioButton onWeekClients;

    @FXML
    private RadioButton onYearBooking;

    @FXML
    private RadioButton onYearClients;

    @FXML
    private ToggleGroup perDateBooking;

    @FXML
    private ToggleGroup perDateBookingClient;

    @FXML
    private Pane roomsPane;

    @FXML
    private TableView<ClientReport.ClientStats> reportTableClients;

    @FXML
    TableView<ReportRooms.BookingStats> tableStatRooms;

    @FXML
    private TextField titleBookingType;

    @FXML
    private Text username;

    @Autowired
    private BookingTypeService bookingTypeService;
    @Autowired
    private NotificationsController notificationsController;
    @Autowired
    private BookingDataController bookingDataController;

    @Autowired
    private ReportRooms reportRooms;
    @Autowired
    private ClientReport clientReport;

    @FXML
    void addBookingType(ActionEvent event) {
        bookingTypeDataInputController.addBookingType(titleBookingType.getText(),Float.parseFloat(discountBookingType.getText()),bookingTypeDescription.getText());
        clearFields();
        initialize();
        selectedBookingType=0;
        notificationsController.actionSuccessfully();
    }
    private long selectedBookingType = 0;
    @FXML
    void getBookingTypeData(MouseEvent event) {
        SelectionModel<BookingType> selectionModel = bookingTypeTable.getSelectionModel();

        // Получаем выбранную строку
        BookingType selectedItem = selectionModel.getSelectedItem();
        long id = 0;
        if (selectedItem != null) {
            id = selectedItem.getId();
            selectedBookingType = id;
        }
        if(id!=0){
            BookingType bookingType = bookingTypeService.getBookingTypeById(selectedBookingType);
            titleBookingType.setText(bookingType.getType());
            discountBookingType.setText(bookingType.getDiscount()+"");
            bookingTypeDescription.setText(bookingType.getDescription());
        }
    }



    @FXML
    void changeBookingType(ActionEvent event) {
        if(selectedBookingType!=0){
            bookingTypeDataInputController.changeBookingType(selectedBookingType,titleBookingType.getText(),
                    Float.parseFloat(discountBookingType.getText()),
                    bookingTypeDescription.getText());
            clearFields();
            initialize();
            selectedBookingType=0;
            notificationsController.actionSuccessfully();
        }else{
            notificationsController.selectColumnInTable();
        }
    }


    @FXML
    void deleteBookingType(ActionEvent event) {
        if(selectedBookingType!=0){
            if(notificationsController.confirmExitFromApplication()) {
                bookingTypeDataInputController.deleteBookingType(selectedBookingType);
                clearFields();
                initialize();
                selectedBookingType = 0;
            }
        }else{
            notificationsController.selectColumnInTable();
        }
    }

    @FXML
    void exit(MouseEvent event) {
        exitConfirmationController.confirmExitFromAccount(AuthorizationView.getStage());
    }

    @FXML
    void filterBooking(ActionEvent event) {

    }

    @FXML
    void filtredClient(ActionEvent event) {
        clientReport.getClientReportStats(reportTableClients,
                onAllTimeClients.isSelected(),
                onYearClients.isSelected(),
                onMonthClients.isSelected(),
                onWeekClients.isSelected());
    }

    @FXML
    void printTableViewButton(ActionEvent event) {
        String pane = getCurrentVisiblePane()+"";
        if(pane.equals("Pane[id=clientsPane]")){
            if(onAllTimeClients.isSelected()){
                printReport.printReportClients(reportTableClients, "За все время");
            } else if (onYearClients.isSelected()) {
                printReport.printReportClients(reportTableClients, "За последний год");
            }else if (onMonthClients.isSelected()) {
                printReport.printReportClients(reportTableClients, "За последний месяц");
            }else{
                printReport.printReportClients(reportTableClients, "За последнюю неделю");
            }
        } else if (pane.equals("Pane[id=roomsPane]")) {
            if(onAllTimeRooms.isSelected()){
                printReport.printReportBooking(tableStatRooms,"За все время");
            } else if (onYearRooms.isSelected()) {
                printReport.printReportBooking(tableStatRooms,"За последний год");
            }else if (onMonthRooms.isSelected()) {
                printReport.printReportBooking(tableStatRooms,"За последний месяц");
            }else if (onWeekRooms.isSelected()) {
                printReport.printReportBooking(tableStatRooms,"За последнюю неделю");
            }
        }else{
            notificationsController.pleaseFormReportAgain();
        }
    }


    @FXML
    void filterReportRooms(ActionEvent event) {
        reportRooms.getBookingReportStats(tableStatRooms,
                onAllTimeRooms.isSelected(),
                onYearRooms.isSelected(),
                onMonthRooms.isSelected(),
                onWeekRooms.isSelected());
    }



    private void clearFields(){
        titleBookingType.setText("");
        discountBookingType.setText("");
        bookingTypeDescription.setText("");
    }


    @Autowired
    private BookingTypeDataController bookingTypeDataController;

    @Autowired
    private ExitConfirmationController exitConfirmationController;

    @Autowired
    private BookingTypeDataInputController bookingTypeDataInputController;

    @Autowired
    private PrintReport printReport;

    public void initialize() {
        bookingTypeDataController.getBookingTypesInTable(bookingTypeTable);
        username.setText(AuthorizationView.getFullusername());
    }


    public static void loadView(Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader(AuthorizationView.class.getResource("/fxml/Director.fxml"));
            loader.setControllerFactory(Main.getContext()::getBean);
            AnchorPane root = loader.load();
            stage.getIcons().add(new Image("file:src/main/resources/images/hotel-svgrepo-com.png"));
            // обработчик для событий перетаскивания
            final DirectorView.Delta dragDelta = new DirectorView.Delta();
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
            DirectorView directorView = Main.getContext().getBean(DirectorView.class);
            directorView.initialize();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Класс для хранения смещения
    private static class Delta { double x, y; }





    @FXML
    private void bookingTypeOpenPane(ActionEvent event) {
        setPaneVisibleWithFade(bookingTypePane);
    }
    @FXML
    private void roomsButtonClick(ActionEvent event) {
        setPaneVisibleWithFade(roomsPane);
    }

    @FXML
    private void clientButtonClick(ActionEvent event) {
        setPaneVisibleWithFade(clientsPane);
    }

    //Метод который определеяет какая панель сейчас открыта что бы её закрыть
    private Pane getCurrentVisiblePane() {
        if (clientsPane.isVisible()) {
            return clientsPane;
        } else if (roomsPane.isVisible()) {
            return roomsPane; // добавлено
        } else if (bookingTypePane.isVisible()) {
            return bookingTypePane; // добавлено
        } else {
            return null;
        }
    }


    //Метод переключения панелей
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

}
