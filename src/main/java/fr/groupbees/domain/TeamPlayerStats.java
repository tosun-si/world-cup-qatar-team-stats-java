package fr.groupbees.domain;

import fr.groupbees.domain.exception.TeamFifaRankingUnknownException;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toList;

public class TeamPlayerStats {

    public TeamPlayerStats(String teamName,
                           Integer teamTotalGoals,
                           Integer fifaRanking,
                           String nationalTeamKitSponsor,
                           TopScorersStats topScorers,
                           BestPassersStats bestPassers,
                           BestDribblersStats bestDribblers,
                           GoalkeeperStats goalKeeper,
                           PlayersMostAppearancesStats playersMostAppearances,
                           PlayersMostDuelsWonStats playersMostDuelsWon,
                           PlayersMostInterceptionStats playersMostInterception,
                           PlayersMostSuccessfulTacklesStats playersMostSuccessfulTackles) {
        this.teamName = teamName;
        this.teamTotalGoals = teamTotalGoals;
        this.fifaRanking = fifaRanking;
        this.nationalTeamKitSponsor = nationalTeamKitSponsor;
        this.topScorers = topScorers;
        this.bestPassers = bestPassers;
        this.bestDribblers = bestDribblers;
        this.goalKeeper = goalKeeper;
        this.playersMostAppearances = playersMostAppearances;
        this.playersMostDuelsWon = playersMostDuelsWon;
        this.playersMostInterception = playersMostInterception;
        this.playersMostSuccessfulTackles = playersMostSuccessfulTackles;
    }

    private String teamName;
    private Integer teamTotalGoals;
    private Integer fifaRanking;
    private String nationalTeamKitSponsor;
    private TopScorersStats topScorers;
    private BestPassersStats bestPassers;
    private BestDribblersStats bestDribblers;
    private GoalkeeperStats goalKeeper;
    private PlayersMostAppearancesStats playersMostAppearances;
    private PlayersMostDuelsWonStats playersMostDuelsWon;
    private PlayersMostInterceptionStats playersMostInterception;
    private PlayersMostSuccessfulTacklesStats playersMostSuccessfulTackles;

    public TeamPlayerStats addFifaRankingToTeamStats(List<TeamFifaRanking> teamFifaRankingList) {
        final TeamFifaRanking currentTeamFifaRanking = teamFifaRankingList
                .stream()
                .filter(r -> r.getTeamName().equals(teamName))
                .findFirst()
                .orElseThrow(() -> new TeamFifaRankingUnknownException("Team ranking unknown for team $teamName"));

        this.setFifaRanking(currentTeamFifaRanking.getFifaRanking());

        return this;
    }

    private static Stream<TeamPlayerStatsRaw> getStatsRawAsStream(final Iterable<TeamPlayerStatsRaw> teamPlayersStatsRaw) {
        return StreamSupport.stream(teamPlayersStatsRaw.spliterator(), false);
    }

