import java.util.*;

public class Main {

	boolean found[];

	int N;
	int cap[][];
	int flow[][];
	int cost[][];
	int dad[], dist[], pi[];

	static final int INF
		= Integer.MAX_VALUE / 2 - 1;

	boolean search(int src, int sink)
	{
		Arrays.fill(found, false);
		Arrays.fill(dist, INF);
		dist[src] = 0;
		while (src != N) {

			int best = N;
			found[src] = true;

			for (int k = 0; k < N; k++) {
				if (found[k])
					continue;
				if (flow[k][src] != 0) {
					int val
						= dist[src] + pi[src]
						- pi[k] - cost[k][src];
					if (dist[k] > val) {
						dist[k] = val;
						dad[k] = src;
					}
				}

				if (flow[src][k] < cap[src][k]) {

					int val = dist[src] + pi[src]
							- pi[k] + cost[src][k];
					if (dist[k] > val) {

						dist[k] = val;
						dad[k] = src;
					}
				}

				if (dist[k] < dist[best])
					best = k;
			}
			src = best;
		}

		for (int k = 0; k < N; k++)
			pi[k]
				= Math.min(pi[k] + dist[k],
						INF);
		return found[sink];
	}
	int[] getMaxFlow(int cap[][], int cost[][],
					int src, int sink)
	{

		this.cap = cap;
		this.cost = cost;

		N = cap.length;
		found = new boolean[N];
		flow = new int[N][N];
		dist = new int[N + 1];
		dad = new int[N];
		pi = new int[N];

		int totflow = 0, totcost = 0;
		while (search(src, sink)) {
			int amt = INF;
			for (int x = sink; x != src; x = dad[x])

				amt = Math.min(amt,
							flow[x][dad[x]] != 0
								? flow[x][dad[x]]
								: cap[dad[x]][x]
										- flow[dad[x]][x]);

			for (int x = sink; x != src; x = dad[x]) {

				if (flow[x][dad[x]] != 0) {
					flow[x][dad[x]] -= amt;
					totcost -= amt * cost[x][dad[x]];
				}
				else {
					flow[dad[x]][x] += amt;
					totcost += amt * cost[dad[x]][x];
				}
			}
			totflow += amt;
		}
		return new int[] { totflow, totcost };
	}
	public static void main(String args[])
	{
		Main flow = new Main();

		int s = 0, t = 4;

		int cap[][] = { { 0, 3, 1, 0, 3 },
						{ 0, 0, 2, 0, 0 },
						{ 0, 0, 0, 1, 6 },
						{ 0, 0, 0, 0, 2 },
						{ 0, 0, 0, 0, 0 } };

		int cost[][] = { { 0, 1, 0, 0, 2 },
						{ 0, 0, 0, 3, 0 },
						{ 0, 0, 0, 0, 0 },
						{ 0, 0, 0, 0, 1 },
						{ 0, 0, 0, 0, 0 } };

		int ret[] = flow.getMaxFlow(cap, cost, s, t);

		System.out.println(ret[0] + " " + ret[1]);
	}
}
