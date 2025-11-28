public class StealResult {
    boolean stolen;
    boolean hasFoul;
    Team stealingTeam;

    public StealResult(boolean stolen, boolean hasFoul, Team stealingTeam) {
        this.stolen = stolen;
        this.hasFoul = hasFoul;
        this.stealingTeam = stealingTeam;
    }

    // Getters

    boolean stolen() {
        return this.stolen;
    }
    boolean hasFoul() {
        return this.hasFoul;
    }

    Team stealingTeam() {
        return this.stealingTeam;
    }
}
