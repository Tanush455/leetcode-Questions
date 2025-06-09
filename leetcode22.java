class Solution {
    public int solveUsingRecursion(int[][] grid, int n, int m, int i, int j) {
        if (i == n - 1 && j == m - 1) {
            return grid[i][j];
        }
        if (i >= n || j >= m) {
            return Integer.MAX_VALUE;
        }
        int rightMove = solveUsingRecursion(grid, n, m, i, j + 1);
        int downMove = solveUsingRecursion(grid, n, m, i + 1, j);
        return grid[i][j] + Math.min(rightMove, downMove);
    }
    
    public int solveUsingMemo(int[][] grid, int n, int m, int i, int j, int[][] dp) {
        if (i == n - 1 && j == m - 1) {
            return grid[i][j];
        }
        if (i >= n || j >= m) {
            return Integer.MAX_VALUE;
        }
        if (dp[i][j] != -1) {
            return dp[i][j];
        }
        int rightMove = solveUsingMemo(grid, n, m, i, j + 1, dp);
        int downMove = solveUsingMemo(grid, n, m, i + 1, j, dp);
        dp[i][j] = grid[i][j] + Math.min(rightMove, downMove);
        return dp[i][j];
    }
    
    public int solveUsingTabulation(int[][] grid) {
        int n = grid.length;
        int m = grid[0].length;
        int[][] dp = new int[n][m];
        dp[n - 1][m - 1] = grid[n - 1][m - 1];
        for (int i = n - 1; i >= 0; i--) {
            for (int j = m - 1; j >= 0; j--) {
                if (i == n - 1 && j == m - 1)
                    continue;
                int rightMove = (j + 1 < m) ? dp[i][j + 1] : Integer.MAX_VALUE;
                int downMove = (i + 1 < n) ? dp[i + 1][j] : Integer.MAX_VALUE;
                dp[i][j] = grid[i][j] + Math.min(rightMove, downMove);
            }
        }
        return dp[0][0];
    }
    
    public int minPathSum(int[][] grid) {
        int n = grid.length;
        int m = grid[0].length;
        int [][]dp = new int[n][m];
        for(int []row:dp){
            Arrays.fill(row,-1);
        }
        // return solveUsingRecursion(grid, n, m, 0, 0);
        return solveUsingMemo(grid,n,m,0,0,dp);
    }
}
