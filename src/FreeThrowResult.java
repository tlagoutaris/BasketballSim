public class FreeThrowResult {
    int freeThrowAttempts;
    int freeThrowsMade;
    int points;

    public FreeThrowResult(int freeThrowAttempts, int freeThrowsMade) {
        this.freeThrowAttempts = freeThrowAttempts;
        this.freeThrowsMade = freeThrowsMade;
    }

    public int getFreeThrowsMade() {
        return this.freeThrowsMade;
    }
}
