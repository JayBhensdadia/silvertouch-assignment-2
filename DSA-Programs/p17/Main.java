import java.util.*;

class Main {

	static void permuteRec(String str, int n, int index, String curr)
	{
		if (index == n) {
			return;
		}
		System.out.println(curr);
		for (int i = index + 1; i < n; i++) {

			curr += str.charAt(i);
			permuteRec(str, n, i, curr);

			curr = curr.substring(0, curr.length() - 1);
		}
		return;
	}

	static void powerSet(String str)
	{
		char[] arr = str.toCharArray();
		Arrays.sort(arr);
		permuteRec(new String(arr), str.length(), -1, "");
	}

	public static void main(String[] args)
	{
		String str = "abc";
		powerSet(str);
	}
}

