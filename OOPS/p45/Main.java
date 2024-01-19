import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

class Task implements Serializable {
    private int[] array;
    private int startIndex;
    private int endIndex;

    public Task(int[] array, int startIndex, int endIndex) {
        this.array = array;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
    }

    public int compute() {
        int sum = 0;
        for (int i = startIndex; i < endIndex; i++) {
            sum += array[i];
        }
        return sum;
    }
}

class TaskDistributor extends Thread {
    private Socket workerSocket;
    private int[] array;
    private int startIndex;
    private int endIndex;

    public TaskDistributor(Socket workerSocket, int[] array, int startIndex, int endIndex) {
        this.workerSocket = workerSocket;
        this.array = array;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
    }

    @Override
    public void run() {
        try {
            
            ObjectOutputStream oos = new ObjectOutputStream(workerSocket.getOutputStream());
            oos.writeObject(new Task(array, startIndex, endIndex));

            
            oos.close();
            workerSocket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class WorkerNode {
    public static void main(String[] args) {
        String masterAddress = "localhost";
        int masterPort = 5000;

        try {
            
            Socket socket = new Socket(masterAddress, masterPort);
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

            
            Task task = (Task) ois.readObject();

            
            int result = task.compute();

            
            oos.writeObject(result);

            
            ois.close();
            oos.close();
            socket.close();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

public class Main {
    public static void main(String[] args) {
        int port = 5000; 
        List<Socket> workerSockets = new ArrayList<>();

        try {
            
            ServerSocket serverSocket = new ServerSocket(port);

            
            for (int i = 0; i < 3; i++) { 
                Socket workerSocket = serverSocket.accept();
                workerSockets.add(workerSocket);
            }

            
            int[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
            int chunkSize = array.length / workerSockets.size();
            int startIndex = 0;

            for (Socket workerSocket : workerSockets) {
                
                new TaskDistributor(workerSocket, array, startIndex, startIndex + chunkSize).start();
                startIndex += chunkSize;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
