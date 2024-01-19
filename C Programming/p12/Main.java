import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

class PlayerHandler implements Runnable {
    private Socket socket;
    private BufferedReader input;
    private PrintWriter output;

    public PlayerHandler(Socket socket) {
        this.socket = socket;
        try {
            this.input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.output = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            output.println("Welcome to the game!");

            String message;
            do {
                message = input.readLine();
                if (message != null) {
                    System.out.println("Received from player: " + message);
                    broadcastMessage("Player " + socket.getPort() + ": " + message);
                }
            } while (message != null && !message.equals("exit"));

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void broadcastMessage(String message) {
    }
}

public class Main {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(8888);

            System.out.println("Server is running. Waiting for players...");

            while (true) {
                Socket playerSocket = serverSocket.accept();
                System.out.println("Player connected: " + playerSocket.getPort());

                PlayerHandler playerHandler = new PlayerHandler(playerSocket);
                Thread playerThread = new Thread(playerHandler);
                playerThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
