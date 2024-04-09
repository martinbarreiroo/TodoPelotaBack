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

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_tournaments",
            joinColumns = @JoinColumn(name = "userId"),
            inverseJoinColumns = @JoinColumn(name = "tournamentId"))
    private Set<Tournament> tournaments = new HashSet<>();



    public Set<Tournament> getTournaments() {
        return tournaments;
    }

    public void setTournaments(Set<Tournament> tournaments) {
        this.tournaments = tournaments;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    /*
    public void setParticipants(Set<User> participants) {
        this.participants = participants;
    }

    public Set<User> getParticipants() {
        return participants;
    }
    */
    public Long getTournamentId() {
        return tournamentId;
    }
}