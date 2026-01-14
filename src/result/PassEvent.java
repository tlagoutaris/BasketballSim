package result;

import model.Player;
import simulation.PossessionEngine;

import java.sql.Time;

public class PassEvent implements GameEvent {
    boolean successful;
    boolean stolen;
    boolean turnover;
    Player passer;
    Player recipient;
    TimeStamp timeStamp;
    PossessionEngine.OutcomeType outcomeType = null;

    public PassEvent(boolean successful, boolean stolen, boolean turnover, Player passer, Player recipient, TimeStamp timeStamp) {
        this.successful = successful;
        this.stolen = stolen;
        this.turnover = turnover;
        this.passer = passer;
        this.recipient = recipient;
        this.timeStamp = timeStamp;
    }

    // Getters
    public boolean isSuccessful() {
        return this.successful;
    }

    public boolean isStolen() {
        return this.stolen;
    }

    public boolean isDeflection() {
        return !this.stolen && !this.successful && !this.turnover;
    }

    public boolean isThrownOutOfBounds() {
        return !this.stolen && this.turnover;
    }

    public boolean isTurnover() {
        return this.turnover;
    }

    public Player getPasser() {
        return this.passer;
    }

    public Player getRecipient() {
        return this.recipient;
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
