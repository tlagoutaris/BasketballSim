package result;

import model.Player;
import model.Team;

public class PassResult {
    boolean successful;
    boolean stolen;
    boolean turnover;
    Player passer;
    Player recipient;

    public PassResult(boolean successful, boolean stolen, boolean turnover, Player passer, Player recipient) {
        this.successful = successful;
        this.stolen = stolen;
        this.turnover = turnover;
        this.passer = passer;
        this.recipient = recipient;
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
}
