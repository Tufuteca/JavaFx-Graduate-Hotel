package com.application.controller.output.ui;

import com.application.Main;
import com.application.controller.input.UserDataInputController;
import com.application.controller.output.alert.ExitConfirmationController;
import com.application.controller.output.alert.NotificationsController;
import com.application.controller.output.data.PersonalDataController;
import com.application.model.users.Personal;

import com.application.model.users.Users;
import com.application.service.users.PersonalService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import javafx.scene.text.Text;

import java.io.IOException;

@Controller
public class SystemAdminView {

    @FXML
    private Text idUser;

    @FXML
    private TextField loginField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField passwordField;

    @FXML
    private TextField patronymicField;

    @FXML
    private ComboBox<String> roleBox;

    @FXML
    private TextField surnameField;

    @FXML
    private Text textLogin;

    @FXML
    private Text textName;

    @FXML
    private Text textPassword;

    @FXML
    private Text textSurname;

    @FXML
    private Text textpatronymic;

    @FXML
    private TableView<Personal> usersTable;

    @Autowired
    private ExitConfirmationController exitConfirmationController;

    @Autowired
    UserDataInputController userDataInputController;
    @Autowired
    private NotificationsController notificationsController;

    @FXML
    void addUser(ActionEvent event) {
        String login = loginField.getText();
        String surname = surnameField.getText();
        String name = nameField.getText();
        String patronymic = patronymicField.getText();
        String role = roleBox.getValue();
        String password = passwordField.getText();

        // Проверка на пустые поля
        if (login.isEmpty() || surname.isEmpty() || name.isEmpty() || patronymic.isEmpty() || role == null || password.isEmpty()) {
            notificationsController.errorMessage("Все поля должны быть заполнены.");
            return;
        }

        userDataInputController.addPersonal(login, surname, name, patronymic, role, password);
        initialize();
        clearAllFields();
        notificationsController.actionSuccessfully();
    }


    public void clearAllFields(){
        surnameField.setText("");
        loginField.setText("");
        passwordField.setText("");
        nameField.setText("");
        patronymicField.setText("");
    }

    @FXML
    void changeUser(ActionEvent event) {
        String login = loginField.getText();
        String surname = surnameField.getText();
        String name = nameField.getText();
        String patronymic = patronymicField.getText();
        String role = roleBox.getValue();
        String password = passwordField.getText();
        if (login.isEmpty() || surname.isEmpty() || name.isEmpty() || patronymic.isEmpty() || role == null) {
            notificationsController.errorMessage("Все поля должны быть заполнены.");
            return;
        }
        if(selectedUserId!=0){
            userDataInputController.changeUserData(selectedUserId,loginField.getText(),surnameField.getText(),nameField.getText(),patronymicField.getText(),roleBox.getValue(),passwordField.getText());
            initialize();
            clearAllFields();
            notificationsController.actionSuccessfully();
        }else{
            notificationsController.selectColumnInTable();
        }
    }

    @Autowired
    private PersonalService personalService;

    @FXML
    void exit(MouseEvent event) {
        exitConfirmationController.confirmExitFromAccount(AuthorizationView.getStage());
    }


    private long selectedUserId = 0;
    @FXML
    void getUserData(MouseEvent event) {
        // Получаем модель выбора из TableView
        SelectionModel<Personal> selectionModel = usersTable.getSelectionModel();

        // Получаем выбранную строку
        Personal selectedItem = selectionModel.getSelectedItem();
        long id = 0;
        if (selectedItem != null) {
            id = selectedItem.getUser().getId();
            selectedUserId = id;
        }
        if(id!=0){
            Users user = new Users();
            user.setId(id);
            var personal = personalService.getPersonalByUser(user);
            surnameField.setText(personal.getUser().getSurname());
            nameField.setText(personal.getUser().getName());
            patronymicField.setText(personal.getUser().getPatronymic());
            loginField.setText(personal.getUser().getUsername());
            roleBox.setValue(personal.getPost());
        }
    }


    @FXML
    void setDeleted(ActionEvent event) {
        if(selectedUserId!=0){
            if(notificationsController.confirmExitFromApplication()) {
                userDataInputController.makeProfileDeleted(selectedUserId);
                initialize();
                clearAllFields();
            }
        }else{
            notificationsController.selectColumnInTable();
        }
    }

    @Autowired
    private PersonalDataController personalDataController;


    public void initialize() {
        personalDataController.fillPersonalPost(roleBox);
        personalDataController.fillPersonalUserData(usersTable);
    }

    public static void loadView(Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader(AuthorizationView.class.getResource("/fxml/AdminPanel.fxml"));
            loader.setControllerFactory(Main.getContext()::getBean);
            AnchorPane root = loader.load();
            stage.getIcons().add(new Image("file:src/main/resources/images/hotel-svgrepo-com.png"));
            // обработчик для событий перетаскивания
            final SystemAdminView.Delta dragDelta = new SystemAdminView.Delta();
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
            SystemAdminView systemAdminView = Main.getContext().getBean(SystemAdminView.class);
            systemAdminView.initialize();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    // Класс для хранения смещения
    private static class Delta { double x, y; }

}
