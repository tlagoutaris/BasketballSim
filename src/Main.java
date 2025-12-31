import model.Game;
import model.Team;
import service.StatisticsService;
import simulation.*;

import java.security.SecureRandom;

public class Main {
    public static void main(String[] args) {
        SecureRandom r = new SecureRandom();
        ActionDecisionEngine actionDecisionEngine = new ActionDecisionEngine(r);
        ShootingEngine shootingEngine = new ShootingEngine(r);
        PassingEngine passingEngine = new PassingEngine(r);
        ReboundingEngine reboundingEngine = new ReboundingEngine(r);
        StealEngine stealEngine = new StealEngine(r);
        StatisticsService statisticsService = new StatisticsService();
        PossessionEngine possessionEngine = new PossessionEngine(actionDecisionEngine, shootingEngine, passingEngine, reboundingEngine, stealEngine, statisticsService, r);

        Team t1 = new Team("Boston", "Celtics");
        Team t2 = new Team("Brooklyn", "Nets");

        Game g = new Game(t1, t2, possessionEngine);
        for (int i = 0; i < 1; i++) {
            g.initializeGame();
            g.simulateGame();
            g.printScore();
            System.out.println(i + 1);
        }

        t1.getStatistics().printAverages();
        t1.getStatistics().printPlayerAverages();
        t2.getStatistics().printAverages();
        t2.getStatistics().printPlayerAverages();
    }
}
