package fr.groupbees.application;

import com.google.api.services.bigquery.model.TableRow;
import fr.groupbees.domain.*;
import fr.groupbees.transform.TeamStatsFn;
import fr.groupbees.transform.TeamStatsWithRankingFn;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.io.gcp.bigquery.BigQueryIO;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.transforms.*;
import org.apache.beam.sdk.values.PCollectionView;
import org.apache.beam.sdk.values.TypeDescriptors;
import org.joda.time.Instant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.apache.beam.sdk.values.TypeDescriptor.of;

public class WorldCupStatsApp {
    private static final Logger LOGGER = LoggerFactory.getLogger(WorldCupStatsApp.class);

    public static void main(String[] args) {
        final WorldCupStatsOptions options = PipelineOptionsFactory
                .fromArgs(args)
                .withValidation()
                .as(WorldCupStatsOptions.class);

        Pipeline pipeline = Pipeline.create(options);

        PCollectionView<List<TeamFifaRanking>> fifaRankingSideInput = pipeline
                .apply("Read Input Json file", TextIO.read().from(options.getInputJsonFile()))
                .apply("Deserialize to Fifa Ranking object", MapElements
                        .into(of(TeamFifaRanking.class))
                        .via(WorldCupStatsApp::deserializeToTeamRanking))
                .apply("Ranking side input as List", View.asList());

        pipeline
                .apply("Read teams players stats", TextIO.read().from(options.getInputJsonFile()))
                .apply("Deserialize to Player stats object", MapElements
                        .into(of(TeamPlayerStatsRaw.class)).
                        via(WorldCupStatsApp::deserializeToPlayerStatsRaw))
                .apply("Validate fields", MapElements
                        .into(of(TeamPlayerStatsRaw.class))
                        .via(TeamPlayerStatsRaw::validateFields))
                .apply("Filter Players with kit sponsor", Filter.by(t -> !t.getNationalTeamKitSponsor().equals("")))
                .apply("Filter Players with position", Filter.by(t -> !t.getPosition().equals("")))
                .apply("With key on team name", WithKeys.of((TeamPlayerStatsRaw t) -> t.getNationality())
                        .withKeyType(TypeDescriptors.strings()))
                .apply("Group by team name", GroupByKey.create())
                .apply("Map to players stats domain", ParDo.of(new TeamStatsFn()))
                .apply("Map to players stats with Fifa ranking", ParDo
                        .of(new TeamStatsWithRankingFn(fifaRankingSideInput))
                        .withSideInputs(fifaRankingSideInput))
                .apply("Write Player stats to BigQuery", BigQueryIO.<TeamPlayerStats>write()
                        .withMethod(BigQueryIO.Write.Method.FILE_LOADS)
                        .to(options.getWorldCupStatsDataset() + "." + options.getWorldCupTeamPlayerStatsTable())
                        .withFormatFunction(WorldCupStatsApp::toTeamPlayerStatsTableRow)
                        .withCreateDisposition(BigQueryIO.Write.CreateDisposition.CREATE_NEVER)
                        .withWriteDisposition(BigQueryIO.Write.WriteDisposition.WRITE_APPEND));

        pipeline.run().waitUntilFinish();

        LOGGER.info("End of CDP integration case JOB");
    }

    private static TeamPlayerStats addFifaRankingToTeam(
            DoFn<TeamPlayerStats, TeamPlayerStats>.ProcessContext context,
            PCollectionView<List<TeamFifaRanking>> fifaRankingSideInput
    ) {
        List<TeamFifaRanking> teamFifaRankingList = context.sideInput(fifaRankingSideInput);
        TeamPlayerStats teamPlayerStats = context.element();

        return teamPlayerStats.addFifaRankingToTeamStats(teamFifaRankingList);
    }

    private static TeamPlayerStatsRaw deserializeToPlayerStatsRaw(String playerStatsAsString) {
        return JsonUtil.deserialize(playerStatsAsString, TeamPlayerStatsRaw.class);
    }

    private static TeamFifaRanking deserializeToTeamRanking(String teamRankingAsString) {
        return JsonUtil.deserialize(teamRankingAsString, TeamFifaRanking.class);
    }

    private static TableRow toTeamPlayerStatsTableRow(TeamPlayerStats teamPlayerStats) {
        TopScorersStats topScorers = teamPlayerStats.getTopScorers();
        BestPassersStats bestPassers = teamPlayerStats.getBestPassers();
        BestDribblersStats bestDribblers = teamPlayerStats.getBestDribblers();
        GoalkeeperStats goalKeeper = teamPlayerStats.getGoalKeeper();
        PlayersMostAppearancesStats playersMostAppearances = teamPlayerStats.getPlayersMostAppearances();
        PlayersMostDuelsWonStats playersMostDuelsWon = teamPlayerStats.getPlayersMostDuelsWon();
        PlayersMostInterceptionStats playersMostInterception = teamPlayerStats.getPlayersMostInterception();
        PlayersMostSuccessfulTacklesStats playersMostSuccessfulTackles = teamPlayerStats.getPlayersMostSuccessfulTackles();

        TableRow topScorersRow = new TableRow()
                .set("players", toPlayerTableRows(topScorers.getPlayers()))
                .set("goals", Integer.valueOf(topScorers.getGoals()));

        TableRow bestPassersRow = new TableRow()
                .set("players", toPlayerTableRows(bestPassers.getPlayers()))
                .set("goalAssists", Integer.valueOf(bestPassers.getGoalAssists()));

        TableRow bestDribblersRow = new TableRow()
                .set("players", toPlayerTableRows(bestDribblers.getPlayers()))
                .set("dribbles", Float.valueOf(bestDribblers.getDribbles()));

        TableRow goalKeeperRow = new TableRow()
                .set("playerName", goalKeeper.getPlayerName())
                .set("appearances", goalKeeper.getAppearances())
                .set("savePercentage", goalKeeper.getSavePercentage())
                .set("cleanSheets", goalKeeper.getCleanSheets());

        TableRow playersMostAppearancesRow = new TableRow()
                .set("players", toPlayerTableRows(playersMostAppearances.getPlayers()))
                .set("appearances", Integer.valueOf(playersMostAppearances.getAppearances()));

        TableRow playersMostDuelsWonRow = new TableRow()
                .set("players", toPlayerTableRows(playersMostDuelsWon.getPlayers()))
                .set("duels", Float.valueOf(playersMostDuelsWon.getDuels()));

        TableRow playersMostInterceptionRow = new TableRow()
                .set("players", toPlayerTableRows(playersMostInterception.getPlayers()))
                .set("interceptions", Float.valueOf(playersMostInterception.getInterceptions()));

        TableRow playersMostSuccessfulTacklesRow = new TableRow()
                .set("players", toPlayerTableRows(playersMostSuccessfulTackles.getPlayers()))
                .set("successfulTackles", Float.valueOf(playersMostSuccessfulTackles.getSuccessfulTackles()));

        return new TableRow()
                .set("teamName", teamPlayerStats.getTeamName())
                .set("teamTotalGoals", teamPlayerStats.getTeamTotalGoals())
                .set("fifaRanking", teamPlayerStats.getFifaRanking())
                .set("nationalTeamKitSponsor", teamPlayerStats.getNationalTeamKitSponsor())
                .set("topScorers", topScorersRow)
                .set("bestPassers", bestPassersRow)
                .set("bestDribblers", bestDribblersRow)
                .set("goalKeeper", goalKeeperRow)
                .set("playersMostAppearances", playersMostAppearancesRow)
                .set("playersMostDuelsWon", playersMostDuelsWonRow)
                .set("playersMostInterception", playersMostInterceptionRow)
                .set("playersMostSuccessfulTackles", playersMostSuccessfulTacklesRow)
                .set("ingestionDate", new Instant().toString());
    }

    private static List<TableRow> toPlayerTableRows(List<Player> players) {
        return players.stream().map(WorldCupStatsApp::toPlayerTableRow).collect(toList());
    }

    private static TableRow toPlayerTableRow(Player player) {
        return new TableRow()
                .set("playerName", player.getPlayerName())
                .set("playerDob", player.getPlayerDob())
                .set("position", player.getPosition())
                .set("club", player.getClub())
                .set("brandSponsorAndUsed", player.getBrandSponsorAndUsed())
                .set("appearances", Integer.valueOf(player.getAppearances()));
    }
}
