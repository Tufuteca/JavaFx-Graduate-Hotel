package com.application.repository.room;

import com.application.model.room.RoomType;
import com.application.model.room.Rooms;
import com.application.model.room.ServiceRoomType;
import com.sun.jdi.LongType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceRoomTypeRepository extends JpaRepository<ServiceRoomType, Long> {

    List<ServiceRoomType> findByRoomsType(RoomType rooms);
}
