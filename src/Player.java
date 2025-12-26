import java.security.SecureRandom;

public class Player {
    // Personal information
    String firstName;
    String lastName;

    Team currentTeam; // make use of this later

    // Attributes
    // offense
    int twoPointOffense;
    int threePointOffense;
    int freeThrow;
    int ballControl;

    // defense
    int twoPointDefense;
    int threePointDefense;
    int steal;
    int offensiveRebounding;
    int defensiveRebounding;

    // Tendencies
    double twoPointTendency;
    double threePointTendency;
    double stealAttemptTendency;
    double foulTendency;
    double offensiveReboundingTendency;
    double defensiveReboundingTendency;

    PlayerStatistics statistics;

    public Player(Team currentTeam) {
        this.currentTeam = currentTeam;

        // Personal information
        firstName = RandomName.generateRandomFirstName();
        lastName = RandomName.generateRandomLastName();

        this.statistics = new PlayerStatistics(this);

        generateAttributes();
        generateTendencies();
    }

    void generateAttributes() {
        // Offense
        this.twoPointOffense = randomAttribute();
        this.threePointOffense = randomAttribute();
        this.freeThrow = randomAttribute();
        this.ballControl = randomAttribute();

        // Defense
        this.twoPointDefense = randomAttribute();
        this.threePointDefense = randomAttribute();
        this.steal = randomAttribute();
        this.offensiveRebounding = randomAttribute();
        this.defensiveRebounding = randomAttribute();
    }

    void generateTendencies() {
        // Offense
        this.twoPointTendency = BoundedNormalDistribution.generateBoundedNormalDoubleInt(Config.BASE_TWO_POINT_ATTEMPT_TENDENCY, Config.BASE_TENDENCY_STDDEV, Config.LOWER_BOUND, Config.UPPER_BOUND);
        this.threePointTendency = 1 - this.twoPointTendency;

        // Defense
        this.stealAttemptTendency = BoundedNormalDistribution.generateBoundedNormalDoubleInt(Config.BASE_STEAL_ATTEMPT_TENDENCY, Config.BASE_TENDENCY_STDDEV, Config.LOWER_BOUND, Config.UPPER_BOUND);
        this.foulTendency = BoundedNormalDistribution.generateBoundedNormalDoubleInt(Config.BASE_FOUL_TENDENCY, Config.BASE_TENDENCY_STDDEV, Config.LOWER_BOUND, Config.UPPER_BOUND);
        this.offensiveReboundingTendency = BoundedNormalDistribution.generateBoundedNormalDoubleInt(Config.BASE_OFFENSIVE_REBOUND_TENDENCY, Config.BASE_TENDENCY_STDDEV, Config.LOWER_BOUND, Config.UPPER_BOUND);
        this.defensiveReboundingTendency = BoundedNormalDistribution.generateBoundedNormalDoubleInt(Config.BASE_DEFENSIVE_REBOUND_TENDENCY, Config.BASE_TENDENCY_STDDEV, Config.LOWER_BOUND, Config.UPPER_BOUND);
    }

    int randomAttribute() {
        return BoundedNormalDistribution.generateBoundedNormalDoubleInt(Config.BASE_ATTRIBUTE_MEAN, Config.BASE_ATTRIBUTE_STDDEV, Config.LOWER_BOUND, Config.UPPER_BOUND);
    }

    // Getters & Setters
    public PlayerStatistics getStatistics() {
        return this.statistics;
    }

    public Team getCurrentTeam() {
        return this.currentTeam;
    }

    String getFullName() {
        return this.firstName + " " + this.lastName;
    }

    String getStats() {
                // Offense
        return "\n2PT Offense: " + this.twoPointOffense +
                "\n3PT Offense: " + this.threePointOffense +
                "\nFree Throw: " + this.freeThrow +
                "\nBall Control: " + this.ballControl +
                // Defense
                "\n2PT Defense: " + this.twoPointDefense +
                "\n3PT Defense: " + this.threePointDefense +
                "\nSteal: " + this.steal
                + "\n";
    }
}
