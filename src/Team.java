import java.security.SecureRandom;

public class Team {
    SecureRandom r = new SecureRandom();

    String city;
    String team;
    int score;
    double twoPointPercentage;
    double threePointPercentage;
    double twoPointTendency;

    // Players
    Player[] roster = new Player[5];

    // Totals
    int gamesPlayed;
    int pointsTotal;
    int twoPointAttemptsTotal;
    int threePointAttemptsTotal;
    int twoPointMakesTotal;
    int threePointMakesTotal;
    int turnoversTotal;
    int stealsTotal;

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
    double turnoversPerGame;
    double stealsPerGame;

    public Team(String city, String team) {
        // Team information
        this.city = city;
        this.team = team;

        // Tendencies (unused)
        this.twoPointPercentage = twoPointPercentage;
        this.threePointPercentage = threePointPercentage;
        this.twoPointTendency = twoPointTendency;

        // Statistic Totals
        score = 0;
        gamesPlayed = 0;
        pointsTotal = 0;
        twoPointAttemptsTotal = 0;
        threePointAttemptsTotal = 0;
        twoPointMakesTotal = 0;
        threePointMakesTotal = 0;
        stealsTotal = 0;
        turnoversTotal = 0;

        wins = 0;
        losses = 0;

        generateTeam();
    }

    void simulateShot(Team opponent) {
        Player shooter = this.roster[r.nextInt(0, this.roster.length)];
        Player defender = opponent.roster[r.nextInt(0, opponent.roster.length)];

        if (defender.attemptSteal(shooter).equals("Steal")) {
            this.setTurnoversTotal(this.getTurnoversTotal() + 1);
            opponent.setStealsTotal(opponent.getStealsTotal() + 1);
            return; // ends possession before the shot attempt
        }

        String[] result = shooter.shoot(defender);

        String shotType = result[0];
        String outcome = result[1];
        int points = Integer.parseInt(result[2]);

        this.score += points;

        if (shotType.equals("2PT")) {
            this.setTwoPointAttemptsTotal(this.getTwoPointAttemptsTotal() + 1);
            if (outcome.equals("Success")) {
                this.setTwoPointMakesTotal(this.getTwoPointMakesTotal() + 1);
            }
        }

        else {
            this.setThreePointAttemptsTotal(this.getThreePointAttemptsTotal() + 1);
            if (outcome.equals("Success")) {
                this.setThreePointMakesTotal(this.getThreePointMakesTotal() + 1);
            }
        }
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

    public int getTurnoversTotal() {
        return this.turnoversTotal;
    }

    public int getStealsTotal() {
        return this.stealsTotal;
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

    public void setTurnoversTotal(int turnoversTotal) {
        this.turnoversTotal = turnoversTotal;
    }

    public void setStealsTotal(int stealsTotal) {
        this.stealsTotal = stealsTotal;
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
        this.turnoversPerGame = (double) this.turnoversTotal / gamesPlayed;
        this.stealsPerGame = (double) this.stealsTotal / gamesPlayed;

        System.out.printf(
                "\nPTS/g: %.2f\nTOV/g: %.2f\nSTL/g: %.2f\nFG%%: %.2f\n3P%%: %.2f\nWins: %d\nLosses: %d\n",
                pointsPerGame,
                turnoversPerGame,
                stealsPerGame,
                (makesPerGame / attemptsPerGame) * 100,
                (threePointMakesPerGame / threePointAttemptsPerGame) * 100,
                wins,
                losses
        );
    }

    void generateTeam() {
        for (int i = 0; i < this.roster.length; i++) {
            this.roster[i] = new Player();
        }
    }

    void printRoster() {
        System.out.printf("\n%s %s Roster: %n", this.city, this.team);

        for (Player p : roster) {
            System.out.print(p.getFullName());
            System.out.println(p.getStats());
        }
    }
}
