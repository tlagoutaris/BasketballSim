public class FreeThrowResult {
    int freeThrowAttempts;
    int freeThrowsMade;
    boolean lastFreeThrowMissed;

    public FreeThrowResult(int freeThrowAttempts, int freeThrowsMade, boolean lastFreeThrowMissed) {
        this.freeThrowAttempts = freeThrowAttempts;
        this.freeThrowsMade = freeThrowsMade;
        this.lastFreeThrowMissed = lastFreeThrowMissed;
    }

    public int getFreeThrowsMade() {
        return this.freeThrowsMade;
    }

    public boolean isLastFreeThrowMissed() {
        return this.lastFreeThrowMissed;
    }
}
