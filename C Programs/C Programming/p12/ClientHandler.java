import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private static int clientCount = 0;

    private Socket clientSocket;
    private GameServer gameServer;
    private PrintWriter out;
    private BufferedReader in;
    private String clientName;

    public ClientHandler(Socket clientSocket, GameServer gameServer) {
        this.clientSocket = clientSocket;
        this.gameServer = gameServer;
        this.clientName = "Player" + (++clientCount);

        try {
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getClientName() {
        return clientName;
    }

    public void sendMessage(String message) {
        out.println(message);
    }

    @Override
    public void run() {
        try {
            out.println("Welcome to the game, " + clientName + "!");
            gameServer.broadcastMessage(clientName + " has joined the game!", this);

            String clientInput;
            while ((clientInput = in.readLine()) != null) {
                if (clientInput.equalsIgnoreCase("exit")) {
                    break;
                }

                String messageToSend = clientName + ": " + clientInput;
                gameServer.broadcastMessage(messageToSend, this);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            gameServer.removeClient(this);
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
