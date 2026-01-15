package simulation;

import config.Config;
import model.Team;
import model.Player;

import result.*;

import service.StatisticsService;
import util.BoundedNormalDistribution;
import java.util.ArrayList;

import java.security.SecureRandom;

public class PossessionEngine {

    public enum OutcomeType {
        STEAL,
        NON_SHOOTING_FOUL,
        SHOOTING_FOUL,
        SHOT_MADE,
        SHOT_MISSED,
        FREE_THROW_SHOT_MADE,
        SHOT_OUT_OF_BOUNDS,
        SUCCESSFUL_PASS,
        PASS_OUT_OF_BOUNDS,
        PASS_DEFLECTED_OUT_OF_BOUNDS,
        OFFENSIVE_REBOUND,
        DEFENSIVE_REBOUND,
        TURNOVER,
        SHOT_CLOCK_VIOLATION
    }

    // Engines
    SecureRandom r = new SecureRandom();
    ActionDecisionEngine actionDecisionEngine = new ActionDecisionEngine(r);
    ShootingEngine shootingEngine = new ShootingEngine(r);
    PassingEngine passingEngine = new PassingEngine(r);
    StealEngine stealEngine = new StealEngine(r);
    ReboundingEngine reboundingEngine = new ReboundingEngine(r);

    // Possession trackers
    int currentEvent = 0;
    int numEvents = 0;
    double shotClock = Config.NUM_SECONDS_POSSESSION;

    public PossessionEngine() {

    }

