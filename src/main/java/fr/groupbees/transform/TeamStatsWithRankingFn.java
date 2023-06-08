package fr.groupbees.transform;

import fr.groupbees.domain.TeamFifaRanking;
import fr.groupbees.domain.TeamPlayerStats;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.values.PCollectionView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class TeamStatsWithRankingFn extends DoFn<TeamPlayerStats, TeamPlayerStats> {

    private final PCollectionView<List<TeamFifaRanking>> fifaRankingSideInput;

    public TeamStatsWithRankingFn(PCollectionView<List<TeamFifaRanking>> fifaRankingSideInput) {
        this.fifaRankingSideInput = fifaRankingSideInput;
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(TeamStatsWithRankingFn.class);

    @Setup
    public void setup() {
        LOGGER.info("####################Setup transform to team stats with Fifa Ranking");
    }

    @ProcessElement
    public void processElement(ProcessContext ctx) {
        TeamPlayerStats currentPlayerStats = ctx.element();
        List<TeamFifaRanking> fifaRankings = ctx.sideInput(fifaRankingSideInput);

        ctx.output(currentPlayerStats.addFifaRankingToTeamStats(fifaRankings));
    }
}
