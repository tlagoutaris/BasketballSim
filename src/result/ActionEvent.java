package result;

import simulation.PossessionEngine;

public class ActionEvent {

    String action;
    TimeStamp timeStamp;
    PossessionEngine.OutcomeType outcomeType = null;

    public ActionEvent(String action) {
        this.action = action;
    }

    public String getAction() {
        return this.action;
    }
}
