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
class Pair {
    TreeNode node;
    int num;

    Pair(TreeNode node, int num) {
        this.node = node;
        this.num = num;
    }
}

class Solution {
    public int widthOfBinaryTree(TreeNode root) {
        if (root == null) return 0;

        int maxWidth = 0;
        Deque<Pair> q = new LinkedList<>();
        q.offer(new Pair(root, 0));

        while (!q.isEmpty()) {
            int size = q.size();
            int first = q.peek().num;
            int last = q.peekLast().num; 
            maxWidth = Math.max(maxWidth, last - first + 1);

            for (int i = 0; i < size; i++) {
                Pair p = q.poll();
                TreeNode node = p.node;
                int pos = p.num;

                
                if (node.left != null) {
                    q.offer(new Pair(node.left, 2 * pos + 1));
                }
                if (node.right != null) {
                    q.offer(new Pair(node.right, 2 * pos + 2));
                }
            }
        }

        return maxWidth;
    }
}
