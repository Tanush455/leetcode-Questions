/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */
class Solution {
    public List<TreeNode> generateTrees(int n) {
        List<TreeNode> [][]dp = new List[n+1][n+1];
        return n > 0 ? generateTrees(1,n,dp) : new ArrayList<>();
    }

    private List<TreeNode> generateTrees(int start,int end,List<TreeNode> [][]dp){
        List<TreeNode> trees = new ArrayList<>();
        if(start > end){
            trees.add(null);
            return trees;
        }

        if(dp[start][end] != null){
            return dp[start][end];
        }

        for(int i = start;i<=end;i++){
            List<TreeNode> lbst = generateTrees(start,i-1,dp);
            List<TreeNode> rbst = generateTrees(i+1,end,dp);

            for(TreeNode l: lbst){
                for(TreeNode r:rbst){
                    TreeNode currTree = new TreeNode(i);
                    currTree.left = l;
                    currTree.right = r;
                    trees.add(currTree);
                }
            }
        }

        return dp[start][end] = trees;
    }
}