import model.Game;
import model.Player;
import model.Team;
import result.ShotEvent;
import result.TimeStamp;
import service.StatisticsService;
import simulation.*;

import java.security.SecureRandom;

public class Main {
    public static void main(String[] args) {
        Team t1 = new Team("Boston", "Celtics");
        Team t2 = new Team("Brooklyn", "Nets");

        Game g = new Game(t1, t2);
        for (int i = 0; i < 1_000; i++) {
            g.initializeGame();
            g.simulateGame();
            g.printScore();
            System.out.println(i + 1);
        }

        t1.getStatistics().printAverages();
        t2.getStatistics().printAverages();
    }
}
