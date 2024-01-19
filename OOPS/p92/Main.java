import java.util.ArrayList;
import java.util.List;

class Team {
    private String teamName;
    private int teamScore;

    public Team(String teamName) {
        this.teamName = teamName;
        this.teamScore = 0;
    }

    public String getTeamName() {
        return teamName;
    }

    public int getTeamScore() {
        return teamScore;
    }

    public void updateTeamScore(int points) {
        teamScore += points;
    }
}

class Match {
    private Team team1;
    private Team team2;
    private int team1Score;
    private int team2Score;

    public Match(Team team1, Team team2) {
        this.team1 = team1;
        this.team2 = team2;
        this.team1Score = 0;
        this.team2Score = 0;
    }

    public Team getTeam1() {
        return team1;
    }

    public Team getTeam2() {
        return team2;
    }

    public int getTeam1Score() {
        return team1Score;
    }

    public int getTeam2Score() {
        return team2Score;
    }

    public void updateMatchResult(int team1Score, int team2Score) {
        this.team1Score = team1Score;
        this.team2Score = team2Score;
    }
}

class Tournament {
    private List<Match> matches;

    public Tournament() {
        this.matches = new ArrayList<>();
    }

    public void addMatch(Match match) {
        matches.add(match);
    }

    public void conductTournament() {
        for (Match match : matches) {
            System.out.println("Conducting match between " +
                    match.getTeam1().getTeamName() + " and " +
                    match.getTeam2().getTeamName());

            // Simulating a match with random scores
            int team1Score = (int) (Math.random() * 5);
            int team2Score = (int) (Math.random() * 5);

            System.out.println("Match Result: " +
                    match.getTeam1().getTeamName() + " " + team1Score + " vs " +
                    match.getTeam2().getTeamName() + " " + team2Score);

            // Update match result
            match.updateMatchResult(team1Score, team2Score);

            // Update team scores
            match.getTeam1().updateTeamScore(team1Score);
            match.getTeam2().updateTeamScore(team2Score);
        }
    }

    public Team determineWinner() {
        Team winner = null;
        int maxScore = Integer.MIN_VALUE;

        for (Match match : matches) {
            if (match.getTeam1().getTeamScore() > maxScore) {
                maxScore = match.getTeam1().getTeamScore();
                winner = match.getTeam1();
            }

            if (match.getTeam2().getTeamScore() > maxScore) {
                maxScore = match.getTeam2().getTeamScore();
                winner = match.getTeam2();
            }
        }

        return winner;
    }
}

public class Main {
    public static void main(String[] args) {
        Team teamA = new Team("Team A");
        Team teamB = new Team("Team B");
        Team teamC = new Team("Team C");
        Team teamD = new Team("Team D");

        Match match1 = new Match(teamA, teamB);
        Match match2 = new Match(teamC, teamD);

        Tournament tournament = new Tournament();
        tournament.addMatch(match1);
        tournament.addMatch(match2);

        tournament.conductTournament();

        Team winner = tournament.determineWinner();
        System.out.println("Tournament Winner: " + winner.getTeamName());
    }
}
