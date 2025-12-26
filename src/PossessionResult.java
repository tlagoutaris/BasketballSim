public class PossessionResult {
    public enum OutcomeType {
        STEAL,
        NON_SHOOTING_FOUL,
        SHOOTING_FOUL,
        SHOT_MADE,
        SHOT_OUT_OF_BOUNDS,
        OFFENSIVE_REBOUND,
        DEFENSIVE_REBOUND,
        TURNOVER
    }

    OutcomeType outcomeType;
    Team offense; // Team that started with possession
    Team defense; // Team that started that possession on defense
    int pointsScored;
    double possessionLength;

    PossessionResult(OutcomeType outcomeType, Team offense, Team defense, int pointsScored) {
        this.outcomeType = outcomeType;
        this.offense = offense;
        this.defense = defense;
        this.pointsScored = pointsScored;
        this.possessionLength = calculatePossessionLength();
    }

    double calculatePossessionLength() {
        return BoundedNormalDistribution.generateBoundedNormalDoubleInt(Config.BASE_AVERAGE_POSSESSION_SECONDS, 5, 0, 24);
    }

    // Getters
    public OutcomeType getOutcomeType() {
        return outcomeType;
    }

    Team getOffense() {
        return this.offense;
    }

    Team getDefense() {
        return this.defense;
    }

    int getPointsScored() {
        return this.pointsScored;
    }

    double getPossessionLength() {
        return this.possessionLength;
    }

    boolean doesPossessionChange() {
        switch (outcomeType) {
            // Outcome types which result in possession changes
            case STEAL:
            case SHOT_MADE:
            case DEFENSIVE_REBOUND:
            case TURNOVER:
            case SHOT_OUT_OF_BOUNDS:
                return true;
            // Outcome type which do not result in possession changes
            case NON_SHOOTING_FOUL:
            case SHOOTING_FOUL:
            case OFFENSIVE_REBOUND:
                return false;
            default:
                return true;
        }
    }

    Team getNextPossessionTeam() {
        return doesPossessionChange() ? defense : offense;
    }
}
