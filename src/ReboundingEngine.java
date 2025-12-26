import java.security.SecureRandom;

public class ReboundingEngine {
    private final SecureRandom r;

    public ReboundingEngine(SecureRandom r) {
        this.r = r;
    }

    public ReboundResult attemptRebound(Team offensiveTeam, Team defensiveTeam) {
        boolean offenseRebounded = false;
        boolean defenseRebounded = false;
        boolean hasFoul = false;
        Player rebounder = null;

        // Find rebounders
        Player offensiveRebounder = determineRebounder(offensiveTeam, true);
        Player defensiveRebounder = determineRebounder(defensiveTeam, false);

        // Calculate the offensive player's rebounding vs defensive player's rebounding
        int attributeDifference = defensiveRebounder.defensiveRebounding - offensiveRebounder.offensiveRebounding;

        double reboundSuccessChance = r.nextDouble(Config.LOWER_BOUND, Config.UPPER_BOUND);
        double successThreshold = BoundedNormalDistribution.generateBoundedNormalDoubleInt(Config.BASE_DEFENSIVE_REBOUND_TENDENCY + (attributeDifference * Config.ATTRIBUTE_DIFFERENCE_MULTIPLIER), Config.BASE_ATTRIBUTE_STDDEV, Config.LOWER_BOUND, Config.UPPER_BOUND);
        if (reboundSuccessChance <= Config.BASE_BALL_OUT_OF_BOUNDS_CHANCE) {
            // Nothing changes
        }

        // if defensive rebounder gets rebound
        else if (reboundSuccessChance <= successThreshold + Config.BASE_BALL_OUT_OF_BOUNDS_CHANCE) {
            defenseRebounded = true;
            rebounder = defensiveRebounder;
            // determine if foul
            double foulChance = r.nextDouble(Config.LOWER_BOUND, Config.UPPER_BOUND);
            if (foulChance <= defensiveRebounder.foulTendency) {
                defenseRebounded = false;
                hasFoul = true;
                rebounder = null;
            }
        }

        // if offensive rebounder gets rebound
        else {
            offenseRebounded = true;
            rebounder = offensiveRebounder;
            // determine if foul
            double foulChance = r.nextDouble(Config.LOWER_BOUND, Config.UPPER_BOUND);
            if (foulChance <= offensiveRebounder.foulTendency) {
                offenseRebounded = false;
                hasFoul = true;
                rebounder = null;
            }
        }

        return new ReboundResult(offenseRebounded, defenseRebounded, hasFoul, rebounder);
    }

    public Player determineRebounder(Team team, boolean onOffense) {
        // Add up all of their tendencies
        double[] runningSum = new double[team.getRoster().length + 1];
        runningSum[0] = 0;

        double tendenciesSum = 0;
        if (onOffense) {
            for (int i = 0; i < team.getRoster().length; i++) {
                Player p = team.getRoster()[i];
                tendenciesSum += p.offensiveReboundingTendency;
                runningSum[i + 1] = tendenciesSum;
            }
        }

        else {
            for (int i = 0; i < team.getRoster().length; i++) {
                Player p = team.getRoster()[i];
                tendenciesSum += p.defensiveReboundingTendency;
                runningSum[i + 1] = tendenciesSum;
            }
        }

        // then do a lottery to find who will be the rebounder
        double pingPongBall = r.nextDouble(0, tendenciesSum);
        Player rebounder = team.getRoster()[0];
        for (int i = 0; i < runningSum.length - 1; i++) {
            if (pingPongBall >= runningSum[i] && pingPongBall <= runningSum[i + 1]) {
                rebounder = team.getRoster()[i];
                break;
            }
        }

        return rebounder;
    }
}
