class Solution {
    public int totalSteps(int[] nums) {
        int n = nums.length;
        if (n <= 1) {
            return 0;
        }

        int[] dp = new int[n];
        int maxSteps = 0;

        Stack<Integer> stack = new Stack<>();

        for (int i = n - 1; i >= 0; i--) {
            int currentRemovalSteps = 0;

            while (!stack.isEmpty() && nums[i] > nums[stack.peek()]) {
                currentRemovalSteps = Math.max(currentRemovalSteps + 1, dp[stack.pop()]);
            }
            dp[i] = currentRemovalSteps;
            maxSteps = Math.max(maxSteps, currentRemovalSteps);
            stack.push(i);
        }

        return maxSteps;
    }
}