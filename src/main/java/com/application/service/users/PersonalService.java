package com.application.service.users;

import com.application.model.users.Personal;
import com.application.model.users.Users;
import com.application.repository.users.PersonalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonalService {

    @Autowired
    private PersonalRepository personalRepository;


    public Iterable<Personal> getAllPersonals() {
        return personalRepository.findAll();
    }

    public Personal getPersonalByUser(Users users){
        return personalRepository.findByUser(users);
    }

    public void addUser(Personal personal){
        personalRepository.save(personal);
    }


}
