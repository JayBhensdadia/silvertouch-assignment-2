import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class Team {
    private String teamName;

    public Team(String teamName) {
        this.teamName = teamName;
    }

    public String getTeamName() {
        return teamName;
    }
}

class Match {
    private Team team1;
    private Team team2;
    private Team winner;

    public Match(Team team1, Team team2) {
        this.team1 = team1;
        this.team2 = team2;
    }

    public Team getTeam1() {
        return team1;
    }

    public Team getTeam2() {
        return team2;
    }

    public Team getWinner() {
        return winner;
    }

    public void playMatch() {
        Random random = new Random();
        // Simulate match result: 0 for team1 wins, 1 for team2 wins
        int result = random.nextInt(2);

        if (result == 0) {
            winner = team1;
        } else {
            winner = team2;
        }
    }
}

class Tournament {
    private List<Team> teams;
    private List<Match> matches;
    private Team winner;

    public Tournament(List<Team> teams) {
        this.teams = teams;
        this.matches = new ArrayList<>();
        initializeMatches();
    }

    public List<Team> getTeams() {
        return teams;
    }

    public List<Match> getMatches() {
        return matches;
    }

    public Team getWinner() {
        return winner;
    }

    public void conductTournament() {
        for (Match match : matches) {
            match.playMatch();
            System.out.println("Match Result: " +
                    match.getTeam1().getTeamName() + " vs " +
                    match.getTeam2().getTeamName() + " Winner: " +
                    match.getWinner().getTeamName());
        }

        determineWinner();
        System.out.println("Tournament Winner: " + winner.getTeamName());
    }

    private void initializeMatches() {
        int numTeams = teams.size();
        if (numTeams < 2 || !isPowerOfTwo(numTeams)) {
            System.out.println("Invalid number of teams for a tournament.");
            return;
        }

        while (numTeams > 1) {
            List<Match> roundMatches = new ArrayList<>();
            for (int i = 0; i < numTeams; i += 2) {
                Team team1 = teams.get(i);
                Team team2 = teams.get(i + 1);
                Match match = new Match(team1, team2);
                roundMatches.add(match);
            }
            matches.addAll(roundMatches);
            teams = extractWinners(roundMatches);
            numTeams = teams.size();
        }
    }

    private List<Team> extractWinners(List<Match> roundMatches) {
        List<Team> winners = new ArrayList<>();
        for (Match match : roundMatches) {
            winners.add(match.getWinner());
        }
        return winners;
    }

    private void determineWinner() {
        if (matches.size() == 1) {
            winner = matches.get(0).getWinner();
        }
    }

    private boolean isPowerOfTwo(int n) {
        return n > 0 && (n & (n - 1)) == 0;
    }
    
}

public class Main {
    public static void main(String[] args) {
        // Create teams
        Team team1 = new Team("Team A");
        Team team2 = new Team("Team B");
        Team team3 = new Team("Team C");
        Team team4 = new Team("Team D");

        // Create a tournament with the teams
        List<Team> teams = List.of(team1, team2, team3, team4);
        Tournament tournament = new Tournament(teams);

        // Conduct the tournament
        tournament.conductTournament();
    }
}
