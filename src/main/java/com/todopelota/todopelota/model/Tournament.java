package com.todopelota.todopelota.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Tournament {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tournament_id")
    private Long tournament_id;

    private String name;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_tournaments",
            joinColumns = @JoinColumn(name = "tournament_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> participants = new HashSet<>();

    private String admin;

    private Integer maxParticipants;

    private String description;

    private String type;

    // Getters and setters

    public Long getTournamentId() {
        return tournament_id;
    }

    public void setTournamentId(Long tournamentId) {
        this.tournament_id = tournamentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<User> getParticipants() {
        return participants;
    }

    public void setParticipants(User participant) {
        this.participants.add(participant);
    }

    public void setAdmin(String username) {
    }

    public String getAdmin() {
        return admin;
    }

    public Integer getMaxParticipants() {
        return maxParticipants;
    }

    public void setMaxParticipants(Integer maxParticipants) {
        this.maxParticipants = maxParticipants;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}