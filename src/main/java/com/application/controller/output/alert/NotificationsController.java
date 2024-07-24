package com.application.controller.output.alert;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import org.springframework.stereotype.Controller;

import java.util.Optional;

@Controller
public class NotificationsController {

    public void loginOrPasswordNull(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Заполните все поля");
        alert.setHeaderText("Поля логин или пароль пустые");
        alert.setContentText("Заполните все поля");
        alert.show();
    }

    public void invalidLoginOrPassword() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Ошибка!");
        alert.setHeaderText("Неверный логин или пароль");
        alert.setContentText("Проверьте правильность введеных данных");
        alert.show();
    }
    public void randomPasswordInfo(String password){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Новый пароль сгенерирован");
        alert.setHeaderText("Пароль: "+password);
        alert.setContentText("Запишите его прежде чем закрыть это окно");
        alert.show();
    }
    public void selectColumnInTable(){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Ошибка!");
        alert.setHeaderText("Вы не выбрали поле в таблице");
        alert.show();
    }

    public void selectInCombobox() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Ошибка!");
        alert.setHeaderText("Выберите значение в выпадающем списке");
        alert.show();
    }

    public void checkData() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Ошибка!");
        alert.setHeaderText("Проверьте правильность заполненых полей и выберите номер для бронирования!");
        alert.show();
    }

    public void pressTheButton() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Ошибка!");
        alert.setHeaderText("Нажмите кнопку рассчитать!");
        alert.show();
    }

    public void chooseAnotherDate() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Ошибка!");
        alert.setHeaderText("Бронь для данного номера на текущий период занята");
        alert.show();
    }

    public void pleaseFormReportAgain() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Ошибка!");
        alert.setHeaderText("Сформируйте отчет заново");
        alert.show();
    }

    public void printSuccessfully() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Успешно!");
        alert.setHeaderText("Отчет сохранен");
        alert.show();
    }

    public void inputRoomNumber() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Ошибка!!");
        alert.setHeaderText("Ведите целое число");
        alert.show();
    }

    public void actionSuccessfully() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Успешно!!");
        alert.setHeaderText("Действие выполнено");
        alert.show();
    }

    public boolean confirmExitFromApplication() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Подтверждение");
        alert.setHeaderText("Вы уверены, что хотите выполнить текущее действие??");
        alert.setContentText("Нажмите OK для подтверждения, или CANCEL для отмены.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            return true;
        }else{
            return false;
        }
    }

    public void validateClientData() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Ошибка!!");
        alert.setHeaderText("Не все данные клиента заполнены!");
        alert.show();
    }

    public void errorMessage(String s) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Ошибка!!");
        alert.setHeaderText(s);
        alert.show();
    }

    public void showDuplicateServiceNotification() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Ошибка!!");
        alert.setHeaderText("Дополнительный сервис дублируется!");
        alert.show();
    }
}
