package simulation;

import config.Config;
import util.BoundedNormalDistribution;
import model.Player;
import result.FreeThrowResult;
import result.ShotResult;
import java.security.SecureRandom;

public class ShootingEngine {
    private final SecureRandom r;

    public ShootingEngine(SecureRandom r) {
        this.r = r;
    }

    public ShotResult attemptShot(Player shooter, Player defender) {
        String shotType; // 2PT, 3PT
        boolean made = false;
        int points = 0;
        boolean drewFoul = false;
        int numFreeThrows = 0;

        double shotTypeChance = r.nextDouble(Config.LOWER_BOUND, Config.UPPER_BOUND);
        double shotSuccessChance = r.nextDouble(Config.LOWER_BOUND, Config.UPPER_BOUND);
        double foulChance;

        if (shotTypeChance <= shooter.getTwoPointTendency()) {
            // 2PT
            shotType = "2PT";

            // Calculate user's offense against opponent's defense
            int attributeDifference = shooter.getTwoPointOffense() - defender.getTwoPointDefense();
            double successThreshold = BoundedNormalDistribution.generateBoundedNormalDoubleInt(Config.BASE_TWO_POINT_PERCENTAGE + (attributeDifference * Config.ATTRIBUTE_DIFFERENCE_MULTIPLIER), Config.BASE_ATTRIBUTE_STDDEV, Config.LOWER_BOUND, Config.UPPER_BOUND);

            if (shotSuccessChance <= successThreshold) {
                points = 2;
                made = true;

                // Check for foul
                foulChance = r.nextDouble(Config.LOWER_BOUND, Config.UPPER_BOUND);
                if (foulChance <= defender.getFoulTendency()) {
                    // Made and-1 2pt shot
                    drewFoul = true;
                    numFreeThrows = 1;
                }
            } else {

                // fail before foul check because if you miss and get fouled, no shot attempt

                // Check for foul
                foulChance = r.nextDouble(Config.LOWER_BOUND, Config.UPPER_BOUND);
                if (foulChance <= defender.getFoulTendency()) {
                    // Missed 2pt shot but got fouled
                    drewFoul = true;
                    numFreeThrows = 2;
                }
            }

        } else {
            // 3PT
            shotType = "3PT";

            int attributeDifference = shooter.getThreePointOffense() - defender.getThreePointDefense();
            double successThreshold = BoundedNormalDistribution.generateBoundedNormalDoubleInt(Config.BASE_THREE_POINT_PERCENTAGE + (attributeDifference * Config.ATTRIBUTE_DIFFERENCE_MULTIPLIER), 10, Config.LOWER_BOUND, Config.UPPER_BOUND);

            if (shotSuccessChance <= successThreshold) {
                made = true;
                points = 3;

                // Check for foul
                foulChance = r.nextDouble(Config.LOWER_BOUND, Config.UPPER_BOUND);
                if (foulChance <= defender.getFoulTendency()) {
                    // Fouled
                    drewFoul = true;
                    numFreeThrows = 1;
                }

            } else {

                // Check for foul
                foulChance = r.nextDouble(Config.LOWER_BOUND, Config.UPPER_BOUND);
                if (foulChance <= defender.getFoulTendency()) {
                    // Missed 3pt shot but got fouled
                    drewFoul = true;
                    numFreeThrows = 3;
                }
            }

        }

        // String shotType, boolean isMade, int points, boolean drewFoul, model.Team offensiveTeam, model.Team defendingTeam
        return new ShotResult(shotType, made, points, drewFoul, shooter.getCurrentTeam(), defender.getCurrentTeam(), numFreeThrows);
    }

    public FreeThrowResult attemptFreeThrows(Player shooter, int freeThrowAttempts) {
        int points = 0;
        boolean lastFreeThrowMissed = false;

        for (int i = 0; i < freeThrowAttempts - 1; i++) {
            double freeThrowSuccessChance = r.nextDouble(Config.LOWER_BOUND, Config.UPPER_BOUND);
            if (freeThrowSuccessChance <= shooter.getFreeThrow()) {
                // Made free throw
                points++;
            }
        }

        double freeThrowSuccessChance = r.nextDouble(Config.LOWER_BOUND, Config.UPPER_BOUND);
        if (freeThrowSuccessChance <= shooter.getFreeThrow()) {
            // Made free throw
            points++;
        } else {
            lastFreeThrowMissed = true;
        }

        return new FreeThrowResult(freeThrowAttempts, points, lastFreeThrowMissed);
    }
}
