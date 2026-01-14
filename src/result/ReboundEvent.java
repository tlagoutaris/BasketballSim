package result;

import model.Player;
import simulation.PossessionEngine;

import java.sql.Time;

public class ReboundEvent implements GameEvent {
    boolean offenseRebounded;
    boolean defenseRebounded;
    boolean hasFoul;
    Player rebounder;
    TimeStamp timeStamp;
    PossessionEngine.OutcomeType outcomeType = null;

    public ReboundEvent(boolean offenseRebounded, boolean defenseRebounded, boolean hasFoul, Player rebounder, TimeStamp timeStamp) {
        this.offenseRebounded = offenseRebounded;
        this.defenseRebounded = defenseRebounded;
        this.hasFoul = hasFoul;
        this.rebounder = rebounder;
        this.timeStamp = timeStamp;
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

    public TimeStamp getTimeStamp() {
        return timeStamp;
    }

    @Override
    public PossessionEngine.OutcomeType getOutcomeType() {
        return this.outcomeType;
    }

    public void setOutcomeType(PossessionEngine.OutcomeType outcomeType) {
        this.outcomeType = outcomeType;
    }
}
