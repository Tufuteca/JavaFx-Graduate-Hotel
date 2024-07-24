package com.application.controller.output.ui;
 

import java.io.IOException;

import com.application.Main;
import com.application.controller.output.alert.ExitConfirmationController;
import com.application.controller.output.alert.NotificationsController;
import com.application.service.users.UsersService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;


@Controller
public class AuthorizationView {

    ExitConfirmationController exitController = new ExitConfirmationController();

    NotificationsController notificationsController = new NotificationsController();

    @Autowired
    UsersService usersService;

    @FXML
    TextField loginField, passwordField;

    @Getter
    private static Stage stage;


    @FXML
    private void exit(MouseEvent event) {
        exitController.confirmExitFromApplication();
    }

    public static void loadView(Stage stage) {
        try {
            AuthorizationView.stage = stage;
            FXMLLoader loader = new FXMLLoader(AuthorizationView.class.getResource("/fxml/Auth.fxml"));
            loader.setControllerFactory(Main.getContext()::getBean);
            AnchorPane root = loader.load();
            stage.getIcons().add(new Image("file:src/main/resources/images/hotel-svgrepo-com.png"));
            // обработчик для событий перетаскивания
            final Delta dragDelta = new Delta();
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
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.show();
            stage.centerOnScreen();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Класс для хранения смещения
    private static class Delta { double x, y; }

    @Getter
    @Setter
    private static String fullusername;

    @FXML
    private void login(ActionEvent event) throws IOException {
        String login = loginField.getText();
        String password = passwordField.getText();

        if(login.equals("admin") && password.equals("admin")) {
            SystemAdminView systemAdminView = new SystemAdminView();
            systemAdminView.loadView(stage);
        }else if (!login.isEmpty() && !password.isEmpty()) {
            String role = usersService.authorizeUser(login, password);
            switch (role) {
                case "Администратор":
                    AdministratorView.loadView(stage);
                    break;
                case "Директор":
                    DirectorView.loadView(stage);
                    break;
                default:
                    notificationsController.invalidLoginOrPassword();
                    break;
            }
        } else {
            notificationsController.loginOrPasswordNull();
        }
    }


}
