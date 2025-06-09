import java.util.Arrays;

class Solution {
    
    public int solveUsingRecursion(int[][] grid, int m, int n, int i, int j) {
        if (i == m - 1 && j == n - 1) {
            return grid[i][j] == 1 ? 0 : 1;
        }

        if (i >= m || j >= n || grid[i][j] == 1) {
            return 0;
        }

        int rightMove = solveUsingRecursion(grid, m, n, i, j + 1);
        int downMove = solveUsingRecursion(grid, m, n, i + 1, j);

        return rightMove + downMove;
    }

   
    public int solveUsingMemo(int[][] grid, int m, int n, int i, int j, int[][] dp) {
        if (i == m - 1 && j == n - 1) {
            return grid[i][j] == 1 ? 0 : 1;
        }

        if (i >= m || j >= n || grid[i][j] == 1) {
            return 0;
        }

        if (dp[i][j] != -1) {
            return dp[i][j];
        }

        int rightMove = solveUsingMemo(grid, m, n, i, j + 1, dp);
        int downMove = solveUsingMemo(grid, m, n, i + 1, j, dp);

        return dp[i][j] = rightMove + downMove;
    }

   
    public int solveUsingTabulation(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;

        int[][] dp = new int[m][n];

     
        if (grid[m - 1][n - 1] == 1) return 0;
        dp[m - 1][n - 1] = 1;

       
        for (int i = m - 1; i >= 0; i--) {
            for (int j = n - 1; j >= 0; j--) {
                if (grid[i][j] == 1) {
                    dp[i][j] = 0; 
                } else {
                    if (i < m - 1) dp[i][j] += dp[i + 1][j]; 
                    if (j < n - 1) dp[i][j] += dp[i][j + 1]; 
                }
            }
        }
        return dp[0][0];
    }


    public int solveUsingSpaceOptimization(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;

        int[] prev = new int[n];

        for (int i = m - 1; i >= 0; i--) {
            int[] curr = new int[n];
            for (int j = n - 1; j >= 0; j--) {
                if (grid[i][j] == 1) {
                    curr[j] = 0; 
                } else if (i == m - 1 && j == n - 1) {
                    curr[j] = 1;
                } else {
                    int rightMove = (j + 1 < n) ? curr[j + 1] : 0;
                    int downMove = (i + 1 < m) ? prev[j] : 0;

                    curr[j] = rightMove + downMove;
                }
            }
            prev = curr;
        }
        return prev[0];
    }

    // Function to find unique paths with obstacles
    public int uniquePathsWithObstacles(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;

        // If starting cell has an obstacle, no path is possible
        if (grid[0][0] == 1) return 0;

        // Recursion (TLE for large inputs)
        // return solveUsingRecursion(grid, m, n, 0, 0);

        // Memoization (Top-Down DP)
        // int[][] dp = new int[m][n];
        // for (int[] row : dp) Arrays.fill(row, -1);
        // return solveUsingMemo(grid, m, n, 0, 0, dp);

        // Tabulation (Bottom-Up DP)
        // return solveUsingTabulation(grid);

        // Space Optimization (Best Approach)
        return solveUsingSpaceOptimization(grid);
    }
}
