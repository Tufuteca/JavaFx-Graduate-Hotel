package com.application.repository.room;

import com.application.model.room.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ServiceRepository extends JpaRepository<Service, Long> {
    Service findByTitle(String title);
    Service findServiceByid(Long id);
}
