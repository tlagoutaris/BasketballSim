package model;

import simulation.PossessionEngine;
import result.PossessionResult;
import config.Config;

import java.security.SecureRandom;

public class Game {
    SecureRandom r;
    PossessionEngine possessionEngine;

    // model.Game state
    Team homeTeam;
    Team awayTeam;
    Team currentOffense;
    Team currentDefense;
    int homeScore;
    int awayScore;
    int overtimes;
    int currentPeriod;
    boolean inOvertime;

    // Constants

    public Game(Team homeTeam, Team awayTeam, PossessionEngine possessionEngine) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.possessionEngine = possessionEngine;
        this.r = new SecureRandom();
        this.initializeGame();
    }

    public void initializeGame() {
        this.homeScore = 0;
        this.awayScore = 0;
        this.currentPeriod = 1;
        this.inOvertime = false;
        this.overtimes = 0;
        this.determinePossession();
    }

    public void determinePossession() {
        if (r.nextBoolean()) {
            this.currentOffense = this.homeTeam;
            this.currentDefense = this.awayTeam;
        }

        else {
            this.currentOffense = this.awayTeam;
            this.currentDefense = this.homeTeam;
        }
    }

    public void simulateGame() {

        // Simulate regulation periods
        for (int period = 1; period <= Config.NUM_PERIODS; period++) {
            this.currentPeriod = period;
            simulatePeriod(Config.REGULATION_PERIOD_LENGTH_SECONDS);
        }

        // Check for overtime
        while (homeScore == awayScore) {
            this.inOvertime = true;
            this.currentPeriod++;
            this.overtimes++;
            this.simulatePeriod(Config.OVERTIME_PERIOD_LENGTH_SECONDS);
        }

        this.determineWinner();
    }

    public void simulatePeriod(double periodLength) {
        double periodTimeLeft = periodLength;

        while (periodTimeLeft > 0) {
            // Simulate one possession
            PossessionResult result = possessionEngine.simulatePossession(currentOffense, currentDefense);

            // Update game based on possession result
            this.updateScore(result);
            periodTimeLeft -= result.getPossessionLength();

            if (result.doesPossessionChange()) {
                this.swapPossession();
            }
        }
    }

    public void updateScore(PossessionResult result) {
        int points = result.getPointsScored();
        if (result.getOffense() == this.homeTeam) {
            this.homeScore += points;
        } else {
            this.awayScore += points;
        }
    }

    public void swapPossession() {
        Team temp = this.currentOffense;
        this.currentOffense = this.currentDefense;
        this.currentDefense = temp;
    }

    public void determineWinner() {
        homeTeam.getStatistics().gamesTotal++;
        awayTeam.getStatistics().gamesTotal++;
        homeTeam.getStatistics().recordGamePlayed();
        awayTeam.getStatistics().recordGamePlayed();

        if (this.homeScore > this.awayScore) {
            homeTeam.getStatistics().winsTotal++;
            awayTeam.getStatistics().lossesTotal++;
        }

        else {
            awayTeam.getStatistics().winsTotal++;
            homeTeam.getStatistics().lossesTotal++;
        }
    }

    public int getOvertimes() {
        return this.overtimes;
    }

    public int getHomeScore() {
        return this.homeScore;
    }

    public int getAwayScore() {
        return this.awayScore;
    }

    public void printScore() {
        if (this.overtimes > 0) {
            System.out.println("FINAL/"+this.overtimes+"OT" + "\n" + this.homeTeam.getFullName() + ": " + this.getHomeScore() + "\n" + this.awayTeam.getFullName() + ": " + this.getAwayScore());
            System.out.println();
        } else {
            System.out.println(this.homeTeam.getFullName() + ": " + this.getHomeScore() + "\n" + this.awayTeam.getFullName() + ": " + this.getAwayScore());
            System.out.println();
        }
    }
}
