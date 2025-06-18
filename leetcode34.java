class Solution {
    public int maximumDifference(int[] nums) {
        int diff = -1;
        int min = nums[0];
        int n = nums.length;

        for(int i = 1;i<n;i++){
            if(nums[i] > min){
                diff = Math.max(diff,nums[i] - min);
            }
            if(nums[i] < min){
                min = nums[i];
            }
        }

        return diff;
    }
}