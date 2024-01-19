import java.util.*;

public class Main {
	
	static int[] parent = new int[1000];
	
	
	static int[] sizes = new int[1000];
	
	
	static List<Integer> st = new ArrayList<>();

	
	static void make(int i) {
		parent[i] = i;
		sizes[i] = 1;
		st.add(1);
	}

	
	static int find(int v) {
		if (v == parent[v]) {
			return v;
		}
		parent[v] = find(parent[v]);
		return parent[v];
	}

	
	static void union(int a, int b) {
		a = find(a);
		b = find(b);
		if (a != b) {
			if (sizes[a] < sizes[b]) {
				int temp = a;
				a = b;
				b = temp;
			}
			parent[b] = a;

			
			st.remove(Integer.valueOf(sizes[a]));

			
			st.remove(Integer.valueOf(sizes[b]));

			
			st.add(sizes[a] + sizes[b]);
			sizes[a] += sizes[b];
		}
	}

	
	public static void main(String[] args) {
		int N = 4;
		int Q = 2;

		
		for (int i = 1; i <= N; i++) {
			make(i);
		}

		int[][] queries = {{1, 2}, {2, 4}};
		for (int i = 0; i < Q; i++) {
			int u = queries[i][0];
			int v = queries[i][1];

			
			union(u, v);

			
			int a = Collections.min(st);

			
			int b = Collections.max(st);
			System.out.println(b - a);
		}
	}
}
