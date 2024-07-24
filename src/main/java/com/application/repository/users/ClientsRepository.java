package com.application.repository.users;

import com.application.model.users.Clients;
import com.application.model.users.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientsRepository extends JpaRepository<Clients, Long> {
    Clients findByUser(Users user);
    Clients findById(long id);
}