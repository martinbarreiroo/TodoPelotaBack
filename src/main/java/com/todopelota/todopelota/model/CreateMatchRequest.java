package com.todopelota.todopelota.model;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class CreateMatchRequest {

    private List<String> team1;
    private List<String> team2;
    private Date date;
    private String location;
    private String description;
    private Long tournamentId;
    private String matchId;
    private String result1;
    private String result2;
    private List<PlayerStat>yellowCards;
    private List<PlayerStat> redCards;
    private List<PlayerStat> goals;
    private List<PlayerStat> assists;
    private Integer team1Points;
    private Integer team2Points;


    // Getters and setters...
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

    public String getMatchId() {
        return matchId;
    }

    public void setMatchId(String matchId) {
        this.matchId = matchId;
    }

    public String getResult1() {
        return result1;
    }

    public void setResult1(String result) {
        this.result1 = result;
    }

    public String getResult2() {
        return result2;
    }

    public void setResult2(String result) {
        this.result2 = result;
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

    public Long getTournamentId() {
        return tournamentId;
    }

    public void setTournamentId(Long tournamentId) {
        this.tournamentId = tournamentId;
    }

    public List<PlayerStat> getYellowCards() {
        return yellowCards;
    }

    public void setYellowCards(List<PlayerStat>yellowCards) {
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
