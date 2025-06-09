class Solution {
    public boolean solveUsingRecursion(int []nums, int n, int index, int target){
        if(target == 0){
            return true;
        }
        if(index >= n || target < 0){
            return false;
        }

        boolean take = solveUsingRecursion(nums, n, index + 1, target - nums[index]);

        boolean notTake = solveUsingRecursion(nums, n, index + 1, target);

        return take || notTake;
    }

    public boolean solveUsingTabulation(int[] nums, int n, int target){
        boolean[][] dp = new boolean[n + 1][target + 1];

        for(int i = 0; i <= n; i++){
            dp[i][0] = true;
        }

        for(int i = 1; i <= n; i++){
            for(int j = 1; j <= target; j++){
                boolean notTake = dp[i-1][j];

                boolean take = false;
                if(j >= nums[i-1]){
                    take = dp[i-1][j - nums[i-1]];
                }
                
                dp[i][j] = take || notTake;
            }
        }
        return dp[n][target];
    }

    public boolean canPartition(int[] nums) {
        int n = nums.length;
        int sum = 0;

        for(int i = 0; i < n; i++){
            sum += nums[i];
        }

        if(sum % 2 != 0){
            return false;
        }

        int target = sum / 2;

        return solveUsingTabulation(nums, n, target);
    }
}