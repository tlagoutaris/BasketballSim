package simulation;

import config.Config;
import model.Team;
import model.Player;

import result.PossessionResult;
import result.FreeThrowResult;
import result.ReboundResult;
import result.ShotResult;
import result.StealResult;
import result.PassResult;
import result.ActionResult;

import service.StatisticsService;
import util.BoundedNormalDistribution;
import util.Trace;

import javax.swing.*;
import java.security.SecureRandom;

public class PossessionEngine {
    ActionDecisionEngine actionDecisionEngine;
    ShootingEngine shootingEngine;
    PassingEngine passingEngine;
    StealEngine stealEngine;
    ReboundingEngine reboundingEngine;
    StatisticsService statisticsService;
    SecureRandom r;

    public PossessionEngine(ActionDecisionEngine actionDecisionEngine, ShootingEngine shootingEngine, PassingEngine passingEngine, ReboundingEngine reboundingEngine, StealEngine stealEngine, StatisticsService statisticsService, SecureRandom r) {
        // Engines
        this.actionDecisionEngine = actionDecisionEngine;
        this.shootingEngine = shootingEngine;
        this.passingEngine = passingEngine;
        this.reboundingEngine = reboundingEngine;
        this.stealEngine = stealEngine;

        // Statistics
        this.statisticsService = statisticsService;

        // Tools
        this.r = r;
    }

