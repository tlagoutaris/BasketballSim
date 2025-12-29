package result;

import model.Team;

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

    public boolean stolen() {
        return this.stolen;
    }
    public boolean hasFoul() {
        return this.hasFoul;
    }

    public Team stealingTeam() {
        return this.stealingTeam;
    }
}
