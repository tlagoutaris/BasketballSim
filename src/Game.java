import java.security.SecureRandom;

public class Game {
    SecureRandom r = new SecureRandom();

    // CONSTANTS
    final double NUM_MINUTES_PER_PERIOD = 12;
    final int NUM_PERIODS = 4;

    double currentPeriodTimeLeft;
    Team homeTeam;
    Team awayTeam;
    double possessionLength;
    char possession = 'A'; // A is team A, B is team B
    int overtimes = 0;

    public Game(Team homeTeam, Team awayTeam) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
    }

    void regulationSimulation() {

        determinePossession();
        for (int i = 0; i < NUM_PERIODS; i++) {
            currentPeriodTimeLeft = NUM_MINUTES_PER_PERIOD * 60;

            if (i == 2) determinePossession();
            while (currentPeriodTimeLeft > 0) {
                possessionLength = Math.round(r.nextDouble(0, 24.1) * 10.0) / 10.0; // rounded to 1 decimal point
                currentPeriodTimeLeft -= possessionLength;

                double fieldGoalPercentageRange = r.nextDouble(0, 1.01);
                switch (possession) {
                    case 'A':
                        if (fieldGoalPercentageRange > 0 && fieldGoalPercentageRange < 0.50) homeTeam.setScore(homeTeam.getScore() + 2);
                        possession = 'B';
                        break;
                    case 'B':
                        if (fieldGoalPercentageRange > 0 && fieldGoalPercentageRange < 0.50) awayTeam.setScore(awayTeam.getScore() + 2);
                        possession = 'A';
                        break;
                }
            }
        }
    }

    void overtimeSimulation() {
        while (homeTeam.getScore() == awayTeam.getScore()) {
            overtimes++;
            currentPeriodTimeLeft = (NUM_MINUTES_PER_PERIOD - 7) * 60;

            determinePossession();
            while (currentPeriodTimeLeft > 0) {

                possessionLength = Math.round(r.nextDouble(0, 24.1) * 10.0) / 10.0; // rounded to 1 decimal point
                currentPeriodTimeLeft -= possessionLength;

                double fieldGoalPercentage = r.nextDouble(0, 1.01);
                switch (possession) {
                    case 'A':
                        if (fieldGoalPercentage > 0 && fieldGoalPercentage < 0.50) homeTeam.setScore(homeTeam.getScore() + 2);
                        possession = 'B';
                        break;
                    case 'B':
                        if (fieldGoalPercentage > 0 && fieldGoalPercentage < 0.50) awayTeam.setScore(awayTeam.getScore() + 2);
                        possession = 'A';
                        break;
                }
            }
        }
    }

    void printScore() {
        if (overtimes > 0) {
            System.out.println("FINAL/"+overtimes+"OT" + "\n" + homeTeam.getFullName() + ": " + homeTeam.getScore() + "\n" + awayTeam.getFullName() + ": " + awayTeam.getScore());
            System.out.println();
        } else {
            System.out.println(homeTeam.getFullName() + ": " + homeTeam.getScore() + "\n" + awayTeam.getFullName() + ": " + awayTeam.getScore());
            System.out.println();
        }
    }

    void determinePossession() {
        int coin = r.nextInt(0, 2);
        if (coin == 0) {
            possession = 'A';
        } else {
            possession = 'B';
        }
    }

    void fullGameSimulation() {
        regulationSimulation();
        overtimeSimulation();
    }

    void reset() {
        homeTeam.setScore(0);
        awayTeam.setScore(0);
        overtimes = 0;
    }

    // Getters
    public int getTeamAScore() {
        return homeTeam.getScore();
    }

    public int getTeamBScore() {
        return awayTeam.getScore();
    }

    public int getOvertimes() {
        return overtimes;
    }
}
