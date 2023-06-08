package fr.groupbees.domain.exception;

public class TeamFifaRankingUnknownException extends RuntimeException {

    public TeamFifaRankingUnknownException(String errorMessage) {
        super(errorMessage);
    }
}
