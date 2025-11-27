import java.security.SecureRandom;
import java.util.ArrayList;

public class Player {
    SecureRandom r = new SecureRandom();

    // Personal information
    String firstName;
    String lastName;

    Team currentTeam; // make use of this later

    // Attributes
    int twoPointOffense;
    int threePointOffense;
    int freeThrow;
    int twoPointDefense;
    int threePointDefense;
    int ballControl;
    int steal;

    // Tendencies
    double twoPointTendency;
    double threePointTendency;
    double stealAttemptTendency;
    double foulTendency;

    // Statistic totals
    int gamesPlayed;
    int pointsTotal;
    int twoPointAttemptsTotal;
    int threePointAttemptsTotal;
    int freeThrowAttemptsTotal;
    int twoPointMakesTotal;
    int threePointMakesTotal;
    int freeThrowMakesTotal;
    int turnoversTotal;
    int stealsTotal;
    int foulsTotal;

    public Player(Team currentTeam) {
        this.currentTeam = currentTeam;

        // Personal information
        firstName = RandomName.generateRandomFirstName();
        lastName = RandomName.generateRandomLastName();

        // Attributes
        generateAttributes();
        // Statistic totals
        initializeStatistics();
    }

    String[] shoot(Player defender) {
        String shotType;
        String outcome;
        int points = 0;

        double shotTypeChance = r.nextDouble(Config.LOWER_BOUND, Config.UPPER_BOUND);
        double shotSuccessChance = r.nextDouble(Config.LOWER_BOUND, Config.UPPER_BOUND);
        double foulChance = r.nextDouble(Config.LOWER_BOUND, Config.UPPER_BOUND);

        if (shotTypeChance <= this.twoPointTendency) {

            // 2PT
            shotType = "2PT";
            this.setTwoPointAttemptsTotal(this.getTwoPointAttemptsTotal() + 1);

            // Calculate user's offense against opponent's defense
            int attributeDifference = defender.twoPointDefense - this.twoPointOffense;
            double successThreshold = BoundedNormalDistribution.generateBoundedNormalDoubleInt(Config.BASE_TWO_POINT_PERCENTAGE + (attributeDifference * Config.ATTRIBUTE_DIFFERENCE_MULTIPLIER), 10, Config.LOWER_BOUND, Config.UPPER_BOUND);

            if (shotSuccessChance <= successThreshold) {
                this.setTwoPointMakesTotal(this.getTwoPointMakesTotal() + 1);
                points = 2;

                // Check for foul
                if (foulChance <= defender.foulTendency) {
                    // Made and-1 2pt shot
                    points += this.shootFreeThrows(1);
                }

                outcome = "Success";
            } else {

                outcome = "Fail"; // fail before foul check because if you miss and get fouled, no shot attempt

                // Check for foul
                if (foulChance <= defender.foulTendency) {
                    // Missed 2pt shot but got fouled
                    this.setTwoPointAttemptsTotal(this.getTwoPointAttemptsTotal() - 1);
                    points += this.shootFreeThrows(2);
                    outcome = "Fouled";
                }
            }

        } else {
            // 3PT
            shotType = "3PT";
            this.setThreePointAttemptsTotal(this.getThreePointAttemptsTotal() + 1);

            int attributeDifference = defender.threePointDefense - this.threePointOffense;
            double successThreshold = BoundedNormalDistribution.generateBoundedNormalDoubleInt(Config.BASE_THREE_POINT_PERCENTAGE + (attributeDifference * Config.ATTRIBUTE_DIFFERENCE_MULTIPLIER), 10, Config.LOWER_BOUND, Config.UPPER_BOUND);

            if (shotSuccessChance <= successThreshold) {
                this.setThreePointMakesTotal(this.getThreePointMakesTotal() + 1);
                points = 3;

                // Check for foul
                if (foulChance <= defender.foulTendency) {
                    // Made and-1 3pt shot
                    points += this.shootFreeThrows(1);
                }

                // Check for foul
                outcome = "Success";
            } else {

                outcome = "Fail";

                // Check for foul
                if (foulChance <= defender.foulTendency) {
                    // Missed 3pt shot but got fouled
                    this.setThreePointAttemptsTotal(this.getThreePointAttemptsTotal() - 1);
                    points += this.shootFreeThrows(3);
                    outcome = "Fouled";
                }
            }

        }

        this.setPointsTotal(this.getPointsTotal() + points);

        return new String[] {shotType, outcome, String.valueOf(points)};
    }

