public class StatisticsService {

    public StatisticsService() {

    }

    public void recordShot(Player shooter, ShotResult result) {
        shooter.getStatistics().recordShotAttempt(result);
        shooter.getCurrentTeam().getStatistics().recordShotAttempt(result);
    }

    public void recordSteal(Player defensivePlayer, StealResult result) {
        defensivePlayer.getStatistics().recordStealAttempt(result);
        defensivePlayer.getCurrentTeam().getStatistics().recordStealAttempt(result);
    }

    public void recordFreeThrows(Player offensivePlayer, FreeThrowResult result) {
        offensivePlayer.getStatistics().recordFreeThrowAttempt(result);
        offensivePlayer.getCurrentTeam().getStatistics().recordFreeThrowAttempt(result);
    }

    public void recordFoul(Player defensivePlayer, StealResult result) {
        defensivePlayer.getStatistics().recordFoul(result);
        defensivePlayer.getCurrentTeam().getStatistics().recordFoul(result);
    }

    public void recordFoul(Player defensivePlayer, ShotResult result) {
        defensivePlayer.getStatistics().recordFoul(result);
        defensivePlayer.getCurrentTeam().getStatistics().recordFoul(result);

    }

    public void recordTurnover(Player offensivePlayer) {
        offensivePlayer.getStatistics().recordTurnover();
        offensivePlayer.getCurrentTeam().getStatistics().recordTurnover();
    }
}
