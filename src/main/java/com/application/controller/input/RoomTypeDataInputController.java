package com.application.controller.input;

import com.application.model.room.RoomType;
import com.application.model.room.Service;
import com.application.model.room.ServiceRoomType;
import com.application.repository.room.RoomTypeRepository;
import com.application.service.room.RoomTypeService;
import com.application.service.room.RoomsService;
import com.application.service.room.ServiceRoomTypeService;
import com.application.service.room.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class RoomTypeDataInputController {
    @Autowired
    private RoomTypeService roomTypeService;
    @Autowired
    private RoomsService roomsService;
    @Autowired
    private ServiceService serviceService;
    @Autowired
    private ServiceRoomTypeService serviceRoomTypeService;

    public void addRoomType(String title, Integer places, String description,String area,String price, boolean... servicesCheckBoxes) {
        RoomType roomType = new RoomType();
        roomType.setTitle(title);
        roomType.setPlaces(places);
        roomType.setPrice(Float.parseFloat(price));
        roomType.setDescription(description);
        roomType.setArea(Float.parseFloat(area));
        roomTypeService.addRoomType(roomType);

        String[] services = {"Телевизор", "WI-FI", "Минибар", "Телефон", "Чайник", "Сейф", "Душевая кабина", "Ванная", "Кондиционер", "Гардеробная", "Вид на город", "Вид на двор", "Гостинная", "Дополнительный санузел"};

        for (int i = 0; i < services.length; i++) {
            if (servicesCheckBoxes[i]) {
                Service service = serviceService.getServiceByTitle(services[i]);
                if (service != null) {
                    ServiceRoomType serviceRoom = new ServiceRoomType();
                    serviceRoom.setRoomsType(roomType);
                    serviceRoom.setService(service);
                    serviceRoomTypeService.save(serviceRoom);
                }
            }
        }
    }
    public void changeRoomType(Long selectedRoom, String title, Integer places, String description,String area,String price, boolean... servicesCheckBoxes){
        RoomType roomType = roomTypeService.getRoomTypeById(selectedRoom);
        roomType.setTitle(title);
        roomType.setPlaces(places);
        roomType.setPrice(Float.parseFloat(price));
        roomType.setDescription(description);
        roomType.setArea(Float.parseFloat(area));
        roomTypeService.addRoomType(roomType);
        roomTypeService.addRoomType(roomType);

        // Удаляем все существующие услуги для комнаты
        List<ServiceRoomType> existingServices = serviceRoomTypeService.getRoomServicesByRoom(roomType);
        for (ServiceRoomType serviceRoom : existingServices) {
            serviceRoomTypeService.delete(serviceRoom);
        }

        String[] services = {"Телевизор", "WI-FI", "Минибар", "Телефон", "Чайник", "Сейф", "Душевая кабина", "Ванная", "Кондиционер", "Гардеробная", "Вид на город", "Вид на двор", "Гостинная", "Дополнительный санузел"};

        for (int i = 0; i < services.length; i++) {
            if (servicesCheckBoxes[i]) {
                Service service = serviceService.getServiceByTitle(services[i]);
                if (service != null) {
                    ServiceRoomType serviceRoom = new ServiceRoomType();
                    serviceRoom.setRoomsType(roomType);
                    serviceRoom.setService(service);
                    serviceRoomTypeService.save(serviceRoom);
                }
            }
        }
    }

    public void deleteRoomType(Long s) {
        try {
            RoomType roomType = roomTypeService.getRoomTypeById(s);
            List<ServiceRoomType> existingServices = serviceRoomTypeService.getRoomServicesByRoom(roomType);
            for (ServiceRoomType serviceRoom : existingServices) {
                serviceRoomTypeService.delete(serviceRoom);
            }
            roomTypeService.deleteRoomType(roomType);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
