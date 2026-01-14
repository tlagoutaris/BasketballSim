package model;

import service.StatisticsService;
import simulation.PossessionEngine;
import config.Config;
import java.security.SecureRandom;
import result.*;
import java.util.ArrayList;

public class Game {
    SecureRandom r;
    PossessionEngine possessionEngine = new PossessionEngine();
    StatisticsService statistics = new StatisticsService();

    // model.Game state
    Team homeTeam;
    Team awayTeam;
    Team currentOffense;
    Team currentDefense;
    BoxScore boxScore = new BoxScore(homeTeam, awayTeam);
    int overtimes;
    int currentPeriod;
    boolean inOvertime;

    // Constants

    public Game(Team homeTeam, Team awayTeam) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.r = new SecureRandom();
        this.initializeGame();
    }

    public void initializeGame() {
        this.boxScore = new BoxScore(homeTeam, awayTeam);
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
        int period;
        for (period = 1; period <= Config.NUM_PERIODS; period++) {
            this.currentPeriod = period;
            simulatePeriod(Config.REGULATION_PERIOD_LENGTH_SECONDS, this.currentPeriod);
        }

        // Check for overtime
        while (boxScore.getHomeScore() == boxScore.getAwayScore()) {
            this.inOvertime = true;
            this.currentPeriod++;
            this.overtimes++;
            this.simulatePeriod(Config.OVERTIME_PERIOD_LENGTH_SECONDS, this.currentPeriod);
        }

        this.determineWinner();
    }

    public void simulatePeriod(double periodLength, int currentPeriod) {
        double periodTimeLeft = periodLength;

        while (periodTimeLeft > 0) {
            // Simulate one possession
            ArrayList<GameEvent> events = possessionEngine.simulatePossession(currentOffense, currentDefense, currentPeriod, periodTimeLeft);

            // Update game based on possession result
            periodTimeLeft -= possessionEngine.calculatePossessionLength(events);

            System.out.println("-- POSSESSION ENDED --");

            // Update scores
            statistics.handle(events, this.boxScore);

            if (possessionEngine.doesPossessionChange(events.getLast().getOutcomeType())) {
                this.swapPossession();
            }
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

        if (boxScore.getHomeScore() > boxScore.getAwayScore()) {
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

    public void printScore() {
        if (this.overtimes > 0) {
            System.out.println("FINAL/"+this.overtimes+"OT" + "\n" + this.homeTeam.getFullName() + ": " + this.boxScore.getHomeScore() + "\n" + this.awayTeam.getFullName() + ": " + this.boxScore.getAwayScore());
            System.out.println();
        } else {
            System.out.println(this.homeTeam.getFullName() + ": " + this.boxScore.getHomeScore() + "\n" + this.awayTeam.getFullName() + ": " + this.boxScore.getAwayScore());
            System.out.println();
        }
    }
}
