package com.application.service.users;


import com.application.model.users.Childrens;
import com.application.repository.users.ChildrensRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChildrensService {

    @Autowired
    ChildrensRepository childrensRepository;


    public Iterable<Childrens> getAll() {
        return childrensRepository.findAll();
    }

    public void addChildren(Childrens children) {
        childrensRepository.save(children);
    }
    public Childrens getChildrenById(long id) {
        return childrensRepository.findById(id);
    }

}
