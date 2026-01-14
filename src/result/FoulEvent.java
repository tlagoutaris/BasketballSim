package result;

import model.Player;
import simulation.PossessionEngine;

public class FoulEvent implements GameEvent {
    Player playerWhoGotFouled;
    Player playerWhoFouled;
    String type;
    TimeStamp timeStamp;
    PossessionEngine.OutcomeType outcomeType = null;

    public FoulEvent(Player playerWhoGotFouled, Player playerWhoFouled, String type, TimeStamp timeStamp) {
        this.playerWhoGotFouled = playerWhoGotFouled;
        this.playerWhoFouled = playerWhoFouled;
        this.type = type;
        this.timeStamp = timeStamp;
    }

    // Getters

    public Player getPlayerWhoGotFouled() {
        return playerWhoGotFouled;
    }

    public Player getPlayerWhoFouled() {
        return playerWhoFouled;
    }

    public String getType() {
        return type;
    }

    public TimeStamp getTimeStamp() {
        return timeStamp;
    }

    public void setOutcomeType(PossessionEngine.OutcomeType outcomeType) {
        this.outcomeType = outcomeType;
    }

    @Override
    public PossessionEngine.OutcomeType getOutcomeType() {
        return this.outcomeType;
    }
}
