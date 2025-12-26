import java.security.SecureRandom;

public class Team {
    SecureRandom r = new SecureRandom();

    String city;
    String team;

    // Players
    Player[] roster = new Player[5];

    // Totals
    TeamStatistics statistics;

    public Team(String city, String team) {
        // Team information
        this.city = city;
        this.team = team;
        this.statistics = new TeamStatistics(this);
        
        // Roster generation
        this.generateRoster();
    }

    // Getters & Setters
    TeamStatistics getStatistics() {
        return this.statistics;
    }

    String getFullName() {
        return this.city + " " + this.team;
    }

    void generateRoster() {
        for (int i = 0; i < this.roster.length; i++) {
            this.roster[i] = new Player(this);
        }
    }

    Player[] getRoster() {
        return this.roster;
    }

    void printRoster() {
        System.out.printf("\n%s %s Roster: %n", this.city, this.team);

        for (Player p : roster) {
            System.out.print(p.getFullName());
            System.out.println(p.getStats());
        }
    }
}
