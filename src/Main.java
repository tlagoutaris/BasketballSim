import java.security.SecureRandom;

public class Main {
    public static void main(String[] args) {
        Team t1 = new Team("Boston", "Celtics");
        Team t2 = new Team("Brooklyn", "Nets");

        Game g = new Game(t1, t2);
        for (int i = 0; i < 2_460; i++) {
            g.fullGameSimulation();
            if (g.overtimes >= 2) {
                g.printScore();
                System.out.println(i);
            }
            g.reset();
        }

        t1.calculateAverages();
        t1.printRoster();
        t2.calculateAverages();
        t2.printRoster();
    }
}