    public static TeamPlayerStats computeTeamPlayerStats(
            String nationality,
            Iterable<TeamPlayerStatsRaw> teamPlayersStatsRaw
    ) {
        final String topScorersValue = getStatsRawAsStream(teamPlayersStatsRaw)
                .filter(t -> isInteger(t.getGoalsScored()))
                .max(Comparator.comparing(t -> Integer.valueOf(t.getGoalsScored())))
                .orElseThrow(() -> new IllegalStateException("No max Goal scored"))
                .getGoalsScored();

        final List<Player> topScorers = getStatsRawAsStream(teamPlayersStatsRaw)
                .filter(t -> t.getGoalsScored().equals(topScorersValue))
                .filter(t -> !t.getGoalsScored().equals("0"))
                .map(TeamPlayerStats::toPlayer)
                .collect(toList());

        final String bestPassersValue = getStatsRawAsStream(teamPlayersStatsRaw)
                .filter(t -> isInteger(t.getAssistsProvided()))
                .max(Comparator.comparing(t -> Integer.valueOf(t.getAssistsProvided())))
                .orElseThrow(() -> new IllegalStateException("No max Assist"))
                .getAssistsProvided();

        final List<Player> bestPassers = getStatsRawAsStream(teamPlayersStatsRaw)
                .filter(t -> t.getAssistsProvided().equals(bestPassersValue))
                .filter(t -> !t.getAssistsProvided().equals("0"))
                .map(TeamPlayerStats::toPlayer)
                .collect(toList());

        final String bestDribblersValue = getStatsRawAsStream(teamPlayersStatsRaw)
                .filter(t -> isInteger(t.getDribblesPerNinety()))
                .max(Comparator.comparing(t -> Float.valueOf(t.getDribblesPerNinety())))
                .orElseThrow(() -> new IllegalStateException("No max Dribbles"))
                .getDribblesPerNinety();

        final List<Player> bestDribblers = getStatsRawAsStream(teamPlayersStatsRaw)
                .filter(t -> t.getDribblesPerNinety().equals(bestDribblersValue))
                .map(TeamPlayerStats::toPlayer)
                .collect(toList());

        final String playerMostAppearancesValue = getStatsRawAsStream(teamPlayersStatsRaw)
                .filter(t -> isInteger(t.getAppearances()))
                .max(Comparator.comparing(t -> Integer.valueOf(t.getAppearances())))
                .orElseThrow(() -> new IllegalStateException("No max appearance"))
                .getAppearances();

        final List<Player> playerWithMostAppearance = getStatsRawAsStream(teamPlayersStatsRaw)
                .filter(t -> t.getAppearances().equals(playerMostAppearancesValue))
                .map(TeamPlayerStats::toPlayer)
                .collect(toList());

        final String playersMostDuelsWonValue = getStatsRawAsStream(teamPlayersStatsRaw)
                .filter(t -> isFloat(t.getTotalDuelsWonPerNinety()))
                .max(Comparator.comparing(t -> Float.valueOf(t.getTotalDuelsWonPerNinety())))
                .orElseThrow(() -> new IllegalStateException("No max total duels"))
                .getTotalDuelsWonPerNinety();

        final List<Player> playersMostDuelsWon = getStatsRawAsStream(teamPlayersStatsRaw)
                .filter(t -> t.getTotalDuelsWonPerNinety().equals(playersMostDuelsWonValue))
                .map(TeamPlayerStats::toPlayer)
                .collect(toList());

        final String playersMostInterceptionsValue = getStatsRawAsStream(teamPlayersStatsRaw)
                .filter(t -> isFloat(t.getInterceptionsPerNinety()))
                .max(Comparator.comparing(t -> Float.valueOf(t.getInterceptionsPerNinety())))
                .orElseThrow(() -> new IllegalStateException("No max total interceptions"))
                .getInterceptionsPerNinety();

        final List<Player> playersMostInterception = getStatsRawAsStream(teamPlayersStatsRaw)
                .filter(t -> t.getInterceptionsPerNinety().equals(playersMostInterceptionsValue))
                .map(TeamPlayerStats::toPlayer)
                .collect(toList());

        final String playersMostSuccessfulTacklesValue = getStatsRawAsStream(teamPlayersStatsRaw)
                .filter(t -> isFloat(t.getTacklesPerNinety()))
                .max(Comparator.comparing(t -> Float.valueOf(t.getTacklesPerNinety())))
                .orElseThrow(() -> new IllegalStateException("No max total interceptions"))
                .getTacklesPerNinety();

        final List<Player> playersMostSuccessfulTackles = getStatsRawAsStream(teamPlayersStatsRaw)
                .filter(t -> t.getTacklesPerNinety().equals(playersMostSuccessfulTacklesValue))
                .map(TeamPlayerStats::toPlayer)
                .collect(toList());

        final TeamPlayerStatsRaw currentGoalKeeperStats = getStatsRawAsStream(teamPlayersStatsRaw)
                .filter(t -> !t.getSavePercentage().equals("-"))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No goalkeeper stats"));

        final int teamTotalGoals = getStatsRawAsStream(teamPlayersStatsRaw)
                .map(TeamPlayerStatsRaw::getGoalsScored)
                .filter(TeamPlayerStats::isInteger)
                .mapToInt(Integer::valueOf)
                .sum();

        final TopScorersStats topScorersStats = new TopScorersStats(
                topScorers,
                topScorersValue
        );

        final BestPassersStats bestPassersStats = new BestPassersStats(
                bestPassers,
                bestPassersValue
        );

        final BestDribblersStats bestDribblersStats = new BestDribblersStats(
                bestDribblers,
                bestDribblersValue
        );

        final PlayersMostAppearancesStats playersMostAppearancesStats = new PlayersMostAppearancesStats(
                playerWithMostAppearance,
                playerMostAppearancesValue
        );

        final PlayersMostDuelsWonStats playersMostDuelsWonStats = new PlayersMostDuelsWonStats(
                playersMostDuelsWon,
                playersMostDuelsWonValue
        );

        final PlayersMostInterceptionStats playersMostInterceptionStats = new PlayersMostInterceptionStats(
                playersMostInterception,
                playersMostInterceptionsValue
        );

        final PlayersMostSuccessfulTacklesStats playersMostSuccessfulTacklesStats = new PlayersMostSuccessfulTacklesStats(
                playersMostSuccessfulTackles,
                playersMostSuccessfulTacklesValue
        );

        final GoalkeeperStats goalKeeperStats = new GoalkeeperStats(
                currentGoalKeeperStats.getPlayerName(),
                currentGoalKeeperStats.getAppearances(),
                currentGoalKeeperStats.getSavePercentage(),
                currentGoalKeeperStats.getCleanSheets()
        );

        final TeamPlayerStatsRaw currentTeam = getStatsRawAsStream(teamPlayersStatsRaw)
                .findFirst()
                .get();

        return new TeamPlayerStats(
                nationality,
                teamTotalGoals,
                null,
                currentTeam.getNationalTeamKitSponsor(),
                topScorersStats,
                bestPassersStats,
                bestDribblersStats,
                goalKeeperStats,
                playersMostAppearancesStats,
                playersMostDuelsWonStats,
                playersMostInterceptionStats,
                playersMostSuccessfulTacklesStats
        );
    }

