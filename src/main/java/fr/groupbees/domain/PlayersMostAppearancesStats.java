package fr.groupbees.domain;

import java.util.List;

public class PlayersMostAppearancesStats {

    public PlayersMostAppearancesStats(List<Player> players, String appearances) {
        this.players = players;
        this.appearances = appearances;
    }

    private List<Player> players;
    private String appearances;

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public String getAppearances() {
        return appearances;
    }

    public void setAppearances(String appearances) {
        this.appearances = appearances;
    }
}
