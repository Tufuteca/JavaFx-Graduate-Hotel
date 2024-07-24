package com.application.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Photos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_photo")
    private Long id;

    private String url;
}
