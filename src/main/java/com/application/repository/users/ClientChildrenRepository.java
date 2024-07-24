package com.application.repository.users;

import com.application.model.users.Childrens;
import com.application.model.users.ClientChildren;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClientChildrenRepository extends JpaRepository<ClientChildren, Long> {
    List<ClientChildren> findByChildren(Childrens childrens);

}
