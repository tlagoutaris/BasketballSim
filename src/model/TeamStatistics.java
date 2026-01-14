package model;

import result.FoulEvent;
import result.FreeThrowEvent;
import result.ShotEvent;
import result.StealEvent;

public class TeamStatistics {
    Team team;

    // Totals
    int pointsTotal;
    int twoPointAttemptsTotal;
    int twoPointMakesTotal;
    int threePointAttemptsTotal;
    int threePointMakesTotal;
    int fieldGoalAttemptsTotal;
    int fieldGoalMakesTotal;
    int freeThrowAttemptsTotal;
    int freeThrowMakesTotal;
    int turnoversTotal;
    int stealsTotal;
    int foulsTotal;
    int reboundsTotal;
    int offensiveReboundsTotal;
    int defensiveReboundsTotal;

    // Averages
    double pointsPerGame;
    double twoPointAttemptsPerGame;
    double twoPointMakesPerGame;
    double threePointAttemptsPerGame;
    double threePointMakesPerGame;
    double fieldGoalAttemptsPerGame;
    double fieldGoalMakesPerGame;
    double freeThrowAttemptsPerGame;
    double freeThrowMakesPerGame;
    double attemptsPerGame;
    double makesPerGame;
    double turnoversPerGame;
    double stealsPerGame;
    double foulsPerGame;
    double reboundsPerGame;
    double offensiveReboundsPerGame;
    double defensiveReboundsPerGame;

    // model.Team Stats
    int gamesTotal;
    int winsTotal;
    int lossesTotal;

    public TeamStatistics(Team team) {
        this.team = team;

        initializeTotals();
        initializeAverages();
    }

    public void recordShotAttempt(ShotEvent shot) {
        if (shot.getShotType().equals("2PT")) {
            if (shot.isMade()) {
                this.twoPointAttemptsTotal++;
                this.twoPointMakesTotal++;
            } else if (!shot.drewFoul() && !shot.isMade()) {
                this.twoPointAttemptsTotal++;
            }
        }

        else if (shot.getShotType().equals("3PT")) {
            if (shot.isMade()) {
                this.threePointAttemptsTotal++;
                this.threePointMakesTotal++;
            } else if (!shot.drewFoul() && !shot.isMade()) {
                this.threePointAttemptsTotal++;
            }
        }

        this.pointsTotal += shot.getPoints();
    }

    public void recordFreeThrowAttempt(FreeThrowEvent freeThrow) {
        this.freeThrowAttemptsTotal += freeThrow.getFreeThrowAttempts();
        this.freeThrowMakesTotal += freeThrow.getFreeThrowsMade();
        this.pointsTotal += freeThrow.getFreeThrowsMade();
    }

    public void recordStealAttempt(StealEvent stealAttempt) {
        if (stealAttempt.stolen()) {
            this.stealsTotal++;
        }
    }

    public void recordFoul() {
        this.foulsTotal++;
    }

    public void recordRebound() {
        this.reboundsTotal++;
    }

    public void recordOffensiveRebound() {
        this.offensiveReboundsTotal++;
    }

    public void recordDefensiveRebound() {
        this.defensiveReboundsTotal++;
    }

    public void recordTurnover() {
        this.turnoversTotal++;
    }

    public void recordGamePlayed() {
        for (int i = 0; i < this.team.roster.length; i++) {
            this.team.roster[i].getStatistics().gamesTotal++;
        }
    }

    public void calculateAverages() {
        this.pointsPerGame = (double) pointsTotal / gamesTotal;
        this.twoPointAttemptsPerGame = (double) twoPointAttemptsTotal / gamesTotal;
        this.twoPointMakesPerGame = (double) twoPointMakesTotal / gamesTotal;
        this.threePointAttemptsPerGame = (double) threePointAttemptsTotal / gamesTotal;
        this.threePointMakesPerGame = (double) threePointMakesTotal / gamesTotal;
        this.fieldGoalMakesPerGame = (double) (twoPointMakesTotal + threePointMakesTotal) / gamesTotal;
        this.fieldGoalAttemptsPerGame = (double) (twoPointAttemptsTotal + threePointAttemptsTotal) / gamesTotal;
        this.freeThrowAttemptsPerGame = (double) freeThrowAttemptsTotal / gamesTotal;
        this.freeThrowMakesPerGame = (double) freeThrowMakesTotal / gamesTotal;
        this.attemptsPerGame = twoPointAttemptsPerGame + threePointAttemptsPerGame;
        this.makesPerGame = twoPointMakesPerGame + threePointMakesPerGame;
        this.turnoversPerGame = (double) this.turnoversTotal / gamesTotal;
        this.stealsPerGame = (double) this.stealsTotal / gamesTotal;
        this.foulsPerGame = (double) this.foulsTotal / gamesTotal;
        this.reboundsPerGame = (double) this.reboundsTotal / gamesTotal;
        this.offensiveReboundsPerGame = (double) this.offensiveReboundsTotal / gamesTotal;
        this.defensiveReboundsPerGame = (double) this.defensiveReboundsTotal / gamesTotal;
    }