    public ArrayList<GameEvent> simulatePossession(Team offense, Team defense, int period, double periodLengthRemaining) {
        ArrayList<GameEvent> events = new ArrayList<>();

        if (periodLengthRemaining < Config.NUM_SECONDS_POSSESSION) {
            shotClock = periodLengthRemaining;
        }

        this.numEvents = numberOfEvents(offense);

        // inbound the ball
        Player inbounder = selectInBounder(offense);

        PassEvent inboundPass = passingEngine.attemptPass(inbounder, defense, new TimeStamp(period, periodLengthRemaining, shotClock));
        // Check if still retained possession
        if (!inboundPass.isSuccessful()) {
            if (inboundPass.isStolen()) {
                inboundPass.setOutcomeType(OutcomeType.TURNOVER);
                events.add(inboundPass);

                StealEvent steal = new StealEvent(true, false, defense, inboundPass.getRecipient(), new TimeStamp(period, periodLengthRemaining, shotClock));
                steal.setOutcomeType(OutcomeType.STEAL);
                events.add(steal);
                return events;
            } else if (inboundPass.isDeflection()) {
                inboundPass.setOutcomeType(OutcomeType.PASS_DEFLECTED_OUT_OF_BOUNDS);
                events.add(inboundPass);
                // it should not technically be a possession change, but it should be inbounded again
            } else if (inboundPass.isThrownOutOfBounds()) {
                inboundPass.setOutcomeType(OutcomeType.PASS_OUT_OF_BOUNDS);
                events.add(inboundPass);

                TurnoverEvent turnover = new TurnoverEvent(inbounder, "Thrown out of bounds", new TimeStamp(period, periodLengthRemaining, shotClock));
                turnover.setOutcomeType(OutcomeType.TURNOVER);
                events.add(turnover);
                return events;
            }
        }

        Player playerWithBall = inboundPass.getRecipient();
        for (this.currentEvent = 0; this.currentEvent < this.numEvents; this.currentEvent++) {
            // Check if it is the last moment or <2 seconds; if so, just shoot
            if (shotClock <= 2 || this.currentEvent == this.numEvents - 1) {
                Player defensivePlayer = selectRandomPlayer(defense);

                // Attempts a shot
                ShotEvent shotEvent = shootingEngine.attemptShot(playerWithBall, defensivePlayer, new TimeStamp(period, periodLengthRemaining, shotClock));

                if (shotEvent.drewFoul()) {
                    if (shotEvent.isMade()) {
                        shotEvent.setOutcomeType(OutcomeType.SHOT_MADE);
                        events.add(shotEvent);
                    }

                    FoulEvent foulEvent = new FoulEvent(playerWithBall, defensivePlayer, "Non shooting foul", new TimeStamp(period, periodLengthRemaining, shotClock));
                    foulEvent.setOutcomeType(OutcomeType.NON_SHOOTING_FOUL);
                    events.add(foulEvent);

                    // Shooting foul
                    FreeThrowEvent freeThrows = shootingEngine.attemptFreeThrows(playerWithBall, shotEvent.freeThrowsAwarded(), new TimeStamp(period, periodLengthRemaining, shotClock));
                    if (freeThrows.isLastFreeThrowMissed()) {
                        ReboundEvent reboundEvent = reboundingEngine.attemptRebound(offense, defense, new TimeStamp(period, periodLengthRemaining, shotClock));
                        if (reboundEvent.isDefenseRebounded()) {
                            reboundEvent.setOutcomeType(OutcomeType.DEFENSIVE_REBOUND);
                            events.add(reboundEvent);
                            return events;
                        }

                        else if (reboundEvent.isOffenseRebounded()) {
                            shotClock = Math.max(Config.SHOT_CLOCK_RESET_ON_OFFENSIVE_REBOUND, shotClock);
                            playerWithBall = reboundEvent.getRebounder();

                            // Reset event count
                            this.currentEvent = 0;
                            this.numEvents = numberOfEvents(offense);

                            reboundEvent.setOutcomeType(OutcomeType.OFFENSIVE_REBOUND);
                            events.add(reboundEvent);
                        }

                        else {
                            freeThrows.setOutcomeType(OutcomeType.TURNOVER);
                            events.add(freeThrows);
                            return events;
                        }
                    } else {
                        freeThrows.setOutcomeType(OutcomeType.FREE_THROW_SHOT_MADE);
                        events.add(freeThrows);
                        return events;
                    }
                } else {
                    // Regular shot attempt
                    if (shotEvent.isMade()) {
                        shotEvent.setOutcomeType(OutcomeType.SHOT_MADE);
                        events.add(shotEvent);
                        return events;
                    } else {
                        shotEvent.setOutcomeType(OutcomeType.SHOT_MISSED);
                        events.add(shotEvent);

                        ReboundEvent reboundEvent = reboundingEngine.attemptRebound(offense, defense, new TimeStamp(period, periodLengthRemaining, shotClock));
                        if (reboundEvent.isDefenseRebounded()) {
                            reboundEvent.setOutcomeType(OutcomeType.DEFENSIVE_REBOUND);
                            events.add(reboundEvent);
                            return events;
                        }

                        else if (reboundEvent.isOffenseRebounded()) {
                            shotClock = Math.max(Config.SHOT_CLOCK_RESET_ON_OFFENSIVE_REBOUND, shotClock);
                            playerWithBall = reboundEvent.getRebounder();

                            // Reset event count
                            this.currentEvent = 0;
                            this.numEvents = numberOfEvents(offense);

                            reboundEvent.setOutcomeType(OutcomeType.OFFENSIVE_REBOUND);
                            events.add(reboundEvent);
                        }

                        else {
                            // Shot out of bounds
                            shotEvent.setOutcomeType(OutcomeType.SHOT_OUT_OF_BOUNDS);
                            return events;
                        }
                    }
                }

            }

            // Some time passes
            shotClock = secondsRemaining(shotClock, numEvents - currentEvent);

            if (shotClock <= 0) {
                break;
            }

            // Player decides an action
            ActionEvent action = actionDecisionEngine.determineAction(playerWithBall, new TimeStamp(period, periodLengthRemaining, shotClock));
            if (action.getAction().equals("Pass")) {
                PassEvent pass = passingEngine.attemptPass(playerWithBall, defense, new TimeStamp(period, periodLengthRemaining, shotClock));
                // Check if still retained possession
                if (!pass.isSuccessful()) {
                    if (pass.isStolen()) {
                        pass.setOutcomeType(OutcomeType.TURNOVER);
                        events.add(pass);

                        // Steal
                        StealEvent steal = new StealEvent(true, false, defense, pass.getRecipient(), new TimeStamp(period, periodLengthRemaining, shotClock));
                        steal.setOutcomeType(OutcomeType.STEAL);
                        events.add(steal);

                        // Turnover
                        TurnoverEvent turnover = new TurnoverEvent(playerWithBall, "Bad pass", new TimeStamp(period, periodLengthRemaining, shotClock));
                        turnover.setOutcomeType(OutcomeType.TURNOVER);
                        events.add(turnover);

                        return events;
                    } else if (pass.isDeflection()) {
                        pass.setOutcomeType(OutcomeType.PASS_DEFLECTED_OUT_OF_BOUNDS);
                        events.add(pass);
                    } else if (pass.isThrownOutOfBounds()) {
                        pass.setOutcomeType(OutcomeType.TURNOVER);
                        events.add(pass);

                        // Turnover
                        TurnoverEvent turnover = new TurnoverEvent(pass.getPasser(), "Thrown out of bounds", new TimeStamp(period, periodLengthRemaining, shotClock));
                        turnover.setOutcomeType(OutcomeType.TURNOVER);
                        events.add(turnover);
                        return events;
                    }
                } else {
                    pass.setOutcomeType(OutcomeType.SUCCESSFUL_PASS);
                    events.add(pass);
                }

                playerWithBall = pass.getRecipient();
            }

            else if (action.getAction().equals("Shoot")) {
                Player defensivePlayer = selectRandomPlayer(defense);

                // Attempts a shot
                ShotEvent shotEvent = shootingEngine.attemptShot(playerWithBall, defensivePlayer, new TimeStamp(period, periodLengthRemaining, shotClock));

                if (shotEvent.drewFoul()) {
                    if (shotEvent.isMade()) {
                        shotEvent.setOutcomeType(OutcomeType.SHOT_MADE);
                        events.add(shotEvent);
                    }

                    // Add foul to list of events
                    FoulEvent foulEvent = new FoulEvent(playerWithBall, defensivePlayer, "Shooting foul", new TimeStamp(period, periodLengthRemaining, shotClock));
                    foulEvent.setOutcomeType(OutcomeType.SHOOTING_FOUL);
                    events.add(foulEvent);

                    // Shooting foul
                    FreeThrowEvent freeThrows = shootingEngine.attemptFreeThrows(playerWithBall, shotEvent.freeThrowsAwarded(), new TimeStamp(period, periodLengthRemaining, shotClock));

                    if (freeThrows.isLastFreeThrowMissed()) {
                        ReboundEvent reboundEvent = reboundingEngine.attemptRebound(offense, defense, new TimeStamp(period, periodLengthRemaining, shotClock));
                        if (reboundEvent.isDefenseRebounded()) {
                            reboundEvent.setOutcomeType(OutcomeType.DEFENSIVE_REBOUND);
                            events.add(reboundEvent);
                            return events;
                        }

                        else if (reboundEvent.isOffenseRebounded()) {
                            shotClock = Math.max(Config.SHOT_CLOCK_RESET_ON_OFFENSIVE_REBOUND, shotClock);
                            playerWithBall = reboundEvent.getRebounder();

                            // Reset event count
                            this.currentEvent = 0;
                            this.numEvents = numberOfEvents(offense);

                            reboundEvent.setOutcomeType(OutcomeType.OFFENSIVE_REBOUND);
                            events.add(reboundEvent);
                        }

                        else {
                            // Shot out of bounds
                            freeThrows.setOutcomeType(OutcomeType.TURNOVER);
                            events.add(freeThrows);
                            return events;
                        }
                    } else {
                        freeThrows.setOutcomeType(OutcomeType.FREE_THROW_SHOT_MADE);
                        events.add(freeThrows);
                        return events;
                    }
                } else {
                    // Regular shot attempt
                    if (shotEvent.isMade()) {
                        shotEvent.setOutcomeType(OutcomeType.SHOT_MADE);
                        events.add(shotEvent);
                        return events;
                    } else {
                        shotEvent.setOutcomeType(OutcomeType.SHOT_MISSED);
                        events.add(shotEvent);

                        ReboundEvent reboundEvent = reboundingEngine.attemptRebound(offense, defense, new TimeStamp(period, periodLengthRemaining, shotClock));
                        if (reboundEvent.isDefenseRebounded()) {
                            reboundEvent.setOutcomeType(OutcomeType.DEFENSIVE_REBOUND);
                            events.add(reboundEvent);
                            return events;
                        }

                        else if (reboundEvent.isOffenseRebounded()) {
                            shotClock = Math.max(Config.SHOT_CLOCK_RESET_ON_OFFENSIVE_REBOUND, shotClock);
                            playerWithBall = reboundEvent.getRebounder();

                            // Reset event count
                            this.currentEvent = 0;
                            this.numEvents = numberOfEvents(offense);

                            reboundEvent.setOutcomeType(OutcomeType.OFFENSIVE_REBOUND);
                            events.add(reboundEvent);
                        }

                        else {
                            // Shot out of bounds
                            shotEvent.setOutcomeType(OutcomeType.SHOT_OUT_OF_BOUNDS);
                            return events;
                        }
                    }
                }
            }

            else if (action.getAction().equals("Dribble")) {
                Player defensivePlayer = selectRandomPlayer(defense);

                // Check for steal attempt
                StealEvent stealEvent = stealEngine.attemptSteal(defensivePlayer, playerWithBall, new TimeStamp(period, periodLengthRemaining, shotClock));

                if (stealEvent.stolen()) {
                    stealEvent.setOutcomeType(OutcomeType.STEAL);
                    events.add(stealEvent);
                    return events;
                }

                if (stealEvent.hasFoul()) {
                    // Nothing
                    stealEvent.setOutcomeType(OutcomeType.NON_SHOOTING_FOUL);
                    events.add(stealEvent);

                    FoulEvent foulEvent = new FoulEvent(playerWithBall, defensivePlayer, "Non shooting foul", new TimeStamp(period, periodLengthRemaining, shotClock));
                    foulEvent.setOutcomeType(OutcomeType.NON_SHOOTING_FOUL);
                    events.add(foulEvent);

                    shotClock = Math.max(Config.SHOT_CLOCK_RESET_ON_FOUL, shotClock);

                    // Reset event count
                    this.currentEvent = 0;
                    this.numEvents = numberOfEvents(offense);
                }
            }
        }

        // this should only be reached upon a shotclock violation because every other event should lead to a natural return statement
        TurnoverEvent shotClockViolation = new TurnoverEvent(playerWithBall, "Shot clock violation", new TimeStamp(period, periodLengthRemaining, shotClock));
        shotClockViolation.setOutcomeType(OutcomeType.SHOT_CLOCK_VIOLATION);
        events.add(shotClockViolation);

        return events;
    }

