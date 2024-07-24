package com.application.repository.users;

import com.application.model.users.Personal;
import com.application.model.users.Users;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonalRepository extends CrudRepository<Personal, Long> {
    Personal findByUser(Users user);

}
