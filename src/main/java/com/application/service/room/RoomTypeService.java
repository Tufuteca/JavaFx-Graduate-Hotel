package com.application.service.room;

import com.application.model.room.RoomType;
import com.application.repository.room.RoomTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomTypeService {
    @Autowired
    private RoomTypeRepository roomTypeRepository;

    public List<RoomType> getAllRoomTypes() {
        return roomTypeRepository.findAll();
    }

    public void addRoomType(RoomType roomType) {
        roomTypeRepository.save(roomType);
    }

    public RoomType getRoomTypeByTitle(String title) {
        return roomTypeRepository.findByTitle(title);
    }

    public RoomType findRoomTypeById(Long roomType) {
        return roomTypeRepository.findRoomTypeById(roomType);
    }

    public RoomType getRoomTypeById(Long selectedRoom) {
        return roomTypeRepository.findRoomTypeById(selectedRoom);
    }

    public void deleteRoomType(RoomType roomType) {
        roomTypeRepository.delete(roomType);
    }
}
