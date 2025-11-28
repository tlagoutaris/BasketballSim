public class PlayerStatistics {
    Player player;

    // Totals
    int gamesTotal;
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

    public PlayerStatistics(Player player) {
        this.player = player;

        this.gamesTotal = 0;
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

    public void recordShotAttempt(ShotResult shot) {
        int points = 0;

        if (shot.shotType.equals("2PT")) {
            if (shot.isMade) {
                this.twoPointAttemptsTotal++;
                this.twoPointMakesTotal++;
                points += 2;
            } else if (!shot.drewFoul() && !shot.isMade()) {
                this.twoPointAttemptsTotal++;
            }
        }

        else if (shot.shotType.equals("3PT")) {
            if (shot.isMade) {
                this.threePointAttemptsTotal++;
                this.threePointMakesTotal++;
                points += 3;
            } else if (!shot.drewFoul() && !shot.isMade()) {
                this.threePointAttemptsTotal++;
            }
        }

        this.pointsTotal += points;
    }

    public void recordFreeThrowAttempt(FreeThrowResult freeThrow) {
        this.freeThrowAttemptsTotal += freeThrow.freeThrowAttempts;
        this.freeThrowMakesTotal += freeThrow.freeThrowsMade;
    }

    public void recordStealAttempt(StealResult stealAttempt) {
        if (stealAttempt.stolen()) {
            this.stealsTotal++;
        }
    }

    public void recordFoul(ShotResult shot) {
        if (shot.drewFoul()) {
            this.foulsTotal++;
        }
    }

    public void recordFoul(StealResult shot) {
        if (shot.hasFoul()) {
            this.foulsTotal++;
        }
    }

    public void recordTurnover() {
        this.turnoversTotal++;
    }

    public void calculateAverages() {
        System.out.println("\n" + this.player.getFullName() + " statistics: ");

        this.pointsPerGame = (double) pointsTotal / gamesTotal;
        this.twoPointAttemptsPerGame = (double) twoPointAttemptsTotal / gamesTotal;
        this.twoPointMakesPerGame = (double) twoPointMakesTotal / gamesTotal;
        this.threePointAttemptsPerGame = (double) threePointAttemptsTotal / gamesTotal;
        this.threePointMakesPerGame = (double) threePointMakesTotal / gamesTotal;
        this.freeThrowAttemptsPerGame = (double) freeThrowAttemptsTotal / gamesTotal;
        this.freeThrowMakesPerGame = (double) freeThrowMakesTotal / gamesTotal;
        this.attemptsPerGame = twoPointAttemptsPerGame + threePointAttemptsPerGame;
        this.makesPerGame = twoPointMakesPerGame + threePointMakesPerGame;
        this.turnoversPerGame = (double) this.turnoversTotal / gamesTotal;
        this.stealsPerGame = (double) this.stealsTotal / gamesTotal;
        this.foulsPerGame = (double) this.foulsTotal / gamesTotal;

        System.out.printf(
                "\nPTS/g: %.2f\nTOV/g: %.2f\nSTL/g: %.2f\nFouls/g: %.2f\nFG%%: %.2f\n3P%%: %.2f\nFT%%: %.2f\n",
                pointsPerGame,
                turnoversPerGame,
                stealsPerGame,
                foulsPerGame,
                (makesPerGame / attemptsPerGame) * 100,
                (threePointMakesPerGame / threePointAttemptsPerGame) * 100,
                (freeThrowMakesPerGame / freeThrowAttemptsPerGame) * 100
        );
    }
}
