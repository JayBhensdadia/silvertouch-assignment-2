import java.util.HashSet;
import java.util.List;

public class Main {

    public static boolean wordBreak(String s, List<String> wordDict) {
        HashSet<String> wordSet = new HashSet<>(wordDict);
        int n = s.length();
        boolean[] dp = new boolean[n + 1];
        dp[0] = true;

        for (int i = 1; i <= n; i++) {
            for (int j = 0; j < i; j++) {
                if (dp[j] && wordSet.contains(s.substring(j, i))) {
                    dp[i] = true;
                    break;
                }
            }
        }

        return dp[n];
    }

    public static void main(String[] args) {
        String s = "rainfall";
        List<String> wordDict = List.of("rain", "fall");

        boolean result = wordBreak(s, wordDict);

        if (result) {
            System.out.println("The string can be segmented into dictionary words.");
        } else {
            System.out.println("The string cannot be segmented into dictionary words.");
        }
    }
}
