package com.application.service.room;

import com.application.model.Status;
import com.application.model.room.RoomType;
import com.application.model.room.Rooms;
import com.application.repository.room.RoomTypeRepository;
import com.application.repository.room.RoomsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomsService {
    @Autowired
    private RoomsRepository roomsRepository;
    @Autowired
    private RoomTypeRepository roomTypeRepository;


    public List<Rooms> getAllRooms(){
        return roomsRepository.findAll();
    }

    public void deleteRoomType(RoomType roomType) {
        roomTypeRepository.delete(roomType);
    }

    public void addRooms(Rooms room) {
        roomsRepository.save(room);
    }
    public Rooms getRoomById(Long roomId) {
        return roomsRepository.findRoomsById(roomId);
    }

    public void deleteRoom(Rooms room) {
        roomsRepository.delete(room);
    }

    public List<Rooms> getRoomsByStatus(Status status) {
        return roomsRepository.findRoomsByStatus(status);
    }
    public Rooms getRoomsByRoomsNumber(Integer roomsNumber) {
        return roomsRepository.findRoomsByRoomNumber(roomsNumber);
    }
}
