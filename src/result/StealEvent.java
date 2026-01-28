package result;

import model.Player;
import model.Team;
import simulation.PossessionEngine;

public class StealEvent implements GameEvent {
    boolean stolen;
    boolean hasFoul;
    Team stealingTeam;
    Player originalBallHandler;
    Player stealer;
    TimeStamp timeStamp;
    PossessionEngine.OutcomeType outcomeType = null;

    public StealEvent(boolean stolen, boolean hasFoul, Team stealingTeam, Player originalBallHandler, Player stealer, TimeStamp timeStamp) {
        this.stolen = stolen;
        this.hasFoul = hasFoul;
        this.stealingTeam = stealingTeam;
        this.originalBallHandler = originalBallHandler;
        this.stealer = stealer;
        this.timeStamp = timeStamp;
    }

    // Getters

    public boolean stolen() {
        return this.stolen;
    }
    public boolean hasFoul() {
        return this.hasFoul;
    }

    public Team stealingTeam() {
        return this.stealingTeam;
    }

    public Player getOriginalBallHandler() {
        return this.originalBallHandler;
    }
    public Player getStealer() {
        return this.stealer;
    }

    public TimeStamp getTimeStamp() {
        return timeStamp;
    }

    @Override
    public PossessionEngine.OutcomeType getOutcomeType() {
        return this.outcomeType;
    }

    // Setters
    public void setOriginalBallHandler(Player originalBallHandler) {
        this.originalBallHandler = originalBallHandler;
    }

    public void setOutcomeType(PossessionEngine.OutcomeType outcomeType) {
        this.outcomeType = outcomeType;
    }
}
