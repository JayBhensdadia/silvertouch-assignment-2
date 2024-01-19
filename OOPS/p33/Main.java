import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

interface Task {
    void execute();
}

class WorkerThread extends Thread {
    private final BlockingQueue<Task> taskQueue;

    public WorkerThread(BlockingQueue<Task> taskQueue) {
        this.taskQueue = taskQueue;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Task task = taskQueue.take();
                task.execute();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}

public class Main {
    private final BlockingQueue<Task> taskQueue;
    private final WorkerThread[] workers;

    public Main(int numThreads) {
        this.taskQueue = new LinkedBlockingQueue<>();
        this.workers = new WorkerThread[numThreads];

        for (int i = 0; i < numThreads; i++) {
            workers[i] = new WorkerThread(taskQueue);
            workers[i].start();
        }
    }

    public void submitTask(Task task) {
        try {
            taskQueue.put(task);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void shutdown() {
        for (WorkerThread worker : workers) {
            worker.interrupt();
        }
    }

    public static void main(String[] args) {

        Main threadPool = new Main(3);

        for (int i = 0; i < 10; i++) {
            final int taskId = i;
            threadPool.submitTask(() -> System.out
                    .println("Task " + taskId + " executed by thread " + Thread.currentThread().getName()));
        }

        threadPool.shutdown();
    }
}
