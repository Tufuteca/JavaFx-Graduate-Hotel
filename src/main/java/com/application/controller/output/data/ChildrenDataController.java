package com.application.controller.output.data;


import com.application.model.users.Childrens;
import com.application.model.users.ClientChildren;
import com.application.model.users.Clients;
import com.application.service.users.ChildrensService;
import com.application.service.users.ClientChildrenService;
import com.application.service.users.ClientService;
import com.mysql.cj.xdevapi.Client;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class ChildrenDataController {

    @Autowired
    ChildrensService childrensService;
    @Autowired
    ClientChildrenService clientChildrenService;
    @Autowired
    ClientService clientService;

    public void getChildrenDataInTable(TableView<Childrens> kidsTable) {
        Iterable<Childrens> childrensIterable = childrensService.getAll();
        kidsTable.getItems().clear();

        // Создание столбцов и установка их имен
        TableColumn<Childrens, String> idColumn = new TableColumn<>("Номер");
        TableColumn<Childrens, String> surnameColumn = new TableColumn<>("Фамилия");
        TableColumn<Childrens, String> nameColumn = new TableColumn<>("Имя");
        TableColumn<Childrens, String> patronymicColumn = new TableColumn<>("Отчество");
        TableColumn<Childrens, String> dateOfBirthColumn = new TableColumn<>("Дата рождения");

        idColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId()+""));
        surnameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSurname()));
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        patronymicColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPatronymic()));
        dateOfBirthColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getBirthDate()+""));
        kidsTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Добавление столбцов в таблицу
        kidsTable.getColumns().setAll(idColumn, surnameColumn, nameColumn, patronymicColumn, dateOfBirthColumn);

        ObservableList<Childrens> clientsObservableList = FXCollections.observableArrayList();

        for(Childrens childrens : childrensIterable){
            if(!childrens.getDeleted()){
                clientsObservableList.add(childrens);
            }
        }
        kidsTable.setItems(clientsObservableList);

    }

    public void deleteParent(long selectedIndex, long selectedChildren) {
        Childrens childrens = childrensService.getChildrenById(selectedChildren);
        Clients clients = clientService.getClientById(selectedIndex);
        System.out.println(selectedIndex+" "+clients);
        System.out.println(selectedChildren+" "+childrens);
        List<ClientChildren> clientChildren = clientChildrenService.getClientChildrenByChildId(childrens);
        for(ClientChildren clientChildren1: clientChildren){
            if(clientChildren1.getClient().getId() == clients.getId()){
                clientChildrenService.deleteParent(clientChildren1);
            }
        }


    }
}
