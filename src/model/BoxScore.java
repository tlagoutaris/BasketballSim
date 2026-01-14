package model;

public class BoxScore {
    Team home;
    Team away;
    int homeScore;
    int awayScore;

    public BoxScore(Team home, Team away) {
        this.home = home;
        this.away = away;

        initializeInformation();
    }

    public void initializeInformation() {
        this.homeScore = 0;
        this.awayScore = 0;
    }

    // Getters
    public int getHomeScore() {
        return homeScore;
    }

    public int getAwayScore() {
        return awayScore;
    }

    // Setters
    public void setScore(Player player, int score) {
        if (player.getCurrentTeam().equals(home)) {
            this.setHomeScore(this.getHomeScore() + score);
        }

        else if (player.getCurrentTeam().equals(away)) {
            this.setAwayScore(this.getAwayScore() + score);
        }
    }

    public void setHomeScore(int homeScore) {
        this.homeScore = homeScore;
    }

    public void setAwayScore(int awayScore) {
        this.awayScore = awayScore;
    }
}
