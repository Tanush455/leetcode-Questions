import java.util.HashMap;

class Solution {
    public int numSubarraysWithSum(int[] nums, int goal) {
        int n = nums.length;
        int count = 0;
        int currentSum = 0;
        HashMap<Integer, Integer> sumFreq = new HashMap<>();
        sumFreq.put(0, 1);

        for (int i = 0; i < n; i++) {
            currentSum += nums[i];

            if (sumFreq.containsKey(currentSum - goal)) {
                count += sumFreq.get(currentSum - goal);
            }

            sumFreq.put(currentSum, sumFreq.getOrDefault(currentSum, 0) + 1);
        }
        return count;
    }
}