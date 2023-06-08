package fr.groupbees.domain;

import java.util.List;

public class PlayersMostInterceptionStats {

    public PlayersMostInterceptionStats(List<Player> players, String interceptions) {
        this.players = players;
        this.interceptions = interceptions;
    }

    private List<Player> players;
    private String interceptions;

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public String getInterceptions() {
        return interceptions;
    }

    public void setInterceptions(String interceptions) {
        this.interceptions = interceptions;
    }
}
