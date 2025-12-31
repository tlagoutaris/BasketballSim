package simulation;

import model.Player;
import result.ActionResult;
import java.security.SecureRandom;

public class ActionDecisionEngine {
    private SecureRandom r;

    public ActionDecisionEngine(SecureRandom r) {
        this.r = r;
    }

    public ActionResult determineAction(Player player) {
        String action = "Dribble";
        // Use the player's tendencies to decide their action
        double tendenciesSum = player.getPassTendency() + player.getShotTendency() + player.getDribbleTendency();

        // For now, there are 3 possible actions: shooting, passing, dribbling
        double tendencyPingPongBall = r.nextDouble(0, tendenciesSum);

        // Shoot
        if (tendencyPingPongBall <= player.getShotTendency()) {
            action = "Shoot";
        }

        else if (tendencyPingPongBall <= (player.getShotTendency() + player.getPassTendency())) {
            action = "Pass";
        }

        // return string
        return new ActionResult(action);
    }
}
