package com.application.service.room;


import com.application.repository.room.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceService {
    @Autowired
    private ServiceRepository serviceRepository;

    public List<com.application.model.room.Service> getAllServices() {
        return serviceRepository.findAll();
    }

    public void addService(com.application.model.room.Service service) {
        serviceRepository.save(service);
    }

    public com.application.model.room.Service getServiceByTitle(String title) {
        return serviceRepository.findByTitle(title);

    }
    public void deleteService(com.application.model.room.Service service) {
        serviceRepository.delete(service);
    }
}
