import java.util.List;

class Solution {
    public int solveUsingRecursion(List<List<Integer>> triangle, int i, int j, int n) {
        if (i == n - 1) {
            return triangle.get(i).get(j);
        }

        int downAns = tri + solveUsingReangle.get(i).get(j) + solveUsingRecursion(triangle, i + 1, j, n);
        int diagonalAns = triangle.get(i).get(j)cursion(triangle, i + 1, j + 1, n);

        return Math.min(downAns, diagonalAns);
    }

    public int solveUsingMemo(List<List<Integer>> triangle, int i, int j, int n, int[][] dp) {
        if (i == n - 1) {
            return triangle.get(i).get(j);
        }

        if (dp[i][j] != -1) {
            return dp[i][j];
        }

        int downAns = triangle.get(i).get(j) + solveUsingMemo(triangle, i + 1, j, n, dp);
        int diagonalAns = triangle.get(i).get(j) + solveUsingMemo(triangle, i + 1, j + 1, n, dp);

        return dp[i][j] = Math.min(downAns, diagonalAns);
    }

    public int solveUsingTabulation(List<List<Integer>> triangle, int n) {
        int[][] dp = new int[n][n];

        for (int j = 0; j < n; j++) {
            dp[n - 1][j] = triangle.get(n - 1).get(j);
        }

        for (int i = n - 2; i >= 0; i--) {
            for (int j = 0; j <= i; j++) {
                int downAns = dp[i + 1][j];
                int diagonalAns = dp[i + 1][j + 1];
                dp[i][j] = triangle.get(i).get(j) + Math.min(downAns, diagonalAns);
            }
        }

        return dp[0][0];
    }

    public int solveUsingTabulationSO(List<List<Integer>> triangle, int n) {
        int[] prevRow = new int[n];

        for (int j = 0; j < n; j++) {
            prevRow[j] = triangle.get(n - 1).get(j);
        }

        for (int i = n - 2; i >= 0; i--) {
            int[] currRow = new int[i + 1];

            for (int j = 0; j <= i; j++) {
                currRow[j] = triangle.get(i).get(j) + Math.min(prevRow[j], prevRow[j + 1]);
            }

            prevRow = currRow;
        }

        return prevRow[0];
    }

    public int minimumTotal(List<List<Integer>> triangle) {
        int n = triangle.size();
        int [][]dp = new int[n][n];
        for(int []rows:dp){
            Arrays.fill(rows,-1);
        }
        return solveUsingMemo(triangle, 0, 0, n,dp);
    }
}
