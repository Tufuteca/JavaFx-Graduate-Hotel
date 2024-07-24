package com.application.controller.input;

import com.application.model.room.Service;
import com.application.service.room.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class ServiceDataInputController {
    @Autowired
    private ServiceService serviceService;
    public void addService(String title,Float price){
        Service service = new Service();
        service.setTitle(title);
        service.setPrice(price);
        service.setEnabled(false);
        serviceService.addService(service);
    }
    public void changeService(String title,Float price){
        Service service = serviceService.getServiceByTitle(title);
        service.setTitle(title);
        service.setPrice(price);
        serviceService.addService(service);
    }
    public void deleteService(String title){
        Service service = serviceService.getServiceByTitle(title);
        serviceService.deleteService(service);
    }
}
