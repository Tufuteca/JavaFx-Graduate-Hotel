package com.application.model.room;


import com.application.model.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Rooms {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rooms")
    private Long id;

    private int roomNumber;

    @ManyToOne
    @EqualsAndHashCode.Exclude
    private RoomType roomType;

    @ManyToOne
    @EqualsAndHashCode.Exclude
    private Status status;
}


