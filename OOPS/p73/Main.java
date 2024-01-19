import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Game {
    private String gameId;
    private String title;
    private int totalLevels;

    public Game(String gameId, String title, int totalLevels) {
        this.gameId = gameId;
        this.title = title;
        this.totalLevels = totalLevels;
    }

    public String getGameId() {
        return gameId;
    }

    public String getTitle() {
        return title;
    }

    public int getTotalLevels() {
        return totalLevels;
    }
}

class Player {
    private String playerId;
    private String playerName;
    private Map<String, Integer> gameProgress; // Map of game ID to current level

    public Player(String playerId, String playerName) {
        this.playerId = playerId;
        this.playerName = playerName;
        this.gameProgress = new HashMap<>();
    }

    public String getPlayerId() {
        return playerId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void addGameProgress(String gameId, int currentLevel) {
        gameProgress.put(gameId, currentLevel);
    }

    public void removeGameProgress(String gameId) {
        gameProgress.remove(gameId);
    }

    public int getGameProgress(String gameId) {
        return gameProgress.getOrDefault(gameId, 0);
    }
}

class Inventory {
    private List<Game> games;

    public Inventory() {
        this.games = new ArrayList<>();
    }

    public void addGame(Game game) {
        games.add(game);
        System.out.println("Game added to inventory: " + game.getTitle());
    }

    public void removeGame(Game game) {
        games.remove(game);
        System.out.println("Game removed from inventory: " + game.getTitle());
    }

    public List<Game> getGames() {
        return games;
    }
}

public class Main {
    public static void main(String[] args) {
        Inventory inventory = new Inventory();

        Game game1 = new Game("G001", "Super Adventure", 10);
        Game game2 = new Game("G002", "Space Shooter", 8);

        inventory.addGame(game1);
        inventory.addGame(game2);

        Player player1 = new Player("P001", "PlayerOne");
        Player player2 = new Player("P002", "PlayerTwo");

        player1.addGameProgress(game1.getGameId(), 5);
        player2.addGameProgress(game2.getGameId(), 3);

        System.out.println(player1.getPlayerName() + " progress in " + game1.getTitle() + ": " +
                player1.getGameProgress(game1.getGameId()) + " levels");
        System.out.println(player2.getPlayerName() + " progress in " + game2.getTitle() + ": " +
                player2.getGameProgress(game2.getGameId()) + " levels");
    }
}