    public PossessionResult simulatePossession(Team offense, Team defense, double shotClock, double periodLengthRemaining) {

        if (periodLengthRemaining < Config.NUM_SECONDS_POSSESSION) {
            shotClock = periodLengthRemaining;
        }

        int moments = numberOfMoments(offense);

        // inbound the ball
        Player inbounder = selectRandomPlayer(offense);

        PassResult inboundPass = passingEngine.attemptPass(inbounder, defense);
        // Check if still retained possession
        if (!inboundPass.isSuccessful()) {
            if (inboundPass.isStolen()) {
                statisticsService.recordTurnover(inbounder);
                statisticsService.recordSteal(inboundPass.getRecipient(), new StealResult(true, false, defense));
                return new PossessionResult(PossessionResult.OutcomeType.STEAL, offense, defense, 0, 0);
            } else if (inboundPass.isDeflection()) {
                // it should not technically be a possession change, but it should be inbounded again
                return new PossessionResult(PossessionResult.OutcomeType.PASS_DEFLECTED_OUT_OF_BOUNDS, offense, defense, 0, 0);
            } else if (inboundPass.isThrownOutOfBounds()) {
                statisticsService.recordTurnover(inbounder);
                return new PossessionResult(PossessionResult.OutcomeType.PASS_OUT_OF_BOUNDS, offense, defense, 0, 0);
            }
        }

        Player lastPasser = inboundPass.getPasser();
        Player playerWithBall = inboundPass.getRecipient();
        PossessionResult.OutcomeType outcome = PossessionResult.OutcomeType.SHOT_CLOCK_VIOLATION;
        for (int i = 0; i < moments; i++) {
            // Check if it is the last moment or <2 seconds; if so, just shoot
            if (shotClock <= 2 || i == moments - 1) {
                Player defensivePlayer = selectRandomPlayer(defense);

                // Attempts a shot
                ShotResult shotResult = shootingEngine.attemptShot(playerWithBall, defensivePlayer);

                if (shotResult.drewFoul()) {
                    // Shooting foul
                    statisticsService.recordShot(playerWithBall, shotResult);
                    statisticsService.recordFoul(defensivePlayer, shotResult);
                    FreeThrowResult freeThrows = shootingEngine.attemptFreeThrows(playerWithBall, shotResult.freeThrowsAwarded());
                    statisticsService.recordFreeThrows(playerWithBall, freeThrows);

                    int totalPoints = shotResult.getPoints() + freeThrows.getFreeThrowsMade();
                    outcome = PossessionResult.OutcomeType.SHOOTING_FOUL;

                    if (freeThrows.isLastFreeThrowMissed()) {
                        ReboundResult reboundResult = reboundingEngine.attemptRebound(offense, defense);
                        if (reboundResult.isDefenseRebounded()) {
                            statisticsService.recordDefensiveRebound(reboundResult.getRebounder());
                            outcome = PossessionResult.OutcomeType.DEFENSIVE_REBOUND;
                            return new PossessionResult(outcome, offense, defense, totalPoints, shotClock);
                        }

                        else if (reboundResult.isOffenseRebounded()) {
                            statisticsService.recordOffensiveRebound(reboundResult.getRebounder());
                            outcome = PossessionResult.OutcomeType.OFFENSIVE_REBOUND;

                            shotClock = Math.max(Config.SHOT_CLOCK_RESET_ON_OFFENSIVE_REBOUND, shotClock);

                            playerWithBall = reboundResult.getRebounder();

                            // Reset moment count
                            i = 0;
                            moments = numberOfMoments(offense);
                        }

                        else {
                            outcome = PossessionResult.OutcomeType.SHOT_OUT_OF_BOUNDS;
                            return new PossessionResult(outcome, offense, defense, totalPoints, shotClock);
                        }
                    } else {
                        return new PossessionResult(outcome, offense, defense, totalPoints, shotClock);
                    }
                } else {
                    // Regular shot attempt
                    statisticsService.recordShot(playerWithBall, shotResult);
                    if (shotResult.isMade()) {
                        outcome = PossessionResult.OutcomeType.SHOT_MADE;
                        return new PossessionResult(outcome, offense, defense, shotResult.getPoints(), shotClock);
                    } else {
                        ReboundResult reboundResult = reboundingEngine.attemptRebound(offense, defense);
                        if (reboundResult.isDefenseRebounded()) {
                            statisticsService.recordDefensiveRebound(reboundResult.getRebounder());
                            outcome = PossessionResult.OutcomeType.DEFENSIVE_REBOUND;
                            return new PossessionResult(outcome, offense, defense, shotResult.getPoints(), shotClock);
                        }

                        else if (reboundResult.isOffenseRebounded()) {
                            statisticsService.recordOffensiveRebound(reboundResult.getRebounder());
                            outcome = PossessionResult.OutcomeType.OFFENSIVE_REBOUND;

                            shotClock = Math.max(Config.SHOT_CLOCK_RESET_ON_OFFENSIVE_REBOUND, shotClock);

                            playerWithBall = reboundResult.getRebounder();

                            // Reset moment count
                            i = 0;
                            moments = numberOfMoments(offense);
                        }

                        else {
                            outcome = PossessionResult.OutcomeType.SHOT_OUT_OF_BOUNDS;
                            return new PossessionResult(outcome, offense, defense, shotResult.getPoints(), shotClock);
                        }
                    }
                }
            }

            // Some time passes
            shotClock = secondsRemaining(shotClock, moments - i);

            if (shotClock <= 0) {
                break;
            }

            // Player decides an action
            ActionResult action = actionDecisionEngine.determineAction(playerWithBall);

            if (action.getAction().equals("Pass")) {
                PassResult pass = passingEngine.attemptPass(playerWithBall, defense);
                // Check if still retained possession
                if (!pass.isSuccessful()) {
                    if (pass.isStolen()) {
                        statisticsService.recordTurnover(playerWithBall);
                        statisticsService.recordSteal(pass.getRecipient(), new StealResult(true, false, defense));
                        outcome = PossessionResult.OutcomeType.STEAL;
                    } else if (pass.isDeflection()) {
                        outcome = PossessionResult.OutcomeType.PASS_DEFLECTED_OUT_OF_BOUNDS;
                    } else if (pass.isThrownOutOfBounds()) {
                        statisticsService.recordTurnover(playerWithBall);
                        outcome = PossessionResult.OutcomeType.PASS_OUT_OF_BOUNDS;
                    }

                    return new PossessionResult(outcome, offense, defense, 0, shotClock);
                }

                playerWithBall = pass.getRecipient();
            }

            else if (action.getAction().equals("Shoot")) {
                Player defensivePlayer = selectRandomPlayer(defense);

                // Attempts a shot
                ShotResult shotResult = shootingEngine.attemptShot(playerWithBall, defensivePlayer);

                if (shotResult.drewFoul()) {
                    // Shooting foul
                    statisticsService.recordShot(playerWithBall, shotResult);
                    statisticsService.recordFoul(defensivePlayer, shotResult);
                    FreeThrowResult freeThrows = shootingEngine.attemptFreeThrows(playerWithBall, shotResult.freeThrowsAwarded());
                    statisticsService.recordFreeThrows(playerWithBall, freeThrows);

                    int totalPoints = shotResult.getPoints() + freeThrows.getFreeThrowsMade();
                    outcome = PossessionResult.OutcomeType.SHOOTING_FOUL;

                    if (freeThrows.isLastFreeThrowMissed()) {
                        ReboundResult reboundResult = reboundingEngine.attemptRebound(offense, defense);
                        if (reboundResult.isDefenseRebounded()) {
                            statisticsService.recordDefensiveRebound(reboundResult.getRebounder());
                            outcome = PossessionResult.OutcomeType.DEFENSIVE_REBOUND;
                            return new PossessionResult(outcome, offense, defense, totalPoints, shotClock);
                        }

                        else if (reboundResult.isOffenseRebounded()) {
                            statisticsService.recordOffensiveRebound(reboundResult.getRebounder());
                            outcome = PossessionResult.OutcomeType.OFFENSIVE_REBOUND;

                            shotClock = Math.max(Config.SHOT_CLOCK_RESET_ON_OFFENSIVE_REBOUND, shotClock);

                            playerWithBall = reboundResult.getRebounder();

                            // Reset moment count
                            i = 0;
                            moments = numberOfMoments(offense);
                        }

                        else {
                            outcome = PossessionResult.OutcomeType.SHOT_OUT_OF_BOUNDS;
                            return new PossessionResult(outcome, offense, defense, totalPoints, shotClock);
                        }
                    } else {
                        return new PossessionResult(outcome, offense, defense, totalPoints, shotClock);
                    }
                } else {
                    // Regular shot attempt
                    statisticsService.recordShot(playerWithBall, shotResult);
                    if (shotResult.isMade()) {
                        outcome = PossessionResult.OutcomeType.SHOT_MADE;
                        return new PossessionResult(outcome, offense, defense, shotResult.getPoints(), shotClock);
                    } else {
                        ReboundResult reboundResult = reboundingEngine.attemptRebound(offense, defense);
                        if (reboundResult.isDefenseRebounded()) {
                            statisticsService.recordDefensiveRebound(reboundResult.getRebounder());
                            outcome = PossessionResult.OutcomeType.DEFENSIVE_REBOUND;
                            return new PossessionResult(outcome, offense, defense, shotResult.getPoints(), shotClock);
                        }

                        else if (reboundResult.isOffenseRebounded()) {
                            statisticsService.recordOffensiveRebound(reboundResult.getRebounder());
                            outcome = PossessionResult.OutcomeType.OFFENSIVE_REBOUND;

                            shotClock = Math.max(Config.SHOT_CLOCK_RESET_ON_OFFENSIVE_REBOUND, shotClock);

                            playerWithBall = reboundResult.getRebounder();

                            // Reset moment count
                            i = 0;
                            moments = numberOfMoments(offense);
                        }

                        else {
                            outcome = PossessionResult.OutcomeType.SHOT_OUT_OF_BOUNDS;
                            return new PossessionResult(outcome, offense, defense, shotResult.getPoints(), shotClock);
                        }
                    }
                }
            }

            else if (action.getAction().equals("Dribble")) {
                Player defensivePlayer = selectRandomPlayer(defense);

                // Check for steal attempt
                StealResult stealResult = stealEngine.attemptSteal(defensivePlayer, playerWithBall);

                if (stealResult.stolen()) {
                    statisticsService.recordSteal(defensivePlayer, stealResult);
                    statisticsService.recordTurnover(playerWithBall);
                    return new PossessionResult(PossessionResult.OutcomeType.STEAL, offense, defense, 0, shotClock);
                }

                if (stealResult.hasFoul()) {
                    statisticsService.recordFoul(defensivePlayer, stealResult);
                    return new PossessionResult(PossessionResult.OutcomeType.NON_SHOOTING_FOUL, offense, defense, 0, shotClock);
                }
            }
        }

        return new PossessionResult(outcome, offense, defense, 0, Config.NUM_SECONDS_POSSESSION);
    }

