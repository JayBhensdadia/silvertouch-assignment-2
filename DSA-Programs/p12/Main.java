import java.util.*;

public class Main {
	static Map<Pair, Pair> parent;

	static class Pair {
		int first;
		int second;

		public Pair(int first, int second) {
			this.first = first;
			this.second = second;
		}

		@Override
		public int hashCode() {
			return Objects.hash(first, second);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null || getClass() != obj.getClass())
				return false;
			Pair other = (Pair) obj;
			return first == other.first && second == other.second;
		}
	}

	static void make(Pair i) {
		parent.put(i, i);
	}

	static Pair find(Pair v) {
		if (parent.get(v).equals(v))
			return v;
		return parent.put(v, find(parent.get(v)));
	}

	static void Union(Pair a, Pair b) {
		a = find(a);
		b = find(b);
		if (!a.equals(b)) {
			parent.put(b, a);
		}
	}

	static int solve(List<Integer> arr, int n) {
		parent = new HashMap<>();

		for (int i = 0; i < n; i++) {
			Pair p = new Pair(2 * i, 2 * i + 1);
			make(p);
		}

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (i == j)
					continue;

				Pair a = new Pair(2 * i, 2 * i + 1);

				Pair b = new Pair(2 * j, 2 * j + 1);

				if ((arr.get(2 * i) / 2 == arr.get(2 * j) / 2)
						|| (arr.get(2 * i) / 2 == arr.get(2 * j + 1) / 2)
						|| (arr.get(2 * i + 1) / 2 == arr.get(2 * j) / 2)
						|| (arr.get(2 * i + 1) / 2 == arr.get(2 * j + 1) / 2)) {

					Union(a, b);
				}
			}
		}

		Map<Pair, Integer> comp = new HashMap<>();

		for (int i = 0; i < n; i++) {
			comp.put(find(new Pair(2 * i, 2 * i + 1)), comp.getOrDefault(find(new Pair(2 * i, 2 * i + 1)), 0) + 1);
		}

		return n - comp.size();
	}

	public static void main(String[] args) {
		int N = 2;
		List<Integer> arr = Arrays.asList(3, 0, 2, 1);

		// Function call
		System.out.println(solve(arr, N));
	}
}


