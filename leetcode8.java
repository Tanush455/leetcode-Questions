class Solution {
    public int equalPairs(int[][] grid) {
        int n = grid.length;
        int count = 0;

        HashMap<String, Integer> rowFrequencies = new HashMap<>();
        HashMap<String, Integer> colFrequencies = new HashMap<>();

        for (int i = 0; i < n; i++) {
            int[] row = new int[n];
            for (int j = 0; j < n; j++) {
                row[j] = grid[i][j];
            }
            String rowString = Arrays.toString(row);
            rowFrequencies.put(rowString, rowFrequencies.getOrDefault(rowString, 0) + 1);
        }

        for (int j = 0; j < n; j++) {
            int[] col = new int[n];
            for (int i = 0; i < n; i++) {
                col[i] = grid[i][j];
            }
            String colString = Arrays.toString(col);
            colFrequencies.put(colString, colFrequencies.getOrDefault(colString, 0) + 1);
        }

        for (String rowStr : rowFrequencies.keySet()) {
            if (colFrequencies.containsKey(rowStr)) {
                count += rowFrequencies.get(rowStr) * colFrequencies.get(rowStr);
            }
        }

        return count;
    }
}