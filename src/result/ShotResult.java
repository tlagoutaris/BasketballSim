package result;

import model.Team;

public class ShotResult {
    String shotType; // 2PT, 3PT
    boolean isMade;
    int points;
    boolean drewFoul;
    Team offensiveTeam;
    Team defendingTeam;
    int numFreeThrows;

    public ShotResult(String shotType, boolean isMade, int points, boolean drewFoul, Team offensiveTeam, Team defendingTeam, int numFreeThrows) {
        this.shotType = shotType;
        this.isMade = isMade;
        this.points = points;
        this.drewFoul = drewFoul;
        this.offensiveTeam = offensiveTeam;
        this.defendingTeam = defendingTeam;
        this.numFreeThrows = numFreeThrows;
    }

    public String getShotType() {
        return this.shotType;
    }

    public boolean isMade() {
        return this.isMade;
    }

    public int getPoints() {
        return this.points;
    }

    public boolean drewFoul() {
        return this.drewFoul;
    }

    public Team getOffensiveTeam() {
        return this.offensiveTeam;
    }

    public Team getDefendingTeam() {
        return this.defendingTeam;
    }

    public int freeThrowsAwarded() {
        return this.numFreeThrows;
    }
}
