public class Team {
    String city;
    String team;
    int score;

    public Team(String city, String team) {
        this.city = city;
        this.team = team;
        score = 0;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getFullName() {
        return this.city + " " + this.team;
    }
}
