package com.todopelota.todopelota.model;

import java.util.List;

public class TournamentPositionsResponse {
    private String tournamentName;
    private List<Position> positions;

    // getters and setters

    public String getTournamentName() {
        return tournamentName;
    }

    public void setTournamentName(String tournamentName) {
        this.tournamentName = tournamentName;
    }

    public List<Position> getPositions() {
        return positions;
    }

    public void setPositions(List<Position> positions) {
        this.positions = positions;
    }

}
