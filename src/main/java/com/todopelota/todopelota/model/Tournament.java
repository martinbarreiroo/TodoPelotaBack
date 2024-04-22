package com.todopelota.todopelota.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.todopelota.todopelota.serializer.TournamentSerializer;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@JsonSerialize(using = TournamentSerializer.class)
public class Tournament {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tournament_id")
    private Long id;

    @Column(name = "admin")
    private Long adminId;

    private String adminUsername;

    private String name;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "user_tournaments",
            joinColumns = @JoinColumn(name = "tournament_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> participants = new HashSet<>();

    @OneToMany(mappedBy = "tournament")
    private Set<SoccerMatch> soccerMatches = new HashSet<>();

    @ManyToMany
    private Set<User> invitedUsers = new HashSet<>();

    private Integer maxParticipants;

    private String description;

    private String type;

    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public void setAdminId(Long id) { this.adminId = id; }

    public Long getAdminId() {
        return adminId;
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

    public Set<SoccerMatch> getSoccerMatches() {
        return soccerMatches;
    }

    public void setSoccerMatches(SoccerMatch soccerMatch) {
        this.soccerMatches.add(soccerMatch);
    }

    public Set<User> getInvitedUsers() {
        return invitedUsers;
    }

    public void setInvitedUsers(User user) {
        this.invitedUsers.add(user);
    }

    public String getAdminUsername() {
        return adminUsername;
    }

    public void setAdminUsername(String adminUsername) {
        this.adminUsername = adminUsername;
    }
}