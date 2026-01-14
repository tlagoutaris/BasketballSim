package result;

import simulation.PossessionEngine;

public interface GameEvent {
    public TimeStamp getTimeStamp();
    public PossessionEngine.OutcomeType getOutcomeType();
}
