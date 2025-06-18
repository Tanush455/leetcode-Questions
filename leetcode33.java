class Solution {
    public void DFS(int u,int [][]isConnected,boolean []visited){
        visited[u] = true;
        
        for(int i =0;i<isConnected.length;i++){
            if(!visited[i] && isConnected[u][i] == 1){
                DFS(i,isConnected,visited);
            }
        }
    }
    public int findCircleNum(int[][] isConnected) {
        int n = isConnected.length;
        boolean[] visited = new boolean[n+1];
        int count = 0;
        for(int i = 0;i<n;i++){
            if(!visited[i]){
                count++;
                DFS(i,isConnected,visited);
            }
        }
        return count;
    }
}