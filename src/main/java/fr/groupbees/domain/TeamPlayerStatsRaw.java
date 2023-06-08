package fr.groupbees.domain;

import fr.groupbees.domain.exception.TeamStatsRawValidatorException;

import java.util.Objects;

public class TeamPlayerStatsRaw {

    private static final String PLAYER_NATIONALITY_EMPTY_ERROR_MESSAGE = "Player nationality name cannot be null or empty";

    private String nationality;
    private Integer fifaRanking;
    private String nationalTeamKitSponsor;
    private String position;
    private Integer nationalTeamJerseyNumber;
    private String playerDob;
    private String club;
    private String playerName;
    private String appearances;
    private String goalsScored;
    private String assistsProvided;
    private String dribblesPerNinety;
    private String interceptionsPerNinety;
    private String tacklesPerNinety;
    private String totalDuelsWonPerNinety;
    private String savePercentage;
    private String cleanSheets;
    private String brandSponsorAndUsed;

    public TeamPlayerStatsRaw validateFields() {
        if (Objects.isNull(nationality) || nationality.equals("")) {
            throw new TeamStatsRawValidatorException(PLAYER_NATIONALITY_EMPTY_ERROR_MESSAGE);
        }
        return this;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public Integer getFifaRanking() {
        return fifaRanking;
    }

    public void setFifaRanking(Integer fifaRanking) {
        this.fifaRanking = fifaRanking;
    }

    public String getNationalTeamKitSponsor() {
        return nationalTeamKitSponsor;
    }

    public void setNationalTeamKitSponsor(String nationalTeamKitSponsor) {
        this.nationalTeamKitSponsor = nationalTeamKitSponsor;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Integer getNationalTeamJerseyNumber() {
        return nationalTeamJerseyNumber;
    }

    public void setNationalTeamJerseyNumber(Integer nationalTeamJerseyNumber) {
        this.nationalTeamJerseyNumber = nationalTeamJerseyNumber;
    }

    public String getPlayerDob() {
        return playerDob;
    }

    public void setPlayerDob(String playerDob) {
        this.playerDob = playerDob;
    }

    public String getClub() {
        return club;
    }

    public void setClub(String club) {
        this.club = club;
    }

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

    public String getGoalsScored() {
        return goalsScored;
    }

    public void setGoalsScored(String goalsScored) {
        this.goalsScored = goalsScored;
    }

    public String getAssistsProvided() {
        return assistsProvided;
    }

    public void setAssistsProvided(String assistsProvided) {
        this.assistsProvided = assistsProvided;
    }

    public String getDribblesPerNinety() {
        return dribblesPerNinety;
    }

    public void setDribblesPerNinety(String dribblesPerNinety) {
        this.dribblesPerNinety = dribblesPerNinety;
    }

    public String getInterceptionsPerNinety() {
        return interceptionsPerNinety;
    }

    public void setInterceptionsPerNinety(String interceptionsPerNinety) {
        this.interceptionsPerNinety = interceptionsPerNinety;
    }

    public String getTacklesPerNinety() {
        return tacklesPerNinety;
    }

    public void setTacklesPerNinety(String tacklesPerNinety) {
        this.tacklesPerNinety = tacklesPerNinety;
    }

    public String getTotalDuelsWonPerNinety() {
        return totalDuelsWonPerNinety;
    }

    public void setTotalDuelsWonPerNinety(String totalDuelsWonPerNinety) {
        this.totalDuelsWonPerNinety = totalDuelsWonPerNinety;
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

    public String getBrandSponsorAndUsed() {
        return brandSponsorAndUsed;
    }

    public void setBrandSponsorAndUsed(String brandSponsorAndUsed) {
        this.brandSponsorAndUsed = brandSponsorAndUsed;
    }
}
