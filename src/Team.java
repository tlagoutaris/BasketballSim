import java.security.SecureRandom;

public class Team {
    SecureRandom r = new SecureRandom();

    String city;
    String team;
    int score;

    // Players
    Player[] roster = new Player[5];

    // Totals
    int gamesPlayed;
    int pointsTotal;
    int twoPointAttemptsTotal;
    int twoPointMakesTotal;
    int threePointAttemptsTotal;
    int threePointMakesTotal;
    int freeThrowAttemptsTotal;
    int freeThrowMakesTotal;
    int turnoversTotal;
    int stealsTotal;
    int foulsTotal;

    int wins;
    int losses;

    // Averages
    double pointsPerGame;
    double twoPointAttemptsPerGame;
    double twoPointMakesPerGame;
    double threePointAttemptsPerGame;
    double threePointMakesPerGame;
    double freeThrowAttemptsPerGame;
    double freeThrowMakesPerGame;
    double attemptsPerGame;
    double makesPerGame;
    double turnoversPerGame;
    double stealsPerGame;
    double foulsPerGame;

    public Team(String city, String team) {
        // Team information
        this.city = city;
        this.team = team;

        // Statistic Totals
        initializeStatistics();
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

            else if (outcome.equals("Fouled")) {
                this.setTwoPointAttemptsTotal(this.getTwoPointAttemptsTotal() - 1);
                opponent.setFoulsTotal(opponent.getFoulsTotal() + 1);
            }
        }

        else if (shotType.equals("3PT")) {
            this.setThreePointAttemptsTotal(this.getThreePointAttemptsTotal() + 1);
            if (outcome.equals("Success")) {
                this.setThreePointMakesTotal(this.getThreePointMakesTotal() + 1);
            }

            else if (outcome.equals("Fouled")) {
                this.setThreePointAttemptsTotal(this.getThreePointAttemptsTotal() - 1);
                opponent.setFoulsTotal(opponent.getFoulsTotal() + 1);
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

    public int getTwoPointMakesTotal() {
        return this.twoPointMakesTotal;
    }

    public int getThreePointAttemptsTotal() {
        return this.threePointAttemptsTotal;
    }

    public int getThreePointMakesTotal() {
        return this.threePointMakesTotal;
    }

    public int getFreeThrowAttemptsTotal() {
        return this.freeThrowAttemptsTotal;
    }

    public int getFreeThrowMakesTotal() {
        return this.freeThrowMakesTotal;
    }

    public int getTurnoversTotal() {
        return this.turnoversTotal;
    }

    public int getStealsTotal() {
        return this.stealsTotal;
    }

    public int getFoulsTotal() {
        return this.foulsTotal;
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

    public void setTwoPointMakesTotal(int twoPointMakesTotal) {
        this.twoPointMakesTotal = twoPointMakesTotal;
    }

    public void setThreePointAttemptsTotal(int threePointAttemptsTotal) {
        this.threePointAttemptsTotal = threePointAttemptsTotal;
    }

    public void setThreePointMakesTotal(int threePointMakesTotal) {
        this.threePointMakesTotal = threePointMakesTotal;
    }

    public void setFreeThrowAttemptsTotal(int freeThrowAttemptsTotal) {
        this.freeThrowAttemptsTotal = freeThrowAttemptsTotal;
    }

    public void setFreeThrowMakesTotal(int freeThrowMakesTotal) {
        this.freeThrowMakesTotal = freeThrowMakesTotal;
    }

    public void setTurnoversTotal(int turnoversTotal) {
        this.turnoversTotal = turnoversTotal;
    }

    public void setStealsTotal(int stealsTotal) {
        this.stealsTotal = stealsTotal;
    }

    public void setFoulsTotal(int foulsTotal) {
        this.foulsTotal = foulsTotal;
    }

    public void calculateAverages() {
        System.out.println("\n" + getFullName() + " statistics: ");

        this.pointsPerGame = (double) pointsTotal / gamesPlayed;
        this.twoPointAttemptsPerGame = (double) twoPointAttemptsTotal / gamesPlayed;
        this.twoPointMakesPerGame = (double) twoPointMakesTotal / gamesPlayed;
        this.threePointAttemptsPerGame = (double) threePointAttemptsTotal / gamesPlayed;
        this.threePointMakesPerGame = (double) threePointMakesTotal / gamesPlayed;
        this.freeThrowAttemptsPerGame = (double) freeThrowAttemptsTotal / gamesPlayed;
        this.freeThrowMakesPerGame = (double) freeThrowMakesTotal / gamesPlayed;
        this.attemptsPerGame = twoPointAttemptsPerGame + threePointAttemptsPerGame;
        this.makesPerGame = twoPointMakesPerGame + threePointMakesPerGame;
        this.turnoversPerGame = (double) this.turnoversTotal / gamesPlayed;
        this.stealsPerGame = (double) this.stealsTotal / gamesPlayed;
        this.foulsPerGame = (double) this.foulsTotal / gamesPlayed;

        System.out.printf(
                "\nPTS/g: %.2f\nTOV/g: %.2f\nSTL/g: %.2f\nFouls/g: %.2f\nFG%%: %.2f\n3P%%: %.2f\nFT%%: %.2f\nWins: %d\nLosses: %d\n",
                pointsPerGame,
                turnoversPerGame,
                stealsPerGame,
                foulsPerGame,
                (makesPerGame / attemptsPerGame) * 100,
                (threePointMakesPerGame / threePointAttemptsPerGame) * 100,
                (freeThrowMakesPerGame / freeThrowAttemptsPerGame) * 100,
                wins,
                losses
        );
    }

    public void initializeStatistics() {
        // Player stats
        this.score = 0;
        this.gamesPlayed = 0;
        this.pointsTotal = 0;
        this.twoPointAttemptsTotal = 0;
        this.twoPointMakesTotal = 0;
        this.threePointAttemptsTotal = 0;
        this.threePointMakesTotal = 0;
        this.freeThrowAttemptsTotal = 0;
        this.freeThrowMakesTotal = 0;
        this.stealsTotal = 0;
        this.turnoversTotal = 0;
        this.foulsTotal = 0;

        // Team stats
        this.wins = 0;
        this.losses = 0;
    }

    void generateTeam() {
        for (int i = 0; i < this.roster.length; i++) {
            this.roster[i] = new Player(this);
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
