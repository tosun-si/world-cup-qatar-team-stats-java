package fr.groupbees.domain;

public class Player {

    private String playerName;
    private String playerDob;
    private String position;
    private String club;
    private String brandSponsorAndUsed;
    private String appearances;

    public Player(String playerName,
                  String playerDob,
                  String position,
                  String club,
                  String brandSponsorAndUsed,
                  String appearances) {
        this.playerName = playerName;
        this.playerDob = playerDob;
        this.position = position;
        this.club = club;
        this.brandSponsorAndUsed = brandSponsorAndUsed;
        this.appearances = appearances;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getPlayerDob() {
        return playerDob;
    }

    public void setPlayerDob(String playerDob) {
        this.playerDob = playerDob;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getClub() {
        return club;
    }

    public void setClub(String club) {
        this.club = club;
    }

    public String getBrandSponsorAndUsed() {
        return brandSponsorAndUsed;
    }

    public void setBrandSponsorAndUsed(String brandSponsorAndUsed) {
        this.brandSponsorAndUsed = brandSponsorAndUsed;
    }

    public String getAppearances() {
        return appearances;
    }

    public void setAppearances(String appearances) {
        this.appearances = appearances;
    }
}
