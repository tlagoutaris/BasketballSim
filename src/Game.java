import java.security.SecureRandom;

public class Game {
    SecureRandom r = new SecureRandom();

    // CONSTANTS
    final double NUM_MINUTES_PER_PERIOD = 12;
    final double NUM_MINUTES_PER_OVERTIME_PERIOD = 5;
    final int NUM_PERIODS = 4;

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

    void simulateShot(Team team) {
        double shotType = r.nextDouble(0, 1.01);
        double shotSuccess = r.nextDouble(0, 1.01);

        if (shotType <= team.twoPointTendency) {
            // 2PT
            team.setTwoPointAttemptsTotal(team.getTwoPointAttemptsTotal() + 1);
            if (shotSuccess < team.twoPointPercentage) {
                team.setTwoPointMakesTotal(team.getTwoPointMakesTotal() + 1);
                team.setScore(team.getScore() + 2);
            }
        } else {
            // 3PT
            team.setThreePointAttemptsTotal(team.getThreePointAttemptsTotal() + 1);
            if (shotSuccess < team.threePointPercentage) {
                team.setThreePointMakesTotal(team.getThreePointMakesTotal() + 1);
                team.setScore(team.getScore() + 3);
            }
        }
    }

    void simulatePossession() {
        switch (possession) {
            case 'H':
                simulateShot(homeTeam);
                possession = 'A';
                break;
            case 'A':
                simulateShot(awayTeam);
                possession = 'H';
                break;
        }
    }

    void simulatePeriod(double minutes, boolean isOvertime) {
        currentPeriodTimeLeft = minutes * 60;

        while (currentPeriodTimeLeft > 0) {
            possessionLength = Math.round(r.nextDouble(0, 24.1) * 10.0) / 10.0; // rounded to 1 decimal point
            currentPeriodTimeLeft -= possessionLength;
            simulatePossession();
        }

        if (isOvertime) {
            overtimes++;
        }
    }

    void regulationSimulation() {
        determinePossession();
        for (int period = 0; period < NUM_PERIODS; period++) {
            simulatePeriod(NUM_MINUTES_PER_PERIOD, false);
        }
    }

    void overtimeSimulation() {
        determinePossession();
        simulatePeriod(NUM_MINUTES_PER_OVERTIME_PERIOD, true);
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
        awayTeam.setGamesPlayed(homeTeam.getGamesPlayed() + 1);
    }

    void reset() {
        homeTeam.setScore(0);
        awayTeam.setScore(0);
        overtimes = 0;
    }
}
