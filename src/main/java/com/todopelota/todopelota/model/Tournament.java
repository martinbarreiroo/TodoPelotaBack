package com.todopelota.todopelota.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.todopelota.todopelota.serializer.TournamentSerializer;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.List;
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

    @ManyToOne
    @JoinColumn(name = "creator_id")
    private User creator;

    @OneToMany(mappedBy = "tournament", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<SoccerMatch> soccerMatches = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "tournament_participants",
            joinColumns = @JoinColumn(name = "tournament_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> invitedUsers = new HashSet<>();

    @OneToMany(mappedBy = "tournament", cascade = CascadeType.ALL)
    private Set<Position> positions = new HashSet<>();

    @OneToMany(mappedBy = "tournament", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Message> messages;

    private String maxParticipants;

    private String description;

    private String type;

    private Boolean fixtureGenerated = false;

    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getCreator() { return creator; }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAdminId(Long id) { this.adminId = id; }

    public Long getAdminId() {
        return adminId;
    }

    public String getMaxParticipants() {
        return maxParticipants;
    }

    public void setMaxParticipants(String maxParticipants) {
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

    public Set<String> getInvitedUsersToString() {
        Set<String> invitedUsers = new HashSet<>();
        for (User user : this.invitedUsers) {
            invitedUsers.add(user.getUsername());
        }
        return invitedUsers;
    }

    public void setInvitedUsers(User user) {
        this.invitedUsers.add(user);
    }

    public void setInvitedUsersDeleteUser(Set<User> users) {
        this.invitedUsers = users;
    }

    public String getAdminUsername() {
        return adminUsername;
    }

    public void setAdminUsername(String adminUsername) {
        this.adminUsername = adminUsername;
    }

    public Set<Position> getPositions() {
        return positions;
    }

    public void setPositions(Set<Position> positions) {
        this.positions = positions;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void addMessage(Message message) {
        this.messages.add(message);
    }

    public Boolean getFixtureGenerated() {
        return fixtureGenerated;
    }

    public void setFixtureGenerated(Boolean fixtureGenerated) {
        this.fixtureGenerated = fixtureGenerated;
    }
}