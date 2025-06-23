class Solution {
    public int numSubarraysWithSum(int[] nums, int goal) {
        int n = nums.length;
        int count = 0;
        int sum = 0;
        HashMap<Integer,Integer> freq = new HashMap<>();
        freq.put(0,1);


        for(int i = 0; i < n; i++){
            sum += nums[i];

            if(freq.containsKey(sum - goal)){
                count += freq.get(sum - goal);
            }

            freq.put(sum,freq.getOrDefault(sum,0) + 1);
        }

        return count;
    }
}