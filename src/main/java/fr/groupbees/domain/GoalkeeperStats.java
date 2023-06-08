package fr.groupbees.domain;

public class GoalkeeperStats {

    public GoalkeeperStats(String playerName, String appearances, String savePercentage, String cleanSheets) {
        this.playerName = playerName;
        this.appearances = appearances;
        this.savePercentage = savePercentage;
        this.cleanSheets = cleanSheets;
    }

    private String playerName;
    private String appearances;
    private String savePercentage;
    private String cleanSheets;

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getAppearances() {
        return appearances;
    }

    public void setAppearances(String appearances) {
        this.appearances = appearances;
    }

    public String getSavePercentage() {
        return savePercentage;
    }

    public void setSavePercentage(String savePercentage) {
        this.savePercentage = savePercentage;
    }

    public String getCleanSheets() {
        return cleanSheets;
    }

    public void setCleanSheets(String cleanSheets) {
        this.cleanSheets = cleanSheets;
    }
}
