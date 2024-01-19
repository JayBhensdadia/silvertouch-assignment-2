import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

interface MessageListener {
    void onMessageReceived(String message);
}

class Server {
    private final int port;
    private final ExecutorService executorService;
    private final MessageListener messageListener;

    public Server(int port, MessageListener messageListener) {
        this.port = port;
        this.executorService = Executors.newFixedThreadPool(10);
        this.messageListener = messageListener;
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server started on port " + port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected: " + clientSocket.getInetAddress());

                ClientHandler clientHandler = new ClientHandler(clientSocket, messageListener);
                executorService.submit(clientHandler);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        executorService.shutdownNow();
    }
}

class ClientHandler implements Runnable {
    private final Socket clientSocket;
    private final MessageListener messageListener;

    public ClientHandler(Socket clientSocket, MessageListener messageListener) {
        this.clientSocket = clientSocket;
        this.messageListener = messageListener;
    }

    @Override
    public void run() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()))) {

            String message;
            while ((message = reader.readLine()) != null) {
                System.out.println("Received message from " + clientSocket.getInetAddress() + ": " + message);
                messageListener.onMessageReceived(message);
                writer.write("Server received your message: " + message + "\n");
                writer.flush();
            }

        } catch (IOException e) {
            System.out.println("Client disconnected: " + clientSocket.getInetAddress());
        }
    }
}

class Client {
    private final String host;
    private final int port;

    public Client(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void sendMessage(String message) {
        try (Socket socket = new Socket(host, port);
             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))) {

            System.out.println("Connected to server at " + host + ":" + port);

            writer.write(message + "\n");
            writer.flush();

            String response = reader.readLine();
            System.out.println("Server response: " + response);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

public class Main {
    public static void main(String[] args) {
        
        MessageListener serverMessageListener = System.out::println;
        Server server = new Server(8080, serverMessageListener);
        new Thread(server::start).start();

        
        Client client = new Client("localhost", 8080);
        client.sendMessage("Hello, Server!");

        
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            server.stop();
        }
    }
}

