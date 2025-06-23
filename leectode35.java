class Solution {
    public int totalFruit(int[] fruits) {
        HashMap <Integer,Integer> baskets=new HashMap<>();
        int left=0,max_length=0;
        for(int right=0;right<fruits.length;right++){
            int rightfruit=fruits[right];
            baskets.put(rightfruit,baskets.getOrDefault(rightfruit,0)+1);
            while(baskets.size()>2){
                int leftfruit=fruits[left];
                baskets.put(leftfruit,baskets.get(leftfruit)-1);
                if(baskets.get(leftfruit)==0){
                    baskets.remove(leftfruit);
                }
                left++;
            }
            max_length=Math.max(max_length,right-left+1);
        }
        return max_length;
    }
}