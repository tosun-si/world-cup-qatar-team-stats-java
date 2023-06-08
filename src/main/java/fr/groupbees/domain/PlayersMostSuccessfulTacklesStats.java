package fr.groupbees.domain;

import java.util.List;

public class PlayersMostSuccessfulTacklesStats {

    public PlayersMostSuccessfulTacklesStats(List<Player> players, String successfulTackles) {
        this.players = players;
        this.successfulTackles = successfulTackles;
    }

    private List<Player> players;
    private String successfulTackles;

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public String getSuccessfulTackles() {
        return successfulTackles;
    }

    public void setSuccessfulTackles(String successfulTackles) {
        this.successfulTackles = successfulTackles;
    }
}
