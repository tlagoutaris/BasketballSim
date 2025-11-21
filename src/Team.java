public class Team {
    String city;
    String team;
    int score;
    double twoPointPercentage;
    double threePointPercentage;
    double twoPointTendency;

    // Totals
    int gamesPlayed;
    int pointsTotal;
    int twoPointAttemptsTotal;
    int threePointAttemptsTotal;
    int twoPointMakesTotal;
    int threePointMakesTotal;
    int wins;
    int losses;

    // Averages
    double pointsPerGame;
    double twoPointAttemptsPerGame;
    double threePointAttemptsPerGame;
    double twoPointMakesPerGame;
    double threePointMakesPerGame;
    double attemptsPerGame;
    double makesPerGame;

    public Team(String city, String team, double twoPointPercentage, double threePointPercentage, double twoPointTendency) {
        this.city = city;
        this.team = team;
        this.twoPointPercentage = twoPointPercentage;
        this.threePointPercentage = threePointPercentage;
        this.twoPointTendency = twoPointTendency;
        score = 0;
        gamesPlayed = 0;
        pointsTotal = 0;
        twoPointAttemptsTotal = 0;
        threePointAttemptsTotal = 0;
        twoPointMakesTotal = 0;
        threePointMakesTotal = 0;

        wins = 0;
        losses = 0;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getFullName() {
        return this.city + " " + this.team;
    }

    public int getGamesPlayed() {
        return this.gamesPlayed;
    }

    public int getPointsTotal() {
        return this.pointsTotal;
    }

    public int getTwoPointAttemptsTotal() {
        return this.twoPointAttemptsTotal;
    }

    public int getThreePointAttemptsTotal() {
        return this.threePointAttemptsTotal;
    }

    public int getTwoPointMakesTotal() {
        return this.twoPointMakesTotal;
    }

    public int getThreePointMakesTotal() {
        return this.threePointMakesTotal;
    }

    public void setGamesPlayed(int gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

    public void setPointsTotal(int pointsTotal) {
        this.pointsTotal = pointsTotal;
    }

    public void setTwoPointAttemptsTotal(int twoPointAttemptsTotal) {
        this.twoPointAttemptsTotal = twoPointAttemptsTotal;
    }

    public void setThreePointAttemptsTotal(int threePointAttemptsTotal) {
        this.threePointAttemptsTotal = threePointAttemptsTotal;
    }

    public void setTwoPointMakesTotal(int twoPointMakesTotal) {
        this.twoPointMakesTotal = twoPointMakesTotal;
    }

    public void setThreePointMakesTotal(int threePointMakesTotal) {
        this.threePointMakesTotal = threePointMakesTotal;
    }

    public void calculateAverages() {
        System.out.println("\n" + getFullName() + " statistics: ");

        this.pointsPerGame = (double) pointsTotal / gamesPlayed;
        this.twoPointAttemptsPerGame = (double) twoPointAttemptsTotal / gamesPlayed;
        this.threePointAttemptsPerGame = (double) threePointAttemptsTotal / gamesPlayed;
        this.twoPointMakesPerGame = (double) twoPointMakesTotal / gamesPlayed;
        this.threePointMakesPerGame = (double) threePointMakesTotal / gamesPlayed;
        this.attemptsPerGame = twoPointAttemptsPerGame + threePointAttemptsPerGame;
        this.makesPerGame = twoPointMakesPerGame + threePointMakesPerGame;

        System.out.printf(
                "\nPPG: %.2f\nFG%%: %.2f\n3P%%: %.2f\nWins: %d\nLosses: %d",
                pointsPerGame,
                (makesPerGame / attemptsPerGame) * 100,
                (threePointMakesPerGame / threePointAttemptsPerGame) * 100,
                wins,
                losses
        );
    }
}
