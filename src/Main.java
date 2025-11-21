import java.security.SecureRandom;

public class Main {
    public static void main(String[] args) {
        Team t1 = new Team("Boston", "Celtics", .546, .347, 1 - .475);
        Team t2 = new Team("Brooklyn", "Nets", .533, .334, 1 - .482);

        Game g = new Game(t1, t2);
        for (int i = 0; i < 100_000; i++) {
            g.fullGameSimulation();
            g.reset();
        }

        t1.calculateAverages();
        t2.calculateAverages();
    }
}
