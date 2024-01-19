import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


enum PieceType {
    KING, QUEEN, ROOK, BISHOP, KNIGHT, PAWN
}


enum PlayerColor {
    WHITE, BLACK
}


class Piece {
    private PieceType type;
    private PlayerColor color;

    public Piece(PieceType type, PlayerColor color) {
        this.type = type;
        this.color = color;
    }

    public PieceType getType() {
        return type;
    }

    public PlayerColor getColor() {
        return color;
    }

    @Override
    public String toString() {
        String colorString = (color == PlayerColor.WHITE) ? "W" : "B";
        String typeString = "";
        switch (type) {
            case KING:
                typeString = "K";
                break;
            case QUEEN:
                typeString = "Q";
                break;
            case ROOK:
                typeString = "R";
                break;
            case BISHOP:
                typeString = "B";
                break;
            case KNIGHT:
                typeString = "N";
                break;
            case PAWN:
                typeString = "P";
                break;
        }
        return colorString + typeString;
    }
}


class Board {
    private Map<String, Piece> pieces;

    public Board() {
        this.pieces = new HashMap<>();
    }

    public void placePiece(int row, int col, Piece piece) {
        String position = getPositionKey(row, col);
        pieces.put(position, piece);
    }

    public void displayBoard() {
        for (int row = 8; row >= 1; row--) {
            for (int col = 1; col <= 8; col++) {
                String position = getPositionKey(row, col);
                Piece piece = pieces.get(position);
                if (piece != null) {
                    System.out.print(piece + " ");
                } else {
                    System.out.print(". ");
                }
            }
            System.out.println();
        }
    }

    public Piece getPieceAtPosition(int row, int col) {
        String position = getPositionKey(row, col);
        return pieces.get(position);
    }

    private String getPositionKey(int row, int col) {
        return row + "-" + col;
    }
}


class Player {
    private String name;
    private PlayerColor color;

    public Player(String name, PlayerColor color) {
        this.name = name;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public PlayerColor getColor() {
        return color;
    }
}


class Game {
    private Board board;
    private Player player1;
    private Player player2;
    private Player currentPlayer;

    public Game(String player1Name, String player2Name) {
        this.board = new Board();
        this.player1 = new Player(player1Name, PlayerColor.WHITE);
        this.player2 = new Player(player2Name, PlayerColor.BLACK);
        this.currentPlayer = player1; // Start with player1
        initializeBoard();
    }

    private void initializeBoard() {
        
        
        board.placePiece(1, 1, new Piece(PieceType.ROOK, PlayerColor.WHITE));
        

        board.placePiece(8, 8, new Piece(PieceType.ROOK, PlayerColor.BLACK));
        
    }

    public void displayBoard() {
        board.displayBoard();
    }

    public boolean makeMove(Player player, int fromRow, int fromCol, int toRow, int toCol) {
        
        
        Piece selectedPiece = board.getPieceAtPosition(fromRow, fromCol);
        if (selectedPiece != null && player.getColor() == selectedPiece.getColor()) {
            
            board.placePiece(fromRow, fromCol, null);
            board.placePiece(toRow, toCol, selectedPiece);
            return true;
        } else {
            System.out.println("Invalid move! Try again.");
            return false;
        }
    }


    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void switchTurn() {
        currentPlayer = (currentPlayer == player1) ? player2 : player1;
    }

    
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        
        Game chessGame = new Game("Player1", "Player2");

        
        while (true) {
            
            chessGame.displayBoard();

            
            Player currentPlayer = chessGame.getCurrentPlayer();
            System.out.println(currentPlayer.getName() + "'s turn. Enter your move:");

            System.out.print("From Row: ");
            int fromRow = scanner.nextInt();
            System.out.print("From Col: ");
            int fromCol = scanner.nextInt();
            System.out.print("To Row: ");
            int toRow = scanner.nextInt();
            System.out.print("To Col: ");
            int toCol = scanner.nextInt();

            
            boolean validMove = chessGame.makeMove(currentPlayer, fromRow, fromCol, toRow, toCol);

            
            if (validMove) {
                chessGame.switchTurn();
            }
        }
    }
}

