package com.todopelota.todopelota.model;

import jakarta.persistence.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Tournament {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tournamentId;

    private String name;

    @ManyToMany
    @JoinTable(
            name = "tournament_participants",
            joinColumns = @JoinColumn(name = "tournamentiId"),
            inverseJoinColumns = @JoinColumn(name = "userId")
    )
    private Set<User> participants = new HashSet<>();
    // other fields...

    // getters and setters...
}