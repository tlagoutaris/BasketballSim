import java.security.SecureRandom;

public class PossessionEngine {
    ShootingEngine shootingEngine;
    StealEngine stealEngine;
    StatisticsService statisticsService;
    SecureRandom r;

    public PossessionEngine(StealEngine stealEngine, ShootingEngine shootingEngine, StatisticsService statisticsService, SecureRandom r) {
        this.stealEngine = stealEngine;
        this.shootingEngine = shootingEngine;
        this.statisticsService = statisticsService;
        this.r = r;
    }

    PossessionResult simulatePossession(Team offense, Team defense) {
        // Select random players for possession
        Player offensivePlayer = selectRandomPlayer(offense);
        Player defensivePlayer = selectRandomPlayer(defense);

        // Check for steal attempt
        StealResult stealResult = stealEngine.attemptSteal(defensivePlayer, offensivePlayer);

        if (stealResult.stolen()) {
            statisticsService.recordSteal(defensivePlayer, stealResult);
            statisticsService.recordTurnover(offensivePlayer);
            return new PossessionResult(PossessionResult.OutcomeType.STEAL, offense, defense, 0);
        }

        if (stealResult.hasFoul()) {
            statisticsService.recordFoul(defensivePlayer, stealResult);
            return new PossessionResult(PossessionResult.OutcomeType.NON_SHOOTING_FOUL, offense, defense, 0);
        }

        // No steal -- attempts a shot
        ShotResult shotResult = shootingEngine.attemptShot(offensivePlayer, defensivePlayer);

        if (shotResult.drewFoul()) {
            // Shooting foul
            statisticsService.recordShot(offensivePlayer, shotResult);
            statisticsService.recordFoul(defensivePlayer, shotResult);
            FreeThrowResult freeThrows = shootingEngine.attemptFreeThrows(offensivePlayer, shotResult.freeThrowsAwarded());
            statisticsService.recordFreeThrows(offensivePlayer, freeThrows);

            int totalPoints = shotResult.getPoints() + freeThrows.getFreeThrowsMade();
            return new PossessionResult(PossessionResult.OutcomeType.SHOOTING_FOUL, offense, defense, totalPoints);
        } else {
            // Regular shot attempt
            statisticsService.recordShot(offensivePlayer, shotResult);
            PossessionResult.OutcomeType outcome;
            if (shotResult.isMade()) {
                outcome = PossessionResult.OutcomeType.SHOT_MADE;
            } else {
                outcome = PossessionResult.OutcomeType.SHOT_MISSED;
            }

            return new PossessionResult(outcome, offense, defense, shotResult.getPoints());
        }
    }

    Player selectRandomPlayer(Team team) {
        return team.roster[r.nextInt(0, team.roster.length)];
    }
}
