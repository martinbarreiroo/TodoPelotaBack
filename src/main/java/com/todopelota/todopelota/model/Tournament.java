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


    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setParticipants(Set<User> participants) {
        this.participants = participants;
    }

    public Set<User> getParticipants() {
        return participants;
    }

    public Long getTournamentId() {
        return tournamentId;
    }
}