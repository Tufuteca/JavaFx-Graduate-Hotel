package com.application.model.users;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "Users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idUser")
    private Long id;

    @Column(unique = true)
    private String username;

    private String password;

    @Column(nullable = false)
    private String surname;

    @Column(nullable = false)
    private String name;
    private LocalDate dateOfBirth;
    private String patronymic;
    private String phoneNumber;

    @Column(name = "email")
    private String email;

    @Column(nullable = true)
    private Boolean deleted;

    @Column(nullable = true)
    private Boolean active;

    @Column(nullable = true)
    private Boolean blocked;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    @ToString.Exclude
    private List<Reviews> reviews;


    @OneToOne(mappedBy = "user")
    @ToString.Exclude
    private Clients client;
    @OneToOne(mappedBy = "user")
    @ToString.Exclude
    private Personal personal;

}

