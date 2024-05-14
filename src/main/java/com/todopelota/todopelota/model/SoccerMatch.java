package com.todopelota.todopelota.model;

import jakarta.persistence.*;

import java.util.*;

@Entity
public class SoccerMatch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany
    private Set<User> users = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    private Tournament tournament;

    @ElementCollection
    private List<String> team1 = new ArrayList<>();

    @ElementCollection
    private List<String> team2 = new ArrayList<>();

    private Date date;

    private String location;

    private String description;

    private Integer result1;

    private Integer result2;

    private Integer yellowCards;

    private Integer redCards;

    private Integer goals;

    private Integer assists;

    private Integer team1Points;

    private Integer team2Points;

    private Boolean hasBeenUpdated = false;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(User user) {
        this.users.add(user);
    }

    public Tournament getTournament() {
        return tournament;
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getResult1() {
        return result1;
    }

    public void setResult1(Integer result) {
        this.result1 = result;
    }

    public Integer getResult2() {
        return result2;
    }

    public void setResult2(Integer result) {
        this.result2 = result;
    }

    public Integer getYellowCards() {
        return yellowCards;
    }

    public void setYellowCards(Integer yellowCards) {
        this.yellowCards = yellowCards;
    }

    public Integer getRedCards() {
        return redCards;
    }

    public void setRedCards(Integer redCards) {
        this.redCards = redCards;
    }

    public Integer getGoals() {
        return goals;
    }

    public void setGoals(Integer goals) {
        this.goals = goals;
    }

    public Integer getAssists() {
        return assists;
    }

    public void setAssists(Integer assists){
        this.assists = assists;
    }

    public List<String> getTeam1() {
        return team1;
    }

    public void setTeam1(List<String> team1) {
        this.team1 = team1;
    }

    public List<String> getTeam2() {
        return team2;
    }

    public void setTeam2(List<String> team2) {
        this.team2 = team2;
    }

    public Boolean getHasBeenUpdated() {
        return hasBeenUpdated;
    }

    public void setHasBeenUpdated(Boolean hasBeenUpdated) {
        this.hasBeenUpdated = hasBeenUpdated;
    }

    public Integer getTeam1Points() {
        return team1Points;
    }

    public void setTeam1Points(Integer team1Points) {
        this.team1Points = team1Points;
    }

    public Integer getTeam2Points() {
        return team2Points;
    }

    public void setTeam2Points(Integer team2Points) {
        this.team2Points = team2Points;
    }
}
