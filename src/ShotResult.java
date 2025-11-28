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

    String shotType() {
        return this.shotType;
    }

    boolean isMade() {
        return this.isMade;
    }

    int getPoints() {
        return this.points;
    }

    boolean drewFoul() {
        return this.drewFoul;
    }

    Team getOffensiveTeam() {
        return this.offensiveTeam;
    }

    Team getDefendingTeam() {
        return this.defendingTeam;
    }

    int freeThrowsAwarded() {
        return this.numFreeThrows;
    }
}
