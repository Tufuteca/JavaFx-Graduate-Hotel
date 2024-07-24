package com.application.service.users;

import com.application.model.users.Clients;
import com.application.model.users.Users;
import com.application.repository.users.ChildrensRepository;
import com.application.repository.users.ClientsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {
    @Autowired
    ClientsRepository clientsRepository;
    @Autowired
    ChildrensRepository childrensRepository;

    public List<Clients> getClientsData(){
        return clientsRepository.findAll();
    }
    public Clients getClientByUser(Users users){
        return clientsRepository.findByUser(users);
    }

    public void addClient(Clients client){
        clientsRepository.save(client);
    }
    public Clients getClientById(long id){
        return clientsRepository.findById(id);
    }
}
