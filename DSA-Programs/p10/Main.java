
import java.util.*;

public class Main {

	static int[] parent;

	static int[] sizez;

	static int[] freq;

	static TreeSet<Integer> st;

	static void make(int i) {
		parent[i] = i;
		sizez[i] = 1;
		freq[1]++;
	}

	static int find(int v) {
		if (v == parent[v])
			return v;

	
	
		return parent[v] = find(parent[v]);
	}

	static void Union(int a, int b) {
		a = find(a);
		b = find(b);
		if (a != b) {
			if (sizez[a] < sizez[b]) {
				int temp = a;
				a = b;
				b = temp;
			}
		
			
			freq[sizez[a]]--;
			if (freq[sizez[a]] == 0)
				st.remove(sizez[a]);

		
		
			freq[sizez[b]]--;
			if (freq[sizez[b]] == 0)
				st.remove(sizez[b]);
	
			parent[b] = a;
			sizez[a] += sizez[b];
		
		
			freq[sizez[a]]++;

		
			if (freq[sizez[a]] == 1)
				st.add(sizez[a]);
		}
	}


	public static void main(String[] args) {
	
		int N = 4;
		int Q = 2;
		int[][] queries = { { 1, 2 }, { 2, 4 } };
		
		st = new TreeSet<>();
		st.add(1);

		parent = new int[N + 1];
		sizez = new int[N + 1];
		freq = new int[N + 1];

	
		for (int i = 1; i <= N; i++) {
			make(i);
		}

		for (int i = 0; i < Q; i++) {
			int u = queries[i][0];
			int v = queries[i][1];
			Union(u, v);
			int ans = Integer.MAX_VALUE;
			int pre = Integer.MIN_VALUE;

			for (int e : st) {
			
			
				if (freq[e] > 1) {
					ans = 0;
					break;
				}
				if (pre != Integer.MIN_VALUE) {
					ans = Math.min(ans, e - pre);
				}
				pre = e;
			}

			System.out.println(ans);
		}
	}
}
