package com.application.model.room;

import com.application.model.Photos;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomPhoto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_room_photo")
    private Long id;

    @ManyToOne
    private RoomType roomsType;

    @ManyToOne
    private Photos photos;
}
