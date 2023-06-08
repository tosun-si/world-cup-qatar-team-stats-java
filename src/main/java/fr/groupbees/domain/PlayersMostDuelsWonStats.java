package fr.groupbees.domain;

import java.util.List;

public class PlayersMostDuelsWonStats {

    public PlayersMostDuelsWonStats(List<Player> players, String duels) {
        this.players = players;
        this.duels = duels;
    }

    private List<Player> players;
    private String duels;

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public String getDuels() {
        return duels;
    }

    public void setDuels(String duels) {
        this.duels = duels;
    }
}
