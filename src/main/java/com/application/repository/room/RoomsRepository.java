package com.application.repository.room;

import com.application.model.Status;
import com.application.model.room.Rooms;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomsRepository extends JpaRepository<Rooms, Long> {

    Rooms findRoomsById(long id);
    List<Rooms> findRoomsByStatus(Status status);

    Rooms findRoomsByRoomNumber(int roomNumber);
}
