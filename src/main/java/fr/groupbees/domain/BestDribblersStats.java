package fr.groupbees.domain;

import java.io.Serializable;
import java.util.List;

public class BestDribblersStats implements Serializable {

    public BestDribblersStats(List<Player> players, String dribbles) {
        this.players = players;
        this.dribbles = dribbles;
    }

    private List<Player> players;
    private String dribbles;

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public String getDribbles() {
        return dribbles;
    }

    public void setDribbles(String dribbles) {
        this.dribbles = dribbles;
    }
}
