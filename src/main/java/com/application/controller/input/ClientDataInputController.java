package com.application.controller.input;


import com.application.model.users.Clients;
import com.application.model.users.Users;
import com.application.service.users.ClientService;
import com.application.service.users.UsersService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;

@Controller
public class ClientDataInputController {
    @Autowired
    UsersService usersService;
    @Autowired
    ClientService clientService;

    public void addUser(String surname,
                        String name,
                        String patronymic,
                        String phone,
                        String serialPas,
                        String numberPas,
                        String issuedPas,
                        LocalDate birthDate,
                        LocalDate dateIssue,
                        String registration){
        try {
            Users user = new Users();
            user.setActive(false);
            user.setDeleted(false);
            user.setBlocked(false);
            user.setSurname(surname);
            user.setName(name);
            user.setPatronymic(patronymic);
            user.setPhoneNumber(phone);
            user.setDateOfBirth(birthDate);

            usersService.addUser(user);
            Clients client = new Clients();
            client.setUser(user);
            client.setPassportSeries(Integer.parseInt(serialPas));
            client.setPassportNumber(Integer.parseInt(numberPas));
            client.setIssuedPasport(issuedPas);
            client.setDateIssue(dateIssue);
            client.setRegistration(registration);

            clientService.addClient(client);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void editUser(Long id,
                         String surname,
                         String name,
                         String patronymic,
                         String phone,
                         String serialPas,
                         String numberPas,
                         String issuedPas,
                         LocalDate birthDate,
                         LocalDate dateIssue,
                         String registration){
        try {
            Users user = usersService.findUserById(id);
            user.setSurname(surname);
            user.setName(name);
            user.setPatronymic(patronymic);
            user.setPhoneNumber(phone);
            user.setDateOfBirth(birthDate);

            usersService.addUser(user);
            Clients client = clientService.getClientByUser(user);
            client.setPassportSeries(Integer.parseInt(serialPas));
            client.setPassportNumber(Integer.parseInt(numberPas));
            client.setIssuedPasport(issuedPas);
            client.setDateIssue(dateIssue);
            client.setRegistration(registration);

            clientService.addClient(client);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setDeleteUser(long selectedClient) {
        Users user = usersService.findUserById(selectedClient);
        user.setDeleted(true);
        usersService.addUser(user);
    }
}
