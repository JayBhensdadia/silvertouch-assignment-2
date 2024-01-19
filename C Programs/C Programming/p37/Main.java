public class Main {
    public static int maxProfit(int[] prices, int length) {
        int[] dp = new int[length + 1];

        for (int i = 1; i <= length; i++) {
            int maxProfit = Integer.MIN_VALUE;
            for (int j = 1; j <= i; j++) {
                maxProfit = Math.max(maxProfit, prices[j - 1] + dp[i - j]);
            }
            dp[i] = maxProfit;
        }

        return dp[length];
    }

    public static void main(String[] args) {
        int[] prices = {1, 5, 8, 9, 10, 17, 17, 20};
        int rodLength = 4;

        int maxProfit = maxProfit(prices, rodLength);

        System.out.println("Maximum Profit for Rod of Length " + rodLength + ": " + maxProfit);
    }
}