    private static Player toPlayer(TeamPlayerStatsRaw teamPlayerStats) {
        return new Player(
                teamPlayerStats.getPlayerName(),
                teamPlayerStats.getPlayerDob(),
                teamPlayerStats.getPosition(),
                teamPlayerStats.getClub(),
                teamPlayerStats.getBrandSponsorAndUsed(),
                teamPlayerStats.getAppearances()
        );
    }

    private static boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private static boolean isFloat(String str) {
        try {
            Float.parseFloat(str);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public Integer getTeamTotalGoals() {
        return teamTotalGoals;
    }

    public void setTeamTotalGoals(Integer teamTotalGoals) {
        this.teamTotalGoals = teamTotalGoals;
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

    public TopScorersStats getTopScorers() {
        return topScorers;
    }

    public void setTopScorers(TopScorersStats topScorers) {
        this.topScorers = topScorers;
    }

    public BestPassersStats getBestPassers() {
        return bestPassers;
    }

    public void setBestPassers(BestPassersStats bestPassers) {
        this.bestPassers = bestPassers;
    }

    public BestDribblersStats getBestDribblers() {
        return bestDribblers;
    }

    public void setBestDribblers(BestDribblersStats bestDribblers) {
        this.bestDribblers = bestDribblers;
    }

    public GoalkeeperStats getGoalKeeper() {
        return goalKeeper;
    }

    public void setGoalKeeper(GoalkeeperStats goalKeeper) {
        this.goalKeeper = goalKeeper;
    }

    public PlayersMostAppearancesStats getPlayersMostAppearances() {
        return playersMostAppearances;
    }

    public void setPlayersMostAppearances(PlayersMostAppearancesStats playersMostAppearances) {
        this.playersMostAppearances = playersMostAppearances;
    }

    public PlayersMostDuelsWonStats getPlayersMostDuelsWon() {
        return playersMostDuelsWon;
    }

    public void setPlayersMostDuelsWon(PlayersMostDuelsWonStats playersMostDuelsWon) {
        this.playersMostDuelsWon = playersMostDuelsWon;
    }

    public PlayersMostInterceptionStats getPlayersMostInterception() {
        return playersMostInterception;
    }

    public void setPlayersMostInterception(PlayersMostInterceptionStats playersMostInterception) {
        this.playersMostInterception = playersMostInterception;
    }

    public PlayersMostSuccessfulTacklesStats getPlayersMostSuccessfulTackles() {
        return playersMostSuccessfulTackles;
    }

    public void setPlayersMostSuccessfulTackles(PlayersMostSuccessfulTacklesStats playersMostSuccessfulTackles) {
        this.playersMostSuccessfulTackles = playersMostSuccessfulTackles;
    }
}