    int shootFreeThrows(int numFreeThrows) {
        int points = 0;

        for (int i = 0; i < numFreeThrows; i++) {

            this.setFreeThrowAttemptsTotal(this.getFreeThrowAttemptsTotal() + 1);
            this.currentTeam.setFreeThrowAttemptsTotal(this.currentTeam.getFreeThrowAttemptsTotal() + 1);

            double freeThrowSuccessChance = r.nextDouble(Config.LOWER_BOUND, Config.UPPER_BOUND);
            if (freeThrowSuccessChance <= this.freeThrow) {
                // Made free throw
                this.setFreeThrowMakesTotal(this.getFreeThrowMakesTotal() + 1);
                this.currentTeam.setFreeThrowMakesTotal(this.currentTeam.getFreeThrowMakesTotal() + 1);
                points++;
            }
        }

        return points;
    }

    String attemptSteal(Player playerWithBall) {
        String outcome = "No attempt";

        double stealAttemptChance = r.nextDouble(Config.LOWER_BOUND, Config.UPPER_BOUND);
        double stealSuccessChance = r.nextDouble(Config.LOWER_BOUND, Config.UPPER_BOUND);

        // either you steal it or you don't
        if (stealAttemptChance <= this.stealAttemptTendency) {
            // you attempt the steal
            int attributeDifference = playerWithBall.ballControl - this.steal;
            double successThreshold = BoundedNormalDistribution.generateBoundedNormalDoubleInt(Config.BASE_STEAL_SUCCESS_CHANCE + (attributeDifference * Config.ATTRIBUTE_DIFFERENCE_MULTIPLIER), 10, Config.LOWER_BOUND, Config.UPPER_BOUND);

            if (stealSuccessChance <= successThreshold) {
                // if steal happens
                outcome = "Steal";
                this.setStealsTotal(this.getStealsTotal() + 1);
                playerWithBall.setTurnoversTotal(playerWithBall.getTurnoversTotal() + 1);

            } else {
                // Foul tendency
                double foulChance = BoundedNormalDistribution.generateBoundedNormalDoubleInt(Config.BASE_FOUL_SUCCESS_CHANCE + (attributeDifference * Config.ATTRIBUTE_DIFFERENCE_MULTIPLIER), 10, Config.LOWER_BOUND, Config.UPPER_BOUND);
                if (foulChance <= this.foulTendency) {
                    outcome = "Foul";
                    this.setFoulsTotal(this.getFoulsTotal() + 1);
                }

                // No steal
                outcome = "No steal";
            }
        }

        return outcome;
    }

    void generateAttributes() {
        // Attributes
        this.twoPointOffense = randomAttribute();
        this.threePointOffense = randomAttribute();
        this.freeThrow = randomAttribute();
        this.twoPointDefense = randomAttribute();
        this.threePointDefense = randomAttribute();
        this.ballControl = randomAttribute();
        this.steal = randomAttribute();

        // Tendencies
        this.twoPointTendency = BoundedNormalDistribution.generateBoundedNormalDoubleInt(Config.BASE_TWO_POINT_ATTEMPT_TENDENCY, Config.BASE_TENDENCY_STDDEV, Config.LOWER_BOUND, Config.UPPER_BOUND);
        this.threePointTendency = 1 - this.twoPointTendency;
        this.stealAttemptTendency = BoundedNormalDistribution.generateBoundedNormalDoubleInt(Config.BASE_STEAL_ATTEMPT_TENDENCY, Config.BASE_TENDENCY_STDDEV, Config.LOWER_BOUND, Config.UPPER_BOUND);
        this.foulTendency = BoundedNormalDistribution.generateBoundedNormalDoubleInt(Config.BASE_FOUL_TENDENCY, Config.BASE_TENDENCY_STDDEV, Config.LOWER_BOUND, Config.UPPER_BOUND);
    }

