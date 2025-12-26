import java.security.SecureRandom;

public class Main {
    public static void main(String[] args) {
        SecureRandom r = new SecureRandom();
        ShootingEngine shootingEngine = new ShootingEngine(r);
        ReboundingEngine reboundingEngine = new ReboundingEngine(r);
        StealEngine stealEngine = new StealEngine(r);
        StatisticsService statisticsService = new StatisticsService();
        PossessionEngine possessionEngine = new PossessionEngine(shootingEngine, reboundingEngine, stealEngine, statisticsService, r);

        Team t1 = new Team("Boston", "Celtics");
        Team t2 = new Team("Brooklyn", "Nets");

        Game g = new Game(t1, t2, possessionEngine);
        for (int i = 0; i < 2_460; i++) {
            g.simulateGame();
            if (g.overtimes >= 2) {
                g.printScore();
                System.out.println(i);
            }
            g.initializeGame();
        }

        t1.getStatistics().printAverages();
        t1.getStatistics().printPlayerAverages();
        t2.getStatistics().printAverages();
        t2.getStatistics().printPlayerAverages();
    }
}
