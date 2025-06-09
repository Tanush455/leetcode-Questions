import java.util.Arrays;

class Solution {
    // Recursion Approach
    public int solveUsingRecursion(int m, int n, int i, int j) {
        if (i == m - 1 && j == n - 1) return 1; // Base case: reached destination
        if (i >= m || j >= n) return 0; // Out of bounds
        
        int rightMove = solveUsingRecursion(m, n, i, j + 1);
        int downMove = solveUsingRecursion(m, n, i + 1, j);
        
        return rightMove + downMove;
    }

    // Memoization Approach (Top-Down DP)
    public int solveUsingMemo(int m, int n, int i, int j, int[][] dp) {
        if (i == m - 1 && j == n - 1) return 1;
        if (i >= m || j >= n) return 0;
        
        if (dp[i][j] != -1) return dp[i][j];

        int rightMove = solveUsingMemo(m, n, i, j + 1, dp);
        int downMove = solveUsingMemo(m, n, i + 1, j, dp);

        return dp[i][j] = rightMove + downMove;
    }

    // Tabulation Approach (Bottom-Up DP)
    public int solveUsingTabulation(int m, int n) {
        int[][] dp = new int[m][n];

        
        dp[m - 1][n - 1] = 1;

        for (int i = m - 1; i >= 0; i--) {
            for (int j = n - 1; j >= 0; j--) {
                if (i == m - 1 && j == n - 1) continue; 
                
                int rightMove = (j + 1 < n) ? dp[i][j + 1] : 0;
                int downMove = (i + 1 < m) ? dp[i + 1][j] : 0;
                
                dp[i][j] = rightMove + downMove;
            }
        }
        return dp[0][0];
    }


    public int solveUsingSpaceOptimization(int m, int n) {
        int[] prev = new int[n];
        Arrays.fill(prev, 1); 

        for (int i = m - 2; i >= 0; i--) {
            int[] curr = new int[n];
            for (int j = n - 1; j >= 0; j--) {
                int rightMove = (j + 1 < n) ? curr[j + 1] : 0;
                int downMove = prev[j];

                curr[j] = rightMove + downMove;
            }
            prev = curr;
        }
        return prev[0];
    }

    // Function to compute unique paths
    public int uniquePaths(int m, int n) {
        // Recursion (Exponential Time)
        // return solveUsingRecursion(m, n, 0, 0);

        // Memoization (O(m * n) Time & Space)
        // int[][] dp = new int[m][n];
        // for (int[] row : dp) Arrays.fill(row, -1);
        // return solveUsingMemo(m, n, 0, 0, dp);

        // Tabulation (O(m * n) Time, O(m * n) Space)
        // return solveUsingTabulation(m, n);

        // Space Optimization (O(m * n) Time, O(n) Space)
        return solveUsingSpaceOptimization(m, n);
    }
}