    public Player selectInBounder(Team offensiveTeam) {
        return selectRandomPlayer(offensiveTeam);
    }

    public int numberOfMoments(Team offensiveTeam) { // this should eventually be moved to a lineup class where each new active lineup is re-calculated for its "activeness"
        return r.nextInt(1, 8);

//        double tendenciesSum = 0;
//        for (Player p : offensiveTeam.getRoster()) {
//            tendenciesSum += p.getShotTendency();
//            tendenciesSum += p.getPassTendency();
//            tendenciesSum += p.getDribbleTendency();
//        }
//
//        double activeness = tendenciesSum / (Config.BASE_SHOT_ATTEMPT_TENDENCY + Config.BASE_DRIBBLE_TENDENCY + Config.BASE_PASS_ATTEMPT_TENDENCY);
//
//        return (int) (activeness * Config.BASE_MOMENTS_PER_POSSESSION);
    }

    public double secondsRemaining(double shotClock, int numberOfMomentsLeft) {
        if (numberOfMomentsLeft <= 0) {
            return 0;
        }

        double averageMomentLengthPossible = shotClock / numberOfMomentsLeft;
        double timeUsed = BoundedNormalDistribution.generateBoundedNormalDoubleInt(
                averageMomentLengthPossible,
                Config.BASE_MOMENT_LENGTH_STDDEV,
                Config.BASE_TIME_NEEDED_TO_SHOOT,
                shotClock
        );

        return shotClock - timeUsed;
    }

    public Player selectRandomPlayer(Team team) {
        return team.getRoster()[r.nextInt(0, team.getRoster().length)];
    }
}
