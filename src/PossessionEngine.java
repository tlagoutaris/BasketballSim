import java.security.SecureRandom;

public class PossessionEngine {
    ShootingEngine shootingEngine;
    StealEngine stealEngine;
    ReboundingEngine reboundingEngine;
    StatisticsService statisticsService;
    SecureRandom r;

    public PossessionEngine(ShootingEngine shootingEngine, ReboundingEngine reboundingEngine, StealEngine stealEngine, StatisticsService statisticsService, SecureRandom r) {
        // Engines
        this.shootingEngine = shootingEngine;
        this.reboundingEngine = reboundingEngine;
        this.stealEngine = stealEngine;

        // Statistics
        this.statisticsService = statisticsService;

        // Tools
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
            PossessionResult.OutcomeType outcome = PossessionResult.OutcomeType.SHOOTING_FOUL;

            if (freeThrows.isLastFreeThrowMissed()) {
                ReboundResult reboundResult = reboundingEngine.attemptRebound(offense, defense);
                if (reboundResult.defenseRebounded) {
                    statisticsService.recordDefensiveRebound(reboundResult.rebounder);
                    outcome = PossessionResult.OutcomeType.DEFENSIVE_REBOUND;
                }

                else if (reboundResult.offenseRebounded) {
                    statisticsService.recordOffensiveRebound(reboundResult.rebounder);
                    outcome = PossessionResult.OutcomeType.OFFENSIVE_REBOUND;
                }

                else {
                    outcome = PossessionResult.OutcomeType.SHOT_OUT_OF_BOUNDS;
                }
            }

            return new PossessionResult(outcome, offense, defense, totalPoints);
        } else {
            // Regular shot attempt
            statisticsService.recordShot(offensivePlayer, shotResult);
            PossessionResult.OutcomeType outcome;
            if (shotResult.isMade()) {
                outcome = PossessionResult.OutcomeType.SHOT_MADE;
            } else {
                ReboundResult reboundResult = reboundingEngine.attemptRebound(offense, defense);
                if (reboundResult.defenseRebounded) {
                    statisticsService.recordDefensiveRebound(reboundResult.rebounder);
                    outcome = PossessionResult.OutcomeType.DEFENSIVE_REBOUND;
                }

                else if (reboundResult.offenseRebounded) {
                    statisticsService.recordOffensiveRebound(reboundResult.rebounder);
                    outcome = PossessionResult.OutcomeType.OFFENSIVE_REBOUND;
                }

                else {
                    outcome = PossessionResult.OutcomeType.SHOT_OUT_OF_BOUNDS;
                }
            }

            return new PossessionResult(outcome, offense, defense, shotResult.getPoints());
        }
    }

    Player selectRandomPlayer(Team team) {
        return team.roster[r.nextInt(0, team.roster.length)];
    }
}