    public Player selectInBounder(Team offensiveTeam) {
        return selectRandomPlayer(offensiveTeam);
    }

    public int numberOfEvents(Team offensiveTeam) { // this should eventually be moved to a lineup class where each new active lineup is re-calculated for its "activeness"
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

    public double secondsRemaining(double shotClock, int numberOfEventsLeft) {
        if (numberOfEventsLeft <= 0) {
            return 0;
        }

        // double averageMomentLengthPossible = shotClock / numberOfEventsLeft; // this is supposed to create a sort of speeding up of play as getting closer to the shotclock
        double timeUsed = BoundedNormalDistribution.generateBoundedNormalDoubleInt(
                Config.BASE_MOMENT_LENGTH_MEAN,
                Config.BASE_MOMENT_LENGTH_STDDEV,
                Config.BASE_TIME_NEEDED_TO_SHOOT,
                shotClock
        );

        return shotClock - timeUsed;
    }

    public boolean doesPossessionChange(OutcomeType outcome) {
        if (outcome == null) {
            throw new IllegalStateException("OutcomeType was null — possession resolution incomplete");
        }

        switch (outcome) {
            // Outcome types which result in possession changes
            case STEAL:
            case SHOT_MADE:
            case FREE_THROW_SHOT_MADE:
            case DEFENSIVE_REBOUND:
            case TURNOVER:
            case SHOT_OUT_OF_BOUNDS:
            case PASS_OUT_OF_BOUNDS:
            case SHOT_CLOCK_VIOLATION:
                return true;
            // Outcome type which do not result in possession changes
            case NON_SHOOTING_FOUL:
            case SHOOTING_FOUL:
            case OFFENSIVE_REBOUND:
            case PASS_DEFLECTED_OUT_OF_BOUNDS:
            case SUCCESSFUL_PASS:
                return false;
            default:
                return true;
        }
    }

    public double calculatePossessionLength(ArrayList<GameEvent> eventsList) {
        return Config.NUM_SECONDS_POSSESSION - eventsList.getLast().getTimeStamp().getShotClock();
    }

    public Player selectRandomPlayer(Team team) {
        return team.getRoster()[r.nextInt(0, team.getRoster().length)];
    }
}
