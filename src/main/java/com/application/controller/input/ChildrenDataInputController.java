package com.application.controller.input;

import com.application.model.users.Childrens;
import com.application.model.users.ClientChildren;
import com.application.model.users.Clients;
import com.application.service.users.ChildrensService;
import com.application.service.users.ClientChildrenService;
import com.application.service.users.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;

@Controller
public class ChildrenDataInputController {

    @Autowired
    ChildrensService childrensService;

    @Autowired
    ClientService clientService;

    @Autowired
    ClientChildrenService clientChildrenService;

    public void addChildren(String surname, String name, String patronymic, LocalDate birthdate){
        Childrens children = new Childrens();
        children.setSurname(surname);
        children.setName(name);
        children.setPatronymic(patronymic);
        children.setBirthDate(birthdate);
        children.setDeleted(false);
        childrensService.addChildren(children);
    }

    public void editChild(long selectedChildren, String surname, String name, String patronymic, LocalDate birthdate) {
        Childrens children = childrensService.getChildrenById(selectedChildren);
        children.setSurname(surname);
        children.setName(name);
        children.setPatronymic(patronymic);
        children.setBirthDate(birthdate);
        childrensService.addChildren(children);
    }
    public void setDeleteChild(long selectedChildren) {
        Childrens children = childrensService.getChildrenById(selectedChildren);
        children.setDeleted(true);
        childrensService.addChildren(children);
    }


    public void addChildrenToClient(Long clientId, Long childrenId) {
        Childrens children = childrensService.getChildrenById(childrenId);
        Clients clients = clientService.getClientById(clientId);
        ClientChildren clientChildren = new ClientChildren();
        clientChildren.setClient(clients);
        clientChildren.setChildren(children);
        clientChildrenService.addParentToChildren(clientChildren);
    }




}
