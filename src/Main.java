import java.security.SecureRandom;

public class Main {
    public static void main(String[] args) {
        Team t1 = new Team("Boston", "Celtics");
        Team t2 = new Team("Brooklyn", "Nets");

        Game g = new Game(t1, t2);
        for (int i = 0; i < 1_000_000; i++) {
            g.fullGameSimulation();
            if (g.getOvertimes() >= 5) {
                g.printScore();
            }
            g.reset();
        }
    }
}
