package fr.groupbees.domain.exception;

public class TeamStatsRawValidatorException extends RuntimeException {
    public TeamStatsRawValidatorException(String errorMessage) {
        super(errorMessage);
    }
}
