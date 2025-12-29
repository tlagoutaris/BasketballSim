package model;

import util.BoundedNormalDistribution;
import util.RandomName;
import config.Config;

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

    public void generateAttributes() {
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

    public void generateTendencies() {
        // Offense
        this.twoPointTendency = BoundedNormalDistribution.generateBoundedNormalDoubleInt(Config.BASE_TWO_POINT_ATTEMPT_TENDENCY, Config.BASE_TENDENCY_STDDEV, Config.LOWER_BOUND, Config.UPPER_BOUND);
        this.threePointTendency = 1 - this.twoPointTendency;

        // Defense
        this.stealAttemptTendency = BoundedNormalDistribution.generateBoundedNormalDoubleInt(Config.BASE_STEAL_ATTEMPT_TENDENCY, Config.BASE_TENDENCY_STDDEV, Config.LOWER_BOUND, Config.UPPER_BOUND);
        this.foulTendency = BoundedNormalDistribution.generateBoundedNormalDoubleInt(Config.BASE_FOUL_TENDENCY, Config.BASE_TENDENCY_STDDEV, Config.LOWER_BOUND, Config.UPPER_BOUND);
        this.offensiveReboundingTendency = BoundedNormalDistribution.generateBoundedNormalDoubleInt(Config.BASE_OFFENSIVE_REBOUND_TENDENCY, Config.BASE_TENDENCY_STDDEV, Config.LOWER_BOUND, Config.UPPER_BOUND);
        this.defensiveReboundingTendency = BoundedNormalDistribution.generateBoundedNormalDoubleInt(Config.BASE_DEFENSIVE_REBOUND_TENDENCY, Config.BASE_TENDENCY_STDDEV, Config.LOWER_BOUND, Config.UPPER_BOUND);
    }

    public int randomAttribute() {
        return BoundedNormalDistribution.generateBoundedNormalDoubleInt(Config.BASE_ATTRIBUTE_MEAN, Config.BASE_ATTRIBUTE_STDDEV, Config.LOWER_BOUND, Config.UPPER_BOUND);
    }

    // Getters & Setters
    public PlayerStatistics getStatistics() {
        return this.statistics;
    }

    public Team getCurrentTeam() {
        return this.currentTeam;
    }

    public String getFullName() {
        return this.firstName + " " + this.lastName;
    }

    // Attributes
    public int getTwoPointOffense() {
        return this.twoPointOffense;
    }

    public int getThreePointOffense() {
        return this.threePointOffense;
    }

    public int getFreeThrow() {
        return this.freeThrow;
    }

    public int getBallControl() {
        return this.ballControl;
    }

    public int getTwoPointDefense() {
        return this.twoPointDefense;
    }

    public int getThreePointDefense() {
        return this.threePointDefense;
    }

    public int getSteal() {
        return this.steal;
    }

    // Tendencies
    public double getTwoPointTendency() {
        return this.twoPointTendency;
    }

    public double getThreePointTendency() {
        return this.threePointTendency;
    }

    public double getStealAttemptTendency() {
        return this.stealAttemptTendency;
    }

    public double getFoulTendency() {
        return this.foulTendency;
    }

    public double getOffensiveReboundingTendency() {
        return this.offensiveReboundingTendency;
    }

    public double getDefensiveReboundingTendency() {
        return this.defensiveReboundingTendency;
    }

    public int getOffensiveRebounding() {
        return this.offensiveRebounding;
    }

    public int getDefensiveRebounding() {
        return this.defensiveRebounding;
    }

    public String getStats() {
                // Offense
        return "\n2PT Offense: " + this.twoPointOffense +
                "\n3PT Offense: " + this.threePointOffense +
                "\nFree Throw: " + this.freeThrow +
                "\nBall Control: " + this.ballControl +
                // Defense
                "\n2PT Defense: " + this.twoPointDefense +
                "\n3PT Defense: " + this.threePointDefense +
                "\nSteal: " + this.steal +
                "\nOffensive Rebounding: " + this.offensiveRebounding +
                "\nDefensive Rebounding: " + this.defensiveRebounding
                + "\n";
    }
}
