import java.security.SecureRandom;

public class Player {
    SecureRandom r = new SecureRandom();

    String firstName;
    String lastName;

    // Attributes
    int twoPointOffense;
    int threePointOffense;
    int twoPointDefense;
    int threePointDefense;

    // Tendencies
    double twoPointTendency;
    double threePointTendency;

    // Statistic Totals
    int gamesPlayed;
    int pointsTotal;
    int twoPointAttemptsTotal;
    int threePointAttemptsTotal;
    int twoPointMakesTotal;
    int threePointMakesTotal;

    public Player() {
        generateStats();
        firstName = RandomName.generateRandomFirstName();
        lastName = RandomName.generateRandomLastName();
        this.gamesPlayed = 0;
        this.pointsTotal = 0;
        this.twoPointAttemptsTotal = 0;
        this.threePointAttemptsTotal = 0;
        this.twoPointMakesTotal = 0;
        this.threePointMakesTotal = 0;
    }

    String[] shoot(Player defender) {
        String shotType;
        String outcome;
        int points = 0;

        double shotTypeChance = r.nextDouble(0, 100.0);
        double shotSuccessChance = r.nextDouble(0, 100.0);

        if (shotTypeChance <= this.twoPointTendency) {

            // 2PT
            shotType = "2PT";
            this.setTwoPointAttemptsTotal(this.getTwoPointAttemptsTotal() + 1);

            // Calculate user's offense against opponent's defense
            int attributeDifference = defender.twoPointDefense - this.twoPointOffense;
            double successThreshold = BoundedNormalDistribution.generateBoundedNormal(54 + (attributeDifference * 0.5), 10, 0, 100);

            if (shotSuccessChance <= successThreshold) {
                this.setTwoPointMakesTotal(this.getTwoPointMakesTotal() + 1);
                points = 2;
                outcome = "Success";
            } else {
                outcome = "Fail";
            }

        } else {
            // 3PT
            shotType = "3PT";
            this.setThreePointAttemptsTotal(this.getThreePointAttemptsTotal() + 1);

            int attributeDifference = defender.threePointDefense - this.threePointDefense;
            double successThreshold = BoundedNormalDistribution.generateBoundedNormal(36 + (attributeDifference * 0.5), 10, 0, 100);

            if (shotSuccessChance <= successThreshold) {
                this.setThreePointMakesTotal(this.getThreePointMakesTotal() + 1);
                points = 3;
                outcome = "Success";
            } else {
                outcome = "Fail";
            }

        }

        this.setPointsTotal(this.getPointsTotal() + points);

        return new String[] {shotType, outcome, String.valueOf(points)};
    }

    void generateStats() {
        this.twoPointOffense = BoundedNormalDistribution.generateBoundedNormal(50, 10, 0, 100);
        this.twoPointDefense = BoundedNormalDistribution.generateBoundedNormal(50, 10, 0, 100);
        this.threePointOffense = BoundedNormalDistribution.generateBoundedNormal(50, 10, 0, 100);
        this.threePointDefense = BoundedNormalDistribution.generateBoundedNormal(50, 10, 0, 100);
        this.twoPointTendency = BoundedNormalDistribution.generateBoundedNormal(50, 10, 0, 100);
        this.threePointTendency = 1 - this.twoPointTendency;
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

    String getFullName() {
        return this.firstName + " " + this.lastName;
    }

    String getStats() {
        return "\n2PT Offense: " + this.twoPointOffense + "\n" + "3PT Offense: " + this.threePointDefense + "\n2PT Defense: " + this.twoPointDefense + "\n3PT Defense: " + this.threePointDefense + "\n";
    }
}
