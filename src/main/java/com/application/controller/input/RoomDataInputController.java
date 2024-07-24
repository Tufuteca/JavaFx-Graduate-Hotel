package com.application.controller.input;

import com.application.model.Status;
import com.application.model.room.ServiceRoomType;
import com.application.model.room.RoomType;
import com.application.model.room.Rooms;
import com.application.model.room.Service;
import com.application.service.StatusService;
import com.application.service.room.RoomTypeService;
import com.application.service.room.RoomsService;
import com.application.service.room.ServiceRoomTypeService;
import com.application.service.room.ServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;



@Controller
public class RoomDataInputController {
    @Autowired
    private RoomsService roomsService;
    @Autowired
    private ServiceService serviceService;
    @Autowired
    private ServiceRoomTypeService serviceRoomService;
    @Autowired
    private RoomTypeService roomTypeService;
    @Autowired
    private StatusService statusService;

    public void addRoom(String numberOfRoom,
                        String roomTypeBox) {
        Rooms room = new Rooms();
        room.setRoomNumber(Integer.parseInt(numberOfRoom));
        RoomType roomType = roomTypeService.getRoomTypeByTitle(roomTypeBox);
        room.setRoomType(roomType);
        Status status = statusService.getStatusFromTitle("Свободен");
        room.setStatus(status);
        roomsService.addRooms(room);
    }

    public void changeRoom(Integer numberOfRoom, String roomTypeBox){
        Rooms room = roomsService.getRoomsByRoomsNumber(numberOfRoom);
        if (room != null) {
            room.setRoomNumber(numberOfRoom);
            RoomType roomType = roomTypeService.getRoomTypeByTitle(roomTypeBox);
            room.setRoomType(roomType);
            roomsService.addRooms(room);
        } else {
            // Handle case where the room is not found
            System.out.println("Room not found for number: " + numberOfRoom);
        }
    }


    public void deleteRoom(int selectedRoom) {
        Rooms room = roomsService.getRoomsByRoomsNumber(selectedRoom);
        roomsService.deleteRoom(room);
    }
}
