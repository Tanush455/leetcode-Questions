class Solution {

    public boolean isSafe(int row, int col, List<String> board, int n) {

        // Checking for the left upper diagonal
        int r = row;
        int c = col;
        while (r >= 0 && c >= 0) {
            if (board.get(r).charAt(c) == 'Q')
                return false;
            row--;
            col--;
        }

        // Checking for the left straight path
        r = row;
        c = col;
        while (col >= 0) {
            if (board.get(r).charAt(c) == 'Q')
                return false;
            col--;
        }

        // Checking for the lower diagonal
        r = row;
        c = col;
        while (r < n && col >= 0) {
            if (board.get(r).charAt(c) == 'Q')
                return false;
            col--;
            row++;
        }

        return true;
    }

    public static void solve(int col, char[][] board, List<List<String>> ans, int[] leftRow, int[] upperDiagonal,
            int[] lowerDiagonal, int n) {
        if (col == n) {
            List<String> currentBoard = new ArrayList<>();
            for (int i = 0; i < board.length; i++) {
                currentBoard.add(new String(board[i])); // Convert char[] row to String
            }
            ans.add(currentBoard);
            return;
        }

        for (int row = 0; row < n; row++) {
            if (leftRow[row] == 0 && lowerDiagonal[row + col] == 0 && upperDiagonal[n - 1 + col - row] == 0) {

                // Place Queen
                board[row][col] = 'Q';

                leftRow[row] = 1;
                lowerDiagonal[row + col] = 1;
                upperDiagonal[n - 1 + col - row] = 1;

                solve(col + 1, board, ans, leftRow, upperDiagonal, lowerDiagonal, n);

                // Backtrack
                board[row][col] = '.';

                leftRow[row] = 0;
                lowerDiagonal[row + col] = 0;
                upperDiagonal[n - 1 + col - row] = 0;
            }
        }
    }

    public static List<List<String>> solveNQueens(int n) {
        List<List<String>> ans = new ArrayList<>();
        char[][] board = new char[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                board[i][j] = '.';
            }
        }

        int[] leftRow = new int[n];
        int[] upperDiagonal = new int[2 * n - 1];
        int[] lowerDiagonal = new int[2 * n - 1];

        solve(0, board, ans, leftRow, upperDiagonal, lowerDiagonal, n);
        return ans;
    }
}