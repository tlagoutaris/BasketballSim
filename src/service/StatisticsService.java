package service;

import model.Player;
import model.BoxScore;
import service.GameLogUpdater;
import result.*;

import java.util.ArrayList;

public class StatisticsService {

    public StatisticsService() {

    }

    public void handle(ArrayList<GameEvent> events, BoxScore boxScore) {
        for (GameEvent event : events) {

            event.getTimeStamp().printTime();

            if (event instanceof ShotEvent shot) {
                if (!shot.isMade()) {
                    System.out.println("Shot missed event!");
                } else {
                    System.out.println("Shot made event: " + shot.getPoints() + " points");
                }

                this.recordShot(shot.getShooter(), boxScore, shot);
            }

            else if (event instanceof StealEvent steal) {
                System.out.println("Steal event!");
                this.recordSteal(steal.getStealer(), steal);
            }

            else if (event instanceof FreeThrowEvent freeThrow) {
                System.out.println("Free throw event: " + freeThrow.getFreeThrowAttempts() + " attempts");
                this.recordFreeThrows(freeThrow.getPlayer(), boxScore, freeThrow);
            }

            else if (event instanceof FoulEvent foul) {
                System.out.println("Foul event!");
                this.recordFoul(foul.getPlayerWhoFouled());
            }

            else if (event instanceof ReboundEvent rebound) {
                if (rebound.isDefenseRebounded()) {
                    System.out.println("Defense rebound event!");
                    this.recordDefensiveRebound(rebound.getRebounder());
                }

                else {
                    System.out.println("Offensive rebound event!");
                    this.recordOffensiveRebound(rebound.getRebounder());
                }
            }

            else if (event instanceof TurnoverEvent turnover) {
                System.out.println("Turnover event!");
                this.recordTurnover(turnover.getPlayerWhoTurnedItOver());
            }

            else if (event instanceof PassEvent pass) {
                System.out.println("Pass event!");
            }

            else {
                System.out.println("Error event!");
            }
        }
    }

    public void recordShot(Player offensivePlayer, BoxScore boxScore, ShotEvent result) {
        offensivePlayer.getStatistics().recordShotAttempt(result);
        offensivePlayer.getCurrentTeam().getStatistics().recordShotAttempt(result);
        boxScore.setScore(offensivePlayer, result.getPoints());
    }

    public void recordSteal(Player defensivePlayer, StealEvent result) {
        defensivePlayer.getStatistics().recordStealAttempt(result);
        defensivePlayer.getCurrentTeam().getStatistics().recordStealAttempt(result);
    }

    public void recordFreeThrows(Player offensivePlayer, BoxScore boxScore, FreeThrowEvent result) {
        offensivePlayer.getStatistics().recordFreeThrowAttempt(result);
        offensivePlayer.getCurrentTeam().getStatistics().recordFreeThrowAttempt(result);
        boxScore.setScore(offensivePlayer, result.getFreeThrowsMade());
    }

    public void recordFoul(Player defensivePlayer) {
        defensivePlayer.getStatistics().recordFoul();
        defensivePlayer.getCurrentTeam().getStatistics().recordFoul();
    }

    public void recordRebound(Player rebounder) {
        rebounder.getStatistics().recordRebound();
        rebounder.getCurrentTeam().getStatistics().recordRebound();
    }

    public void recordOffensiveRebound(Player rebounder) {
        rebounder.getStatistics().recordOffensiveRebound();
        rebounder.getStatistics().recordRebound();
        rebounder.getCurrentTeam().getStatistics().recordOffensiveRebound();
        rebounder.getCurrentTeam().getStatistics().recordRebound();
    }

    public void recordDefensiveRebound(Player rebounder) {
        rebounder.getStatistics().recordDefensiveRebound();
        rebounder.getStatistics().recordRebound();
        rebounder.getCurrentTeam().getStatistics().recordDefensiveRebound();
        rebounder.getCurrentTeam().getStatistics().recordRebound();
    }

    public void recordTurnover(Player offensivePlayer) {
        offensivePlayer.getStatistics().recordTurnover();
        offensivePlayer.getCurrentTeam().getStatistics().recordTurnover();
    }
}
