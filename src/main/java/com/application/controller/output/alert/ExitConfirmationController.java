package com.application.controller.output.alert;


import com.application.Main;
import com.application.controller.output.ui.AuthorizationView;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.util.Optional;

@Controller
public class ExitConfirmationController {

    public void confirmExitFromApplication() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Подтверждение");
        alert.setHeaderText("Вы уверены, что хотите выйти?");
        alert.setContentText("Нажмите OK для выхода, или CANCEL для продолжения работы.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            System.exit(0);
        }
    }
    public void confirmExitFromAccount(Stage stage) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Подтверждение");
        alert.setHeaderText("Вы уверены, что хотите выйти из учетной записи?");
        alert.setContentText("Нажмите OK для выхода, или CANCEL для продолжения работы.");

        // Отображение диалогового окна и обработка выбора пользователя
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
                try {
                    FXMLLoader loader = new FXMLLoader(AuthorizationView.class.getResource("/fxml/Auth.fxml"));
                    loader.setControllerFactory(Main.getContext()::getBean);
                    AnchorPane root = loader.load();
                    Scene scene = new Scene(root);
                    scene.setFill(javafx.scene.paint.Color.TRANSPARENT);
                    stage.setScene(scene);
                    stage.show();
                    stage.centerOnScreen();
                    AuthorizationView authorizationView = Main.getContext().getBean(AuthorizationView.class);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
        }
    }

}
