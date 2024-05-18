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

    @ElementCollection
    private List<PlayerStat> yellowCards;

    @ElementCollection
    private List<PlayerStat> redCards;

    @ElementCollection
    private List<PlayerStat> goals;

    @ElementCollection
    private List<PlayerStat> assists;

    private Integer team1Points;

    private Integer team2Points;

    private Boolean hasBeenUpdated = false;

    private Integer numberOfGoals = 0;

    private Integer numberOfAssists = 0;

    private Integer numberOfYellowCards = 0;

    private Integer numberOfRedCards = 0;


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

    public List<PlayerStat> getYellowCards() {
        return yellowCards;
    }

    public void setYellowCards(List<PlayerStat> yellowCards) {
        this.yellowCards = yellowCards;
    }

    public List<PlayerStat> getRedCards() {
        return redCards;
    }

    public void setRedCards(List<PlayerStat> redCards) {
        this.redCards = redCards;
    }

    public List<PlayerStat> getGoals() {
        return goals;
    }

    public void setGoals(List<PlayerStat> goals) {
        this.goals = goals;
    }

    public List<PlayerStat> getAssists() {
        return assists;
    }

    public void setAssists(List<PlayerStat> assists) {
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

    public Integer getNumberOfGoals() {
        if(numberOfGoals == null) {
            return 0;
        }
        return numberOfGoals;
    }

    public void setNumberOfGoals(Integer numberOfGoals) {
        this.numberOfGoals = numberOfGoals != null ? numberOfGoals : 0;
    }

    public Integer getNumberOfAssists() {
        if (numberOfAssists == null) {
            return 0;
        }
        return numberOfAssists;
    }

    public void setNumberOfAssists(Integer numberOfAssists) {
        this.numberOfAssists = numberOfAssists != null ? numberOfAssists : 0;;
    }

    public Integer getNumberOfYellowCards() {
        if (numberOfYellowCards == null) {
            return 0;
        }
        return numberOfYellowCards;
    }

    public void setNumberOfYellowCards(Integer numberOfYellowCards) {
        this.numberOfYellowCards = numberOfYellowCards != null ? numberOfYellowCards : 0;;
    }

    public Integer getNumberOfRedCards() {
        if (numberOfRedCards == null) {
            return 0;
        }
        return numberOfRedCards;
    }

    public void setNumberOfRedCards(Integer numberOfRedCards) {
        this.numberOfRedCards = numberOfRedCards != null ? numberOfRedCards : 0;
    }

}