    int randomAttribute() {
        return BoundedNormalDistribution.generateBoundedNormalDoubleInt(Config.BASE_ATTRIBUTE_MEAN, Config.BASE_ATTRIBUTE_STDDEV, Config.LOWER_BOUND, Config.UPPER_BOUND);
    }

    void initializeStatistics() {
        this.gamesPlayed = 0;
        this.pointsTotal = 0;
        this.twoPointAttemptsTotal = 0;
        this.twoPointMakesTotal = 0;
        this.threePointAttemptsTotal = 0;
        this.threePointMakesTotal = 0;
        this.freeThrowAttemptsTotal = 0;
        this.freeThrowMakesTotal = 0;
        this.turnoversTotal = 0;
        this.stealsTotal = 0;
        this.foulsTotal = 0;
    }

    // Getters & Setters
    int getGamesPlayed() {
        return this.gamesPlayed;
    }

    int getPointsTotal() {
        return this.pointsTotal;
    }

    int getTwoPointAttemptsTotal() {
        return this.twoPointAttemptsTotal;
    }

    int getThreePointAttemptsTotal() {
        return this.threePointAttemptsTotal;
    }

    int getTwoPointMakesTotal() {
        return this.twoPointMakesTotal;
    }

    int getThreePointMakesTotal() {
        return this.threePointMakesTotal;
    }

    int getTurnoversTotal() {
        return this.turnoversTotal;
    }

    int getStealsTotal() {
        return this.stealsTotal;
    }
    int getFoulsTotal() {
        return this.foulsTotal;
    }

    int getFreeThrowAttemptsTotal() {
        return this.freeThrowAttemptsTotal;
    }

    int getFreeThrowMakesTotal() {
        return this.freeThrowMakesTotal;
    }

    void setGamesPlayed(int gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

    void setPointsTotal(int pointsTotal) {
        this.pointsTotal = pointsTotal;
    }

    void setTwoPointAttemptsTotal(int twoPointAttemptsTotal) {
        this.twoPointAttemptsTotal = twoPointAttemptsTotal;
    }

    void setThreePointAttemptsTotal(int threePointAttemptsTotal) {
        this.threePointAttemptsTotal = threePointAttemptsTotal;
    }

    void setTwoPointMakesTotal(int twoPointMakesTotal) {
        this.twoPointMakesTotal = twoPointMakesTotal;
    }

    void setThreePointMakesTotal(int threePointMakesTotal) {
        this.threePointMakesTotal = threePointMakesTotal;
    }

    void setTurnoversTotal(int turnoversTotal) {
        this.turnoversTotal = turnoversTotal;
    }

    void setStealsTotal(int stealsTotal) {
        this.stealsTotal = stealsTotal;
    }
    void setFoulsTotal(int foulsTotal) {
        this.foulsTotal = foulsTotal;
    }

    void setFreeThrowAttemptsTotal(int freeThrowAttemptsTotal) {
        this.freeThrowAttemptsTotal = freeThrowAttemptsTotal;
    }

    void setFreeThrowMakesTotal(int freeThrowMakesTotal) {
        this.freeThrowMakesTotal = freeThrowMakesTotal;
    }

    String getFullName() {
        return this.firstName + " " + this.lastName;
    }

    String getStats() {
        return "\n2PT Offense: " + this.twoPointOffense +
                "\n3PT Offense: " + this.threePointOffense +
                "\nFree Throw: " + this.freeThrow +
                "\nBall Control: " + this.ballControl +
                "\n2PT Defense: " + this.twoPointDefense +
                "\n3PT Defense: " + this.threePointDefense +
                "\nSteal: " + this.steal
                + "\n";
    }
}
