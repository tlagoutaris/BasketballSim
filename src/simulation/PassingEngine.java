package simulation;

import config.Config;
import model.Player;
import model.Team;
import result.PassResult;
import java.security.SecureRandom;
import java.util.Arrays;

public class PassingEngine {

    private final SecureRandom r;
    private final double[] runningSum = new double[6];

    public PassingEngine(SecureRandom r) {
        this.r = r;
    }

    public PassResult attemptPass(Player passer, Team opposingTeam) {
        boolean successful;
        boolean stolen;
        boolean turnover;

        // determine the intended recipient
        Player recipient = determineIntendedRecipient(passer.getCurrentTeam(), passer);

        double passSuccessChance = r.nextDouble(Config.LOWER_BOUND, Config.UPPER_BOUND);
        // pass thrown out of bounds
        if (passSuccessChance <= Config.BASE_PASS_BALL_OUT_OF_BOUNDS_CHANCE) {
            successful = false;
            stolen = false;
            turnover = true;
        }

        // pass deflected
        else if (passSuccessChance <= Config.BASE_PASS_DEFLECTED_CHANCE + (Config.BASE_PASS_BALL_OUT_OF_BOUNDS_CHANCE)) {
            recipient = determineDeflector(opposingTeam);

            successful = false;
            stolen = false;
            turnover = false;
        }

        // pass intercepted
        else if (passSuccessChance <= Config.BASE_PASS_INTERCEPTED_CHANCE + (Config.BASE_PASS_DEFLECTED_CHANCE + Config.BASE_PASS_BALL_OUT_OF_BOUNDS_CHANCE)) {
            recipient = determineInterceptor(opposingTeam);

            successful = false;
            stolen = true;
            turnover = true;
        }

        else {
            successful = true;
            stolen = false;
            turnover = false;
        }

        return new PassResult(successful, stolen, turnover, passer, recipient);
    }

    public Player determineIntendedRecipient(Team team, Player playerCurrentlyWithBall) {
        // Add up all of their tendencies
        Player recipient;

        int winningIndex = r.nextInt(0, team.getRoster().length);
        while (team.getRoster()[winningIndex] == playerCurrentlyWithBall) {
            winningIndex = r.nextInt(0, team.getRoster().length);
        }

        recipient = team.getRoster()[winningIndex];

        return recipient;
    }

    public Player determineInterceptor(Team team) {
        // Add up all of their tendencies
        this.runningSum[0] = 0;

        double tendenciesSum = 0;
        for (int i = 0; i < team.getRoster().length; i++) {
            Player p = team.getRoster()[i];
            tendenciesSum += p.getInterceptionTendency(); // intercepting tendency
            this.runningSum[i + 1] = tendenciesSum;
        }

        // then do a lottery to find who will be the interceptor
        double pingPongBall = r.nextDouble(0, tendenciesSum);
        Player interceptor = team.getRoster()[0];
        for (int i = 0; i < this.runningSum.length - 1; i++) {
            if (pingPongBall >= this.runningSum[i] && pingPongBall <= this.runningSum[i + 1]) {
                interceptor = team.getRoster()[i];
                break;
            }
        }

        //resetRunningSum();
        return interceptor;
    }

    public Player determineDeflector(Team team) {
        // Add up all of their tendencies
        this.runningSum[0] = 0;

        double tendenciesSum = 0;
        for (int i = 0; i < team.getRoster().length; i++) {
            Player p = team.getRoster()[i];
            tendenciesSum += p.getDeflectionTendency(); // deflection tendency
            this.runningSum[i + 1] = tendenciesSum;
        }

        // then do a lottery to find who will be the interceptor
        double pingPongBall = r.nextDouble(0, tendenciesSum);
        Player deflector = team.getRoster()[0];
        for (int i = 0; i < this.runningSum.length - 1; i++) {
            if (pingPongBall >= this.runningSum[i] && pingPongBall <= this.runningSum[i + 1]) {
                deflector = team.getRoster()[i];
                break;
            }
        }

        //resetRunningSum();
        return deflector;
    }

    void resetRunningSum() {
        Arrays.fill(this.runningSum, 0);
    }
}