    public void initializeTotals() {
        this.pointsTotal = 0;
        this.twoPointAttemptsTotal = 0;
        this.twoPointMakesTotal = 0;
        this.threePointAttemptsTotal = 0;
        this.threePointMakesTotal = 0;
        this.fieldGoalAttemptsTotal = 0;
        this.fieldGoalMakesTotal = 0;
        this.freeThrowAttemptsTotal = 0;
        this.freeThrowMakesTotal = 0;
        this.turnoversTotal = 0;
        this.stealsTotal = 0;
        this.foulsTotal = 0;
        this.reboundsTotal = 0;
        this.offensiveReboundsTotal = 0;
        this.defensiveReboundsTotal = 0;

        this.gamesTotal = 0;
        this.winsTotal = 0;
        this.lossesTotal = 0;
    }

    public void initializeAverages() {
        this.pointsPerGame = 0.0;
        this.twoPointAttemptsPerGame = 0.0;
        this.twoPointMakesPerGame = 0.0;
        this.threePointAttemptsPerGame = 0.0;
        this.threePointMakesPerGame = 0.0;
        this.fieldGoalAttemptsPerGame = 0.0;
        this.fieldGoalMakesPerGame = 0.0;
        this.freeThrowAttemptsPerGame = 0.0;
        this.freeThrowMakesPerGame = 0.0;
        this.attemptsPerGame = 0.0;
        this.makesPerGame = 0.0;
        this.turnoversPerGame = 0.0;
        this.stealsPerGame = 0.0;
        this.foulsPerGame = 0.0;
        this.reboundsPerGame = 0.0;
        this.offensiveReboundsPerGame = 0.0;
        this.defensiveReboundsPerGame = 0.0;
    }

    public void printAverages() {
        if (this.pointsPerGame == 0.0) { // Could technically be true even if the team has played before, but it is going to be true that the team hasn't played in pretty much 100% of the time.
            calculateAverages();
        }

        System.out.println("\n" + this.team.getFullName() + " statistics: ");
        System.out.printf(
                "\nPTS/g: %.2f\nREB/g: %.2f\nOREB/g: %.2f\nTOV/g: %.2f\nSTL/g: %.2f\nFouls/g: %.2f\nFG%%: %.2f\n3P%%: %.2f\nFT%%: %.2f\nFGM/g: %.2f\nFGA/g: %.2f\n2PM/g: %.2f\n2PA/g: %.2f\n3PM/g: %.2f\n3PA/g: %.2f\nFTM/g: %.2f\nFTA/g: %.2f\nWins: %d\nLosses: %d\n",
                pointsPerGame,
                reboundsPerGame,
                offensiveReboundsPerGame,
                turnoversPerGame,
                stealsPerGame,
                foulsPerGame,
                (makesPerGame / attemptsPerGame) * 100,
                (threePointMakesPerGame / threePointAttemptsPerGame) * 100,
                (freeThrowMakesPerGame / freeThrowAttemptsPerGame) * 100,
                fieldGoalMakesPerGame,
                fieldGoalAttemptsPerGame,
                twoPointMakesPerGame,
                twoPointAttemptsPerGame,
                threePointMakesPerGame,
                threePointAttemptsPerGame,
                freeThrowMakesPerGame,
                freeThrowAttemptsPerGame,
                winsTotal,
                lossesTotal
        );
    }

    public void printPlayerAverages() {
        for (int i = 0; i < team.roster.length; i++) {
            team.roster[i].getStatistics().printAverages();
        }
    }
}
