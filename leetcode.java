class Solution {
    public int[] findIndices(int[] nums, int indexDifference, int valueDifference) {
        int n = nums.length;

        int minVal = nums[0];
        int minIdx = 0;
        int maxVal = nums[0];
        int maxIdx = 0;

        for (int i = indexDifference; i < n; i++) {
            if (nums[i - indexDifference] < minVal) {
                minVal = nums[i - indexDifference];
                minIdx = i - indexDifference;
            }
            if (nums[i - indexDifference] > maxVal) {
                maxVal = nums[i - indexDifference];
                maxIdx = i - indexDifference;
            }

            if (nums[i] - minVal >= valueDifference) {
                return new int[]{minIdx, i};
            }
            if (maxVal - nums[i] >= valueDifference) {
                return new int[]{maxIdx, i};
            }
        }

        return new int[]{-1, -1};
    }
}