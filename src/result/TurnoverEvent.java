package result;

import model.Player;
import simulation.PossessionEngine;

public class TurnoverEvent implements GameEvent {
    Player playerWhoTurnedItOver;
    String type; // "Thrown out of bounds", "Shot clock violation", "Bad pass"
    TimeStamp timeStamp;
    PossessionEngine.OutcomeType outcomeType = null;

    public TurnoverEvent(Player playerWhoTurnedItOver, String type, TimeStamp timeStamp) {
        this.playerWhoTurnedItOver = playerWhoTurnedItOver;
        this.type = type;
        this.timeStamp = timeStamp;
    }

    // Getters

    public Player getPlayerWhoTurnedItOver() {
        return playerWhoTurnedItOver;
    }

    public String getType() {
        return type;
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
