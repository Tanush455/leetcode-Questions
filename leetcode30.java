class Solution {
    public int maxProfit(int k, int[] prices) {
        int n = prices.length;

        int[][][] dp = new int[n + 1][2][k + 1];

        for (int index = n - 1; index >= 0; index--) {
            for (int buy = 0; buy <= 1; buy++) {
                for (int transactionsRemaining = 1; transactionsRemaining <= k; transactionsRemaining++) {

                    if (buy == 1) {
                        int option1 = -prices[index] + dp[index + 1][0][transactionsRemaining];
                        int option2 = dp[index + 1][1][transactionsRemaining];
                        dp[index][buy][transactionsRemaining] = Math.max(option1, option2);
                    } else {
                        int option1 = prices[index] + dp[index + 1][1][transactionsRemaining - 1];
                        int option2 = dp[index + 1][0][transactionsRemaining];
                        dp[index][buy][transactionsRemaining] = Math.max(option1, option2);
                    }
                }
            }
        }
        return dp[0][1][k];
    }
}