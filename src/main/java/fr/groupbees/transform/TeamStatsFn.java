package fr.groupbees.transform;

import fr.groupbees.domain.TeamPlayerStats;
import fr.groupbees.domain.TeamPlayerStatsRaw;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.values.KV;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TeamStatsFn extends DoFn<KV<String, Iterable<TeamPlayerStatsRaw>>, TeamPlayerStats> {

    private static final Logger LOGGER = LoggerFactory.getLogger(TeamStatsFn.class);

    @StartBundle
    public void setup() {
        LOGGER.info("####################Start bundle transform to team stats domain");
    }

    @ProcessElement
    public void processElement(ProcessContext ctx) {
        KV<String, Iterable<TeamPlayerStatsRaw>> playerStatsGroupedPerTeam = ctx.element();

        ctx.output(TeamPlayerStats.computeTeamPlayerStats(
                playerStatsGroupedPerTeam.getKey(),
                playerStatsGroupedPerTeam.getValue())
        );
    }
}
