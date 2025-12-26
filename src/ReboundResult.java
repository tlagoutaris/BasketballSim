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

    boolean isOffenseRebounded() {
        return this.offenseRebounded;
    }
    boolean isDefenseRebounded() {
        return this.defenseRebounded;
    }
    boolean hasFoul() {
        return this.hasFoul;
    }

    Player getRebounder() {
        return this.rebounder;
    }
}
