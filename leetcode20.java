public class leetcode20 {
    
}
class Solution {
    private int[][] dp; // Memoization table

    // Recursive function with memoization
    private int solve(int[][] matrix, int row, int col) {
        if (col < 0 || col >= matrix[0].length) {
            return Integer.MAX_VALUE; // Boundary condition for invalid column indices
        }
        if (row == matrix.length - 1) { // Base case: last row
            return matrix[row][col];
        }
        if (dp[row][col] != Integer.MIN_VALUE) { // Check memoized result
            return dp[row][col];
        }

        // Recursive calls for down, left diagonal, and right diagonal
        int down = solve(matrix, row + 1, col);
        int leftDiagonal = solve(matrix, row + 1, col - 1);
        int rightDiagonal = solve(matrix, row + 1, col + 1);

        // Memoize and return the result
        dp[row][col] = matrix[row][col] + Math.min(down, Math.min(leftDiagonal, rightDiagonal));
        return dp[row][col];
    }

    // Tabulation approach
    public int solveUsingTabulation(int n, int m, int[][] matrix) {
        int[][] dp = new int[n][m];

        // Initialize the last row of dp
        for (int col = 0; col < m; col++) {
            dp[n - 1][col] = matrix[n - 1][col];
        }

        // Fill dp table from the second last row to the first row
        for (int i = n - 2; i >= 0; i--) {
            for (int j = 0; j < m; j++) {
                int down = dp[i + 1][j];
                int leftDiagonal = (j > 0) ? dp[i + 1][j - 1] : Integer.MAX_VALUE;
                int rightDiagonal = (j < m - 1) ? dp[i + 1][j + 1] : Integer.MAX_VALUE;

                dp[i][j] = matrix[i][j] + Math.min(down, Math.min(leftDiagonal, rightDiagonal));
            }
        }

        // Find the minimum value in the first row
        int minPathSum = Integer.MAX_VALUE;
        for (int col = 0; col < m; col++) {
            minPathSum = Math.min(minPathSum, dp[0][col]);
        }
        return minPathSum;
    }

    public int minFallingPathSum(int[][] matrix) {
        int n = matrix.length;
        int m = matrix[0].length;

        // Using tabulation
        // return solveUsingTabulation(n, m, matrix);

        // Uncomment this if you want to use recursion with memoization
        
        dp = new int[n][m];
        for (int[] row : dp) {
            Arrays.fill(row, Integer.MIN_VALUE);
        }
        int minValue = Integer.MAX_VALUE;
        for (int col = 0; col < m; col++) {
            minValue = Math.min(minValue, solve(matrix, 0, col));
        }
        return minValue;
        
    }
}
