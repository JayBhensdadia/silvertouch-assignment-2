import java.util.Arrays;
import java.util.Comparator;

class Job implements Comparable<Job> {
    int id, deadline, profit;

    public Job(int id, int deadline, int profit) {
        this.id = id;
        this.deadline = deadline;
        this.profit = profit;
    }

    @Override
    public int compareTo(Job other) {
        return Integer.compare(other.profit, this.profit);
    }
}

class DisjointSet {
    private int[] parent;

    public DisjointSet(int size) {
        parent = new int[size + 1]; 
        Arrays.fill(parent, -1);
    }

    public int find(int x) {
        if (parent[x] == -1) {
            return x;
        }
        return parent[x] = find(parent[x]);
    }

    public void union(int x, int y) {
        int rootX = find(x);
        int rootY = find(y);

        if (rootX != rootY) {
            parent[rootX] = rootY;
        }
    }
}

public class Main {
    public static void printJobSequence(Job[] jobs) {
        Arrays.sort(jobs);

        int maxDeadline = Arrays.stream(jobs).mapToInt(job -> job.deadline).max().orElse(0);
        DisjointSet disjointSet = new DisjointSet(maxDeadline);

        int totalProfit = 0;
        int scheduledJobs = 0;

        System.out.println("Job Sequence:");

        for (Job job : jobs) {
            int availableSlot = disjointSet.find(job.deadline);

            if (availableSlot > 0) {
                disjointSet.union(availableSlot, availableSlot - 1);
                totalProfit += job.profit;
                scheduledJobs++;
                System.out.println("Job " + job.id + " (Profit: " + job.profit + ") scheduled on slot " + availableSlot);
            }
        }

        System.out.println("Total Profit: " + totalProfit);
        System.out.println("Number of Scheduled Jobs: " + scheduledJobs);
    }

    public static void main(String[] args) {
        Job[] jobs = {
                new Job(1, 4, 20),
                new Job(2, 1, 10),
                new Job(3, 1, 40),
                new Job(4, 1, 30)
        };

        printJobSequence(jobs);
    }
}
