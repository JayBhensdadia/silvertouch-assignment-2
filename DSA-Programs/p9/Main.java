
import java.util.*;

public class Main {

	static int one, zero;

	static boolean flag = true;

	static void BipartiteColor(int v, List<List<Integer>> g,List<Integer> colr, int paint) {

		
		if (colr.get(v) != -1) {

			if (colr.get(v) != paint)
				flag = false;
			return;
		}

		
		colr.set(v, paint);

		
		if (paint == 1)
			one++;
		
		else
			zero++;

		
		for (int child : g.get(v)) {

			BipartiteColor(child, g, colr, (paint == 1) ? 0 : 1);
		}
	}

	static void maximumSoldier(int N, List<List<Integer>> arr) {
	
		List<List<Integer>> g = new ArrayList<>();
		for (int i = 0; i < 20001; i++) {
			g.add(new ArrayList<>());
		}

		
		Set<Integer> st = new HashSet<>();
		for (int i = 0; i < N; i++) {

			
			int u, v;
			u = arr.get(i).get(0);
			v = arr.get(i).get(1);

			
			g.get(u).add(v);
			g.get(v).add(u);

			
			st.add(u);
			st.add(v);
		}

	
		List<Integer> soldiers = new ArrayList<>(st);

		int ans = 0;

		List<Integer> colr = new ArrayList<>(Collections.nCopies(20001, -1));

		for (int e : soldiers) {

			
			if (colr.get(e) != -1)
				continue;

			one = 0;
			zero = 0;

			BipartiteColor(e, g, colr, 0);

			ans += Math.max(one, zero);
		}
		if (flag)
			System.out.println(ans);
		else
			System.out.println(-1);
	}

	
	public static void main(String[] args) {
		
		// int N = 4;
		// List<List<Integer>> arr = new ArrayList<>();
		// arr.add(Arrays.asList(1, 2));
		// arr.add(Arrays.asList(2, 3));
		// arr.add(Arrays.asList(2, 4));
		// arr.add(Arrays.asList(2, 5));

        int N = 8;
		List<List<Integer>> arr = new ArrayList<>();
		arr.add(Arrays.asList(1, 2));
		arr.add(Arrays.asList(2, 3));
		arr.add(Arrays.asList(3, 4));
		arr.add(Arrays.asList(3, 5));
		arr.add(Arrays.asList(6, 7));
		arr.add(Arrays.asList(7, 8));
		arr.add(Arrays.asList(7, 9));
		arr.add(Arrays.asList(7, 10));

        // int N = 3;
		// List<List<Integer>> arr = new ArrayList<>();
		// arr.add(Arrays.asList(1, 2));
		// arr.add(Arrays.asList(2, 3));
		// arr.add(Arrays.asList(3, 1));

		maximumSoldier(N, arr);
	}
}
