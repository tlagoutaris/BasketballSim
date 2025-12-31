package result;

import model.Team;
import util.BoundedNormalDistribution;
import config.Config;

public class PossessionResult {
    public enum OutcomeType {
        STEAL,
        NON_SHOOTING_FOUL,
        SHOOTING_FOUL,
        SHOT_MADE,
        SHOT_OUT_OF_BOUNDS,
        PASS_OUT_OF_BOUNDS,
        PASS_DEFLECTED_OUT_OF_BOUNDS,
        OFFENSIVE_REBOUND,
        DEFENSIVE_REBOUND,
        TURNOVER,
        SHOT_CLOCK_VIOLATION
    }

    OutcomeType outcomeType;
    Team offense; // model.Team that started with possession
    Team defense; // model.Team that started that possession on defense
    int pointsScored;
    double possessionLength;

    public PossessionResult(OutcomeType outcomeType, Team offense, Team defense, int pointsScored, double possessionLength) {
        this.outcomeType = outcomeType;
        this.offense = offense;
        this.defense = defense;
        this.pointsScored = pointsScored;
        this.possessionLength = possessionLength;
    }

    public double calculatePossessionLength() {
        return BoundedNormalDistribution.generateBoundedNormalDoubleInt(Config.BASE_AVERAGE_POSSESSION_SECONDS, 5, 0, 24);
    }

    // Getters
    public OutcomeType getOutcomeType() {
        return outcomeType;
    }

    public Team getOffense() {
        return this.offense;
    }

    public Team getDefense() {
        return this.defense;
    }

    public int getPointsScored() {
        return this.pointsScored;
    }

    public double getPossessionLength() {
        return this.possessionLength;
    }

    public boolean doesPossessionChange() {
        switch (outcomeType) {
            // Outcome types which result in possession changes
            case STEAL:
            case SHOT_MADE:
            case DEFENSIVE_REBOUND:
            case TURNOVER:
            case SHOT_OUT_OF_BOUNDS:
            case PASS_OUT_OF_BOUNDS:
            case SHOT_CLOCK_VIOLATION:
                return true;
            // Outcome type which do not result in possession changes
            case NON_SHOOTING_FOUL:
            case SHOOTING_FOUL:
            case OFFENSIVE_REBOUND:
            case PASS_DEFLECTED_OUT_OF_BOUNDS:
                return false;
            default:
                return true;
        }
    }

    public Team getNextPossessionTeam() {
        return doesPossessionChange() ? defense : offense;
    }
}
