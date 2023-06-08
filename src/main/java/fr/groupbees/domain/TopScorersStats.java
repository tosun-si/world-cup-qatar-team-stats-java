package fr.groupbees.domain;

import java.util.List;

public class TopScorersStats {

    public TopScorersStats(List<Player> players, String goals) {
        this.players = players;
        this.goals = goals;
    }

    private List<Player> players;
    private String goals;

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public String getGoals() {
        return goals;
    }

    public void setGoals(String goals) {
        this.goals = goals;
    }
}
