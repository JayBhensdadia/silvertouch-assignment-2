import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

class Main {
    public int maxPerformance(int n, int[] speed, int[] efficiency, int k) {
        Integer[] indices = new Integer[n];
        for (int i = 0; i < n; i++) {
            indices[i] = i;
        }
        PriorityQueue<Integer> pq = new PriorityQueue<>(Comparator.comparingInt(a -> speed[a]));
        Arrays.sort(indices, Comparator.comparingInt(a -> efficiency[a]));
        long res = 0, mod = (long) 1e9 + 7;
        for (int i = 0; i < n; i++) {
            int idx = indices[i];
            if (pq.size() < k) {
                pq.add(idx);
                res = Math.max(res, (long) efficiency[idx] * speed[idx]);
            } else {
                int top = pq.poll();
                res = Math.max(res, (long) efficiency[idx] * speed[idx]);
                pq.add(idx);
            }
        }
        return (int) (res % mod);
    }

    public static void main(String[] args) {
        Main solution = new Main();
        int n = 6;
        int[] speed = {2, 10, 3, 1, 5, 8};
        int[] efficiency = {5, 4, 3, 9, 7, 2};
        int k = 2;
        System.out.println(solution.maxPerformance(n, speed, efficiency, k));
    }
}