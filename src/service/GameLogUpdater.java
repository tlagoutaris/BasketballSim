package service;

import model.BoxScore;
import model.GameLog;
import model.Player;
import result.*;

import java.util.ArrayList;

public class GameLogUpdater {

    public GameLogUpdater() {

    }

    // Setters
    void addEvent(GameEvent event, GameLog log) {
        // ignore passes
        if (event instanceof PassEvent) {
            return;
        }

        // Add to events list
        log.getEvents().add(event);
        String play = "";

        if (event instanceof ShotEvent shot) {
            Player offensivePlayer = shot.getShooter();

            if (!shot.isMade()) {
                play = String.format("%s missed the jumpshot", offensivePlayer);
            } else {
                play = String.format("%s made a %d jumpshot", offensivePlayer, shot.getPoints());
            }
        }

        else if (event instanceof StealEvent steal) {
            Player offensivePlayer = steal.getOriginalBallHandler();
            Player defensivePlayer = steal.getStealer();

            play = String.format("%s turns it over (%s steals)", offensivePlayer, defensivePlayer);
        }

        else if (event instanceof FreeThrowEvent freeThrow) {
            Player offensivePlayer = freeThrow.getPlayer();

            play = String.format("%s shoots %d/%d from the line", offensivePlayer, freeThrow.getFreeThrowsMade(), freeThrow.getFreeThrowAttempts());
        }

        else if (event instanceof FoulEvent foul) {
            Player didFoul = foul.getPlayerWhoFouled();
            Player gotFouled = foul.getPlayerWhoGotFouled();

            play = String.format("%s fouls %s", didFoul, gotFouled);
        }

        else if (event instanceof ReboundEvent rebound) {
            Player rebounder = rebound.getRebounder();

            if (rebound.isDefenseRebounded()) {
                play = String.format("%s defensive rebound", rebounder);
            }

            else {
                play = String.format("%s offensive rebound", rebounder);
            }
        }

        else if (event instanceof TurnoverEvent turnover) {
            play = String.format("%s turns it over", turnover.getPlayerWhoTurnedItOver());
        }

        log.getTranscript().add(play);
    }
}
