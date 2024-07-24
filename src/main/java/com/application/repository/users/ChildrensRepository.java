package com.application.repository.users;

import com.application.model.users.Childrens;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChildrensRepository extends CrudRepository<Childrens,Long> {
    Childrens findById(long id);
}
