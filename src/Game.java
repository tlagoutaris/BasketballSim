import java.security.SecureRandom;

public class Game {
    SecureRandom r = new SecureRandom();

    double currentPeriodTimeLeft;
    Team homeTeam;
    Team awayTeam;
    double possessionLength;
    char possession = 'H'; // H is home team, A is away team
    int overtimes = 0;

    public Game(Team homeTeam, Team awayTeam) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
    }

    void simulatePossession() {
        switch (possession) {
            case 'H':
                homeTeam.simulateShot(awayTeam);
                possession = 'A';
                break;
            case 'A':
                awayTeam.simulateShot(homeTeam);
                possession = 'H';
                break;
        }
    }

    void simulatePeriod(double minutes, boolean isOvertime) {
        currentPeriodTimeLeft = minutes * 60;

        while (currentPeriodTimeLeft > 0) {
            possessionLength = BoundedNormalDistribution.generateBoundedNormal(Config.BASE_AVERAGE_POSSESSION_SECONDS, Config.BASE_AVERAGE_POSSESSION_STDDEV, 0, Config.NUM_SECONDS_POSSESSION);
            currentPeriodTimeLeft -= possessionLength;
            simulatePossession();
        }

        if (isOvertime) {
            overtimes++;
        }
    }

    void regulationSimulation() {
        determinePossession();
        for (int period = 0; period < Config.NUM_PERIODS; period++) {
            simulatePeriod(Config.NUM_MINUTES_PER_PERIOD, false);
        }
    }

    void overtimeSimulation() {
        determinePossession();
        simulatePeriod(Config.NUM_MINUTES_PER_OVERTIME_PERIOD, true);
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
            possession = 'H';
        } else {
            possession = 'A';
        }
    }

    void fullGameSimulation() {
        regulationSimulation();
        while (homeTeam.getScore() == awayTeam.getScore()) {
            overtimeSimulation();
        }
        homeTeam.setPointsTotal(homeTeam.getPointsTotal() + homeTeam.getScore());
        awayTeam.setPointsTotal(awayTeam.getPointsTotal() + awayTeam.getScore());
        if (homeTeam.getScore() > awayTeam.getScore()) {
            homeTeam.wins++;
            awayTeam.losses++;
        } else {
            homeTeam.losses++;
            awayTeam.wins++;
        }

        homeTeam.setGamesPlayed(homeTeam.getGamesPlayed() + 1);
        awayTeam.setGamesPlayed(awayTeam.getGamesPlayed() + 1);
    }

    void reset() {
        homeTeam.setScore(0);
        awayTeam.setScore(0);
        overtimes = 0;
    }
}
