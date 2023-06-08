package fr.groupbees.application;

import org.apache.beam.sdk.options.Description;
import org.apache.beam.sdk.options.PipelineOptions;

public interface WorldCupStatsOptions extends PipelineOptions {
    @Description("Path of the input Json file to read from")
    String getInputJsonFile();

    void setInputJsonFile(String value);

    @Description("Path of Fifa ranking side input")
    String getInputFileTeamFifaRanking();

    void setInputFileTeamFifaRanking(String value);

    @Description("Output dataset")
    String getWorldCupStatsDataset();

    void setWorldCupStatsDataset(String value);

    @Description("Output table")
    String getWorldCupTeamPlayerStatsTable();

    void setWorldCupTeamPlayerStatsTable(String value);
}
