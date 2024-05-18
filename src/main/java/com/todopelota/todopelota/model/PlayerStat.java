package com.todopelota.todopelota.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Embeddable;

@Embeddable
public class PlayerStat {
    @JsonProperty("player")
    private String playerName;
    @JsonProperty("stat")
    private String stat;

    public PlayerStat() {
    }

    public PlayerStat(String playerName, String stat) {
        this.playerName = playerName;
        this.stat = stat;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }

}
