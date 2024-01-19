import java.util.ArrayList;
import java.util.List;

class Main {
	List<List<String>> ans = new ArrayList<>();

	
	boolean checkPalindrome(List<String> currPartition) {
		for (String s : currPartition) {
			int n = s.length();
			int i = 0, j = n - 1;
			while (i < j) {
				if (s.charAt(i) != s.charAt(j))
					return false;
				i++;
				j--;
			}
		}
		return true;
	}

	
	void generatePartition(String s, String bitString) {
		List<String> currPartition = new ArrayList<>();
		StringBuilder subString = new StringBuilder();
		subString.append(s.charAt(0));
		for (int i = 0; i < bitString.length(); i++) {
			
			
			if (bitString.charAt(i) == '0') {
				
				subString.append(s.charAt(i + 1));
			}
			
			
			
			
			else {
				
				currPartition.add(subString.toString());

				subString.setLength(0);

				
				
				subString.append(s.charAt(i + 1));
			}
		}
		currPartition.add(subString.toString());
		if (checkPalindrome(currPartition)) {
			ans.add(currPartition);
		}
	}

	
	
	
	void bitManipulation(String s, String bitString) {
		
		
		
		if (bitString.length() == s.length() - 1) {
			generatePartition(s, bitString);
			return;
		}
		bitString += '1';
		bitManipulation(s, bitString);
		bitString = bitString.substring(0, bitString.length() - 1);
		bitString += '0';
		bitManipulation(s, bitString);
		bitString = bitString.substring(0, bitString.length() - 1);
	}

	
	List<List<String>> partition(String s) {
		String bitString = "";
		bitManipulation(s, bitString);
		return ans;
	}

	public static void main(String[] args) {
		Main ob = new Main();
		
		List<List<String>> ans;
		// String s = "geeks";
        String s = "nitin";
		ans = ob.partition(s);
		for (List<String> v : ans) {
			for (String it : v) {
				System.out.print(it + " ");
			}
			System.out.println();
		}
	}
}
