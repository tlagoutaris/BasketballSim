package result;

import model.Player;

public class ReboundResult {
    boolean offenseRebounded;
    boolean defenseRebounded;
    boolean hasFoul;
    Player rebounder;

    public ReboundResult(boolean offenseRebounded, boolean defenseRebounded, boolean hasFoul, Player rebounder) {
        this.offenseRebounded = offenseRebounded;
        this.defenseRebounded = defenseRebounded;
        this.hasFoul = hasFoul;
        this.rebounder = rebounder;
    }

    // Getters

    public boolean isOffenseRebounded() {
        return this.offenseRebounded;
    }
    public boolean isDefenseRebounded() {
        return this.defenseRebounded;
    }
    public boolean hasFoul() {
        return this.hasFoul;
    }
    public Player getRebounder() {
        return this.rebounder;
    }
}
