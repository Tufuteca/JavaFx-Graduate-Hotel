package com.application.service.users;


import com.application.model.users.Childrens;
import com.application.model.users.ClientChildren;
import com.application.repository.users.ClientChildrenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClientChildrenService {
    @Autowired
    ClientChildrenRepository clientChildrenRepository;


    public void addParentToChildren(ClientChildren clientChildren) {
        clientChildrenRepository.save(clientChildren);
    }

    @Transactional
    public List<ClientChildren> getClientChildrenByChildId(Childrens childrens) {
        return clientChildrenRepository.findByChildren(childrens);
    }
    @Transactional
    public void deleteParent(ClientChildren clientChildren) {
        clientChildrenRepository.delete(clientChildren);
    }
}
