package com.application.service.room;

import com.application.model.room.RoomType;
import com.application.model.room.Rooms;
import com.application.model.room.ServiceRoomType;
import com.application.repository.room.ServiceRoomTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceRoomTypeService {
    @Autowired
    private ServiceRoomTypeRepository serviceRoomRepository;

    public ServiceRoomType save(ServiceRoomType serviceRoom) {
        return serviceRoomRepository.save(serviceRoom);
    }
    public List<ServiceRoomType> getRoomServicesByRoom(RoomType rooms) {
        return serviceRoomRepository.findByRoomsType(rooms);
    }

    public void delete(ServiceRoomType serviceRoom) {
        serviceRoomRepository.delete(serviceRoom);
    }
}
