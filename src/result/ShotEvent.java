package result;

import model.Player;
import model.Team;
import simulation.PossessionEngine;

public class ShotEvent implements GameEvent {
    Player shooter;
    String shotType; // 2PT, 3PT
    boolean isMade;
    int points;
    boolean drewFoul;
    Team offensiveTeam;
    Team defendingTeam;
    int numFreeThrows;
    TimeStamp timeStamp;
    PossessionEngine.OutcomeType outcomeType = null;

    public ShotEvent(Player shooter, String shotType, boolean isMade, int points, boolean drewFoul, Team offensiveTeam, Team defendingTeam, int numFreeThrows, TimeStamp timeStamp) {
        this.shooter = shooter;
        this.shotType = shotType;
        this.isMade = isMade;
        this.points = points;
        this.drewFoul = drewFoul;
        this.offensiveTeam = offensiveTeam;
        this.defendingTeam = defendingTeam;
        this.numFreeThrows = numFreeThrows;
        this.timeStamp = timeStamp;
    }

    public Player getShooter() {
        return this.shooter;
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
