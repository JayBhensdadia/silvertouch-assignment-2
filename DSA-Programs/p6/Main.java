import java.util.*;

public class Main {

	static int[] parent = new int[26];

	static int find(int v)
	{
		if (v == parent[v])
			return v;
		return parent[v] = find(parent[v]);
	}

	static void union(int a, int b)
	{
		a = find(a);
		b = find(b);
		if (a != b) {
			parent[b] = a;
		}
	}

	static String
	satisfactoryEquation(ArrayList<String> arr)
	{

		for (int i = 0; i < 26; i++)
			parent[i] = i;

		
		for (String e : arr) {
			int a = e.charAt(0) - 'a';
			int b = e.charAt(3) - 'a';
			char c = e.charAt(1);

			
			if (c == '=') {

				
				
				union(a, b);
			}
		}

		
		for (String e : arr) {

			
			if (e.charAt(1) == '!') {
				int a = e.charAt(0) - 'a';
				int b = e.charAt(3) - 'a';

				if (find(a) == find(b))
					return "False";
			}
		}
		return "True";
	}

	
	public static void main(String[] args)
	{
		int N = 3;
		ArrayList<String> arr = new ArrayList<String>(Arrays.asList("a==b", "a==c", "b!=c"));
		System.out.println(satisfactoryEquation(arr));
	}
}

