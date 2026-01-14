package result;

import model.Player;
import simulation.PossessionEngine;

public class FreeThrowEvent implements GameEvent {
    Player player;
    int freeThrowAttempts;
    int freeThrowsMade;
    boolean lastFreeThrowMissed;
    TimeStamp timeStamp;
    PossessionEngine.OutcomeType outcomeType = null;

    public FreeThrowEvent(Player player, int freeThrowAttempts, int freeThrowsMade, boolean lastFreeThrowMissed, TimeStamp timeStamp) {
        this.player = player;
        this.freeThrowAttempts = freeThrowAttempts;
        this.freeThrowsMade = freeThrowsMade;
        this.lastFreeThrowMissed = lastFreeThrowMissed;
        this.timeStamp = timeStamp;
    }

    public Player getPlayer() {
        return this.player;
    }

    public int getFreeThrowAttempts() {
        return this.freeThrowAttempts;
    }

    public int getFreeThrowsMade() {
        return this.freeThrowsMade;
    }

    public boolean isLastFreeThrowMissed() {
        return this.lastFreeThrowMissed;
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
