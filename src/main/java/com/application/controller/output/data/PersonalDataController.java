package com.application.controller.output.data;

import com.application.model.users.Personal;
import com.application.service.users.PersonalService;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class PersonalDataController {

    @Autowired
    PersonalService personalService;

    public void fillPersonalPost(ComboBox<String> comboBox){
        comboBox.getItems().clear();
        comboBox.setPromptText("Роль пользователя");
        comboBox.getItems().add("Администратор");
        comboBox.getItems().add("Директор");
    }
    public void fillPersonalUserData(TableView<Personal> usersTable){
        Iterable<Personal> personalIterable = personalService.getAllPersonals();
        usersTable.getItems().clear();

        // Создание столбцов и установка их имен
        TableColumn<Personal, String> idColumn = new TableColumn<>("Номер");
        TableColumn<Personal, String> surnameColumn = new TableColumn<>("Фамилия");
        TableColumn<Personal, String> nameColumn = new TableColumn<>("Имя");
        TableColumn<Personal, String> patronymicColumn = new TableColumn<>("Отчество");
        TableColumn<Personal, String> usernameColumn = new TableColumn<>("Логин");
        TableColumn<Personal, String> postColumn = new TableColumn<>("Должность"); // Здесь используется тип Personal

        idColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId()+""));
        surnameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUser().getSurname()));
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUser().getName()));
        patronymicColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUser().getPatronymic()));
        usernameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUser().getUsername()));
        postColumn.setCellValueFactory(new PropertyValueFactory<>("post"));
        usersTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Добавление столбцов в таблицу
        usersTable.getColumns().setAll(idColumn, surnameColumn, nameColumn, patronymicColumn, usernameColumn, postColumn);

        ObservableList<Personal> personalList = FXCollections.observableArrayList();

        for(Personal personal : personalIterable){
            if(!personal.getUser().getDeleted()){
                personalList.add(personal);
            }
        }
        usersTable.setItems(personalList);
    }





}
