import java.security.SecureRandom;

public class Main {
    public static void main(String[] args) {
        SecureRandom r = new SecureRandom();
        StealEngine stealEngine = new StealEngine(r);
        ShootingEngine shootingEngine = new ShootingEngine(r);
        StatisticsService statisticsService = new StatisticsService();
        PossessionEngine possessionEngine = new PossessionEngine(stealEngine, shootingEngine, statisticsService, r);

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

        t1.getStatistics().calculateAverages();
        t1.printRoster();
        t2.getStatistics().calculateAverages();
        t2.printRoster();
    }
}
