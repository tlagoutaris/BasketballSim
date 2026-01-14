package simulation;

import config.Config;
import result.TimeStamp;
import util.BoundedNormalDistribution;
import model.Player;
import result.StealEvent;
import java.security.SecureRandom;

public class StealEngine {
    private final SecureRandom r;

    public StealEngine(SecureRandom r) {
        this.r = r;
    }

    public StealEvent attemptSteal(Player defender, Player playerWithBall, TimeStamp timeStamp) {
        boolean stolen = false;
        boolean foul = false;

        double stealAttemptChance = r.nextDouble(Config.LOWER_BOUND, Config.UPPER_BOUND);
        double stealSuccessChance = r.nextDouble(Config.LOWER_BOUND, Config.UPPER_BOUND);

        // either you steal it or you don't
        if (stealAttemptChance <= defender.getStealAttemptTendency()) {
            // you attempt the steal
            int attributeDifference = defender.getSteal() - playerWithBall.getBallControl();
            double successThreshold = BoundedNormalDistribution.generateBoundedNormalDoubleInt(Config.BASE_STEAL_SUCCESS_CHANCE + (attributeDifference * Config.ATTRIBUTE_DIFFERENCE_MULTIPLIER), 10, Config.LOWER_BOUND, Config.UPPER_BOUND);

            if (stealSuccessChance <= successThreshold) {
                // if steal happens
                stolen = true;
            } else {
                // Foul tendency
                double foulChance = BoundedNormalDistribution.generateBoundedNormalDoubleInt(Config.BASE_FOUL_SUCCESS_CHANCE + (attributeDifference * Config.ATTRIBUTE_DIFFERENCE_MULTIPLIER), 10, Config.LOWER_BOUND, Config.UPPER_BOUND);
                if (foulChance <= defender.getFoulTendency()) {
                    foul = true;
                }
            }

        }

        return new StealEvent(stolen, foul, defender.getCurrentTeam(), defender, timeStamp);
    }
}
