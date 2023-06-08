package fr.groupbees.domain;

import java.util.List;

public class BestPassersStats {

    public BestPassersStats(List<Player> players, String goalAssists) {
        this.players = players;
        this.goalAssists = goalAssists;
    }

    private List<Player> players;
    private String goalAssists;

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public String getGoalAssists() {
        return goalAssists;
    }

    public void setGoalAssists(String goalAssists) {
        this.goalAssists = goalAssists;
    }
}
