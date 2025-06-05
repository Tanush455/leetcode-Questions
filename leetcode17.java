import java.util.*;

class Pair implements Comparable<Pair>{
    int first;
    int second;
    // Renamed 'second' to 'weight' for better clarity in graph algorithms
    // where 'second' often represents weight.
    int weight; 
    
    Pair(int first, int second) {
        this.first = first;
        this.second = second; // Keep 'second' for original usage if any
        this.weight = second; // Assign to 'weight' for clarity in graph algorithms
    }

    @Override
    public String toString() {
        return "(" + first + ", " + second + ")";    
    }
    
    @Override
    public int compareTo(Pair other){
        return Integer.compare(this.second,other.second);
    }
}

class Graph {
    public HashMap<Integer, ArrayList<Pair>> adj = new HashMap<>();
    
    public void addEdges(int u, int v, int w, boolean direction) {
        adj.putIfAbsent(u, new ArrayList<>());
        adj.get(u).add(new Pair(v, w));
        
        if (!direction) {
            adj.putIfAbsent(v, new ArrayList<>());
            adj.get(v).add(new Pair(u, w));
        }
    }
    
    public void print() {
        System.out.println("Graph Adjacency List:");
        for (Map.Entry<Integer, ArrayList<Pair>> entry : adj.entrySet()) {
            int node = entry.getKey();
            ArrayList<Pair> neighbors = entry.getValue();
            
            System.out.print(node + " -> ");
            for (Pair nbr : neighbors) {
                System.out.print(nbr + " ");    
            }
            System.out.println();    
        }
    }
    
    public void BFS(int src){
        Queue<Integer> q = new LinkedList<>();
        q.add(src);
        HashMap<Integer,Boolean> visited = new HashMap<>();
        visited.put(src, true); 
        
        while(!q.isEmpty()){
            int current = q.poll(); 
            System.out.print(current + " "); 
            
            if (adj.containsKey(current)) {
                ArrayList<Pair> neighbors = adj.get(current);
                
                for(Pair nbr : neighbors){
                    int node = nbr.first;
                    
                    if(!visited.containsKey(node) || !visited.get(node)){ 
                        q.add(node);
                        visited.put(node, true);
                    }
                }
            }
        }
        System.out.println(); 
    }
    
    public void DFS(int src, HashMap<Integer,Boolean> visited){
        System.out.print(src + " ");
        visited.put(src, true);
        
        if (adj.containsKey(src)) { 
            ArrayList<Pair> neighbors = adj.get(src);
            
            for(Pair nbr : neighbors){
                if(!visited.containsKey(nbr.first) || !visited.get(nbr.first)){
                    DFS(nbr.first, visited);
                }
            }
        }
    }
    
    public void DFSTopo(int src, HashMap<Integer,Boolean> visited, Stack<Integer> st){
        visited.put(src,true);
        if(adj.containsKey(src)){
            ArrayList<Pair> neighbors = adj.get(src);
            for(Pair nbr: neighbors){
                if(!visited.containsKey(nbr.first) || !visited.get(nbr.first)){
                    DFSTopo(nbr.first,visited,st);
                }
            }
        }
        st.push(src); 
    }
    
    public void ShortestPathDFS(int src){
        Stack<Integer> st = new Stack<>();
        HashMap<Integer,Boolean> visited = new HashMap<>();
        
        for (Map.Entry<Integer, ArrayList<Pair>> entry : adj.entrySet()) {
            int node = entry.getKey();
            if (!visited.containsKey(node) || !visited.get(node)) {
                DFSTopo(node, visited, st);
            }
        }
        
        Map<Integer,Integer> distance = new HashMap<>();
        for(int node : adj.keySet()){
            distance.put(node,Integer.MAX_VALUE);
        }
        distance.put(src, 0);
        
        while(!st.isEmpty()){
            int node = st.pop();
            
            if (distance.get(node) != Integer.MAX_VALUE && adj.containsKey(node)) {
                for(Pair nbrPair : adj.get(node)){
                    int nbrNode = nbrPair.first;
                    int nbrWeight = nbrPair.second;
                    
                    if(distance.get(node) + nbrWeight < distance.get(nbrNode)){
                        distance.put(nbrNode,distance.get(node) + nbrWeight);
                    }
                }
            }
        }
        
        System.out.println("Shortest Path from Source " + src + " (DFS for DAGs):");
        for (Map.Entry<Integer, Integer> entry : distance.entrySet()) {
            System.out.println("Node " + entry.getKey() + ": " + (entry.getValue() == Integer.MAX_VALUE ? "Infinity" : entry.getValue()));
        }
    }
    
    public void TopoSortBFS() {
        Map<Integer,Integer> indegree = new HashMap<>();
        
        for (int node : adj.keySet()) {
            indegree.put(node, 0); 
        }

        for (int node : adj.keySet()) {
            for (Pair nbrPair : adj.getOrDefault(node, new ArrayList<>())) {
                int nbr = nbrPair.first;
                indegree.put(nbr, indegree.getOrDefault(nbr, 0) + 1);
            }
        }
        
        Queue<Integer> q = new LinkedList<>();
        for(int node: adj.keySet()){
            if(indegree.getOrDefault(node,0) == 0){
                q.add(node);
            }
        }
        
        List<Integer> topoOrder = new ArrayList<>();
        while(!q.isEmpty()){
            int curr = q.poll();
            topoOrder.add(curr);
            
            if (adj.containsKey(curr)) {
                for(Pair nbrPair : adj.get(curr)){
                    int nbr = nbrPair.first;
                    indegree.put(nbr, indegree.get(nbr) - 1);
                    if(indegree.get(nbr) == 0){
                        q.add(nbr);
                    }
                }
            }
        }

        System.out.print("Topological Sort (BFS/Kahn's Algorithm): ");
        if (topoOrder.size() == adj.keySet().size()) {
            for (int node : topoOrder) {
                System.out.print(node + " ");
            }
        } else {
            System.out.print("Graph contains a cycle, cannot perform topological sort.");
        }
        System.out.println();
    }
    
    public void topologicalSortDFS() {
        Stack<Integer> st = new Stack<>();
        HashMap<Integer, Boolean> visited = new HashMap<>();

        for (Map.Entry<Integer, ArrayList<Pair>> entry : adj.entrySet()) {
            int node = entry.getKey();
            if (!visited.containsKey(node) || !visited.get(node)) {
                DFSTopo(node, visited, st);
            }
        }
        
        System.out.print("Topological Sort (DFS): ");
        while (!st.isEmpty()) {
            System.out.print(st.pop() + " ");
        }
        System.out.println();
    }
    
    public void dijkstraAlgo(int src){
        PriorityQueue<Pair> pq = new PriorityQueue<>();
        pq.add(new Pair(src,0));
        HashMap<Integer,Integer> distance = new HashMap<>();
        HashMap<Integer,Boolean> visited = new HashMap<>();
        
        for(int node : adj.keySet()){
            distance.put(node,Integer.MAX_VALUE);
            visited.put(node, false);
        }
        distance.put(src,0);

        while(!pq.isEmpty()){
            Pair front = pq.poll();
            int node = front.first;
            int currentDist = front.second;
            
            if(visited.get(node)){
                continue;
            }
            
            visited.put(node,true);
            
            for(Pair neighbor : adj.getOrDefault(node, new ArrayList<>())){ 
                int nbrNode = neighbor.first;
                int nbrWeight = neighbor.second;
                
                if(currentDist + nbrWeight < distance.get(nbrNode)){
                    distance.put(nbrNode, currentDist + nbrWeight);
                    pq.add(new Pair(nbrNode, currentDist + nbrWeight));
                }
            }
        }

        System.out.println("Shortest Path from Source " + src + " (Dijkstra's Algorithm):");
        for (Map.Entry<Integer, Integer> entry : distance.entrySet()) {
            System.out.println("Node " + entry.getKey() + ": " + (entry.getValue() == Integer.MAX_VALUE ? "Infinity" : entry.getValue()));
        }
    }
    
    public void BellmanFord(int src, int n){
        // Initialize distances
        int[] dist = new int[n + 1]; // Using n+1 to handle 0-based or 1-based indexing
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[src] = 0;
        
        // Relax edges (V-1) times
        for(int i = 1; i < n; i++){ // Loop (V-1) times, where V is the number of nodes
            // Iterate over all edges
            for(Map.Entry<Integer, ArrayList<Pair>> entry : adj.entrySet()){
                int u = entry.getKey();
                
                // If 'u' is unreachable, we can't relax its outgoing edges
                if (dist[u] == Integer.MAX_VALUE) {
                    continue; 
                }
                for(Pair nbr : entry.getValue()){
                    int v = nbr.first;
                    int weight = nbr.second;
                    
                    // Relaxation step
                    if(dist[u] + weight < dist[v]){
                        dist[v] = dist[u] + weight;
                    }
                }
            }
        }

        // Check for negative cycles (optional, but good practice for Bellman-Ford)
        boolean hasNegativeCycle = false;
        for(Map.Entry<Integer, ArrayList<Pair>> entry : adj.entrySet()){
            int u = entry.getKey();
            if (dist[u] == Integer.MAX_VALUE) {
                continue;
            }
            for(Pair nbr : entry.getValue()){
                int v = nbr.first;
                int weight = nbr.second;
                
                if(dist[u] + weight < dist[v]){
                    hasNegativeCycle = true;
                    break;
                }
            }
            if (hasNegativeCycle) {
                break;
            }
        }

        System.out.println("\nShortest Path from Source " + src + " (Bellman-Ford Algorithm):");
        if (hasNegativeCycle) {
            System.out.println("Graph contains a negative cycle. Shortest paths are not well-defined.");
        } else {
            // Print distances for nodes that are actually part of the graph (0 to n)
            for (int i = 0; i <= n; i++) { 
                // Check if node 'i' actually exists in the adjacency list
                if (adj.containsKey(i) || i == src) { // Include source even if it has no outgoing edges
                    if (dist[i] == Integer.MAX_VALUE) {
                        System.out.println("Node " + i + ": Infinity");
                    } else {
                        System.out.println("Node " + i + ": " + dist[i]);
                    }
                }
            }
        }
    }
    
    public void floydWarshall(int n){
        .
        long [][]dist = new long[n][n]; 

        // Initialize distances
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == j) {
                    dist[i][j] = 0; // Distance to self is 0
                } else {
                    dist[i][j] = Integer.MAX_VALUE; // Initialize with infinity
                }
            }
        }

        // Populate initial distances from direct edges
        for(Map.Entry<Integer, ArrayList<Pair>> entry : adj.entrySet()){
            int u = entry.getKey();
            for(Pair nbr : entry.getValue()){
                int v = nbr.first;
                int weight = nbr.second;
                if (u < n && v < n) { // Ensure nodes are within bounds
                    dist[u][v] = Math.min(dist[u][v], weight); // Handle parallel edges, take minimum
                }
            }
        }
        
        // Floyd-Warshall Algorithm
        for(int k = 0; k < n; k++){ // Intermediate node
            for(int i = 0; i < n; i++){ // Source node
                for(int j = 0; j < n; j++){ // Destination node
                    // Avoid overflow when adding large values (MAX_VALUE + something)
                    if (dist[i][k] != Integer.MAX_VALUE && dist[k][j] != Integer.MAX_VALUE) {
                        dist[i][j] = Math.min(dist[i][j], dist[i][k] + dist[k][j]);
                    }
                }
            }
        }

        // Check for negative cycles (diagonal elements will be negative)
        boolean hasNegativeCycle = false;
        for (int i = 0; i < n; i++) {
            if (dist[i][i] < 0) {
                hasNegativeCycle = true;
                break;
            }
        }

        System.out.println("\nAll-Pairs Shortest Path (Floyd-Warshall Algorithm):");
        if (hasNegativeCycle) {
            System.out.println("Graph contains a negative cycle.");
        } else {
            System.out.println("Distance Matrix:");
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (dist[i][j] == Integer.MAX_VALUE) {
                        System.out.print("INF\t");
                    } else {
                        System.out.print(dist[i][j] + "\t");
                    }
                }
                System.out.println();
            }
        }
    }

    public boolean isCycleBFS(int src, HashMap<Integer, Boolean> visited, HashMap<Integer, Integer> parent){
        Queue<Integer> q = new LinkedList<>();
        q.add(src);
        visited.put(src, true);
        parent.put(src, -1);
        
        while(!q.isEmpty()){
            int current = q.poll();
            
            if (!adj.containsKey(current)) {
                continue; 
            }

            ArrayList<Pair> neighbors = adj.get(current);
            
            for(Pair nbr: neighbors){
                int node = nbr.first;
                
                if(visited.containsKey(node) && visited.get(node) && parent.get(current) != node){
                    return true;
                } else if(!visited.containsKey(node) || !visited.get(node)){
                    visited.put(node, true);
                    parent.put(node, current);
                    q.add(node);
                }
            }
        }
        return false;
    }

    public boolean hasCycleBFS() {
        HashMap<Integer, Boolean> visited = new HashMap<>();
        HashMap<Integer, Integer> parent = new HashMap<>();

        for (Map.Entry<Integer, ArrayList<Pair>> entry : adj.entrySet()) {
            int node = entry.getKey();
            if (!visited.containsKey(node) || !visited.get(node)) {
                if (isCycleBFS(node, visited, parent)) {
                    return true; 
                }
            }
        }
        return false; 
    }

    public boolean isCycleDFS(int src, HashMap<Integer, Boolean> visited, HashMap<Integer, Integer> parent) {
        visited.put(src, true);
        
        if (adj.containsKey(src)) {
            for (Pair nbr : adj.get(src)) {
                int node = nbr.first;
                
                if (visited.containsKey(node) && visited.get(node) && parent.get(src) != node) {
                    return true; 
                } else if (!visited.containsKey(node) || !visited.get(node)) {
                    parent.put(node, src);
                    if (isCycleDFS(node, visited, parent)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean hasCycleDFS() {
        HashMap<Integer, Boolean> visited = new HashMap<>();
        HashMap<Integer, Integer> parent = new HashMap<>();

        for (Map.Entry<Integer, ArrayList<Pair>> entry : adj.entrySet()) {
            int node = entry.getKey();
            if (!visited.containsKey(node) || !visited.get(node)) {
                parent.put(node, -1); 
                if (isCycleDFS(node, visited, parent)) {
                    return true;
                }
            }
        }
        return false;
    }
}

public class Main {
    public static void main(String[] args) {
        System.out.println("Creating a Graph with 15 Nodes!");
        Graph g = new Graph();

        for (int i = 1; i < 15; i++) {
            g.addEdges(i, i + 1, 1, false);    
        }

        g.addEdges(1, 5, 2, false);    
        g.addEdges(3, 10, 5, false);    
        g.addEdges(7, 2, 3, false);    
        g.addEdges(15, 1, 10, false);    

        for (int i = 1; i <= 15; i++) {
            g.adj.putIfAbsent(i, new ArrayList<>());
        }
            
        g.print();

        System.out.println("\nBFS Traversal starting from node 1:");
        g.BFS(1);

        System.out.println("\nDFS Traversal starting from node 1:");
        HashMap<Integer, Boolean> dfsVisited = new HashMap<>();
        g.DFS(1, dfsVisited);
        System.out.println();

        System.out.println("\nChecking for cycles using BFS:");
        if (g.hasCycleBFS()) {
            System.out.println("Cycle detected in the graph.");
        } else {
            System.out.println("No cycle detected in the graph.");
        }

        System.out.println("\nChecking for cycles using DFS:");
        if (g.hasCycleDFS()) {
            System.out.println("Cycle detected in the graph.");
        } else {
            System.out.println("No cycle detected in the graph.");
        }
        
        System.out.println("\n---");
        System.out.println("Topological Sort and Shortest Path for DAGs:");
        System.out.println("Creating a Directed Acyclic Graph for Topological Sort and Shortest Path (DFS):");
        Graph directedGraph = new Graph();
        directedGraph.addEdges(1, 2, 1, true);
        directedGraph.addEdges(1, 3, 1, true);
        directedGraph.addEdges(2, 4, 1, true);
        directedGraph.addEdges(3, 4, 1, true);
        directedGraph.addEdges(4, 5, 1, true);
        directedGraph.addEdges(4, 6, 1, true);
        directedGraph.addEdges(5, 7, 1, true);
        directedGraph.addEdges(6, 7, 1, true);
        
        for (int i = 1; i <= 7; i++) {
            directedGraph.adj.putIfAbsent(i, new ArrayList<>());
        }

        directedGraph.print();
        
        if (!directedGraph.hasCycleDFS()) { 
            directedGraph.topologicalSortDFS();
            System.out.println("\nShortest path from node 1 using DFS (for DAG):");
            directedGraph.ShortestPathDFS(1);
        } else {
            System.out.println("Cannot perform topological sort or shortest path using DFS on this graph as it contains a cycle.");
        }

        System.out.println("\n---");
        System.out.println("Topological sort using BFS (Kahn's Algorithm):");
        for (int i = 1; i <= 7; i++) {
            directedGraph.adj.putIfAbsent(i, new ArrayList<>());
        }
        if (!directedGraph.hasCycleBFS()) { 
            directedGraph.TopoSortBFS();
        } else {
            System.out.println("Cannot perform topological sort using BFS on this graph as it contains a cycle.");
        }

        System.out.println("\n---");
        System.out.println("Dijkstra's Algorithm:");
        Graph weightedGraph = new Graph();
        weightedGraph.addEdges(0, 1, 4, true);
        weightedGraph.addEdges(0, 7, 8, true);
        weightedGraph.addEdges(1, 2, 8, true);
        weightedGraph.addEdges(1, 7, 11, true);
        weightedGraph.addEdges(2, 3, 7, true);
        weightedGraph.addEdges(2, 8, 2, true);
        weightedGraph.addEdges(2, 5, 4, true);
        weightedGraph.addEdges(3, 4, 9, true);
        weightedGraph.addEdges(3, 5, 14, true);
        weightedGraph.addEdges(4, 5, 10, true);
        weightedGraph.addEdges(5, 6, 2, true);
        weightedGraph.addEdges(6, 7, 1, true);
        weightedGraph.addEdges(6, 8, 6, true);
        weightedGraph.addEdges(7, 8, 7, true);
        
        for (int i = 0; i <= 8; i++) {
            weightedGraph.adj.putIfAbsent(i, new ArrayList<>());
        }
        
        weightedGraph.print();
        weightedGraph.dijkstraAlgo(0);

        System.out.println("\n---");
        System.out.println("Bellman-Ford Algorithm:");
        // Example for Bellman-Ford: a graph with a negative edge but no negative cycle
        Graph bellmanFordGraph = new Graph();
        bellmanFordGraph.addEdges(0, 1, 1, true);
        bellmanFordGraph.addEdges(0, 2, 4, true);
        bellmanFordGraph.addEdges(1, 2, 2, true);
        bellmanFordGraph.addEdges(1, 3, 6, true);
        bellmanFordGraph.addEdges(2, 3, 3, true);
        
        // Negative edge
        bellmanFordGraph.addEdges(3, 1, -10, true); 

        // Ensure all nodes are present in the map for proper initialization
        for (int i = 0; i <= 3; i++) {
            bellmanFordGraph.adj.putIfAbsent(i, new ArrayList<>());
        }

        bellmanFordGraph.print();
        bellmanFordGraph.BellmanFord(0, 3); // Source 0, N = 3 (nodes 0, 1, 2, 3)

        System.out.println("\n---");
        System.out.println("Bellman-Ford Algorithm with a Negative Cycle:");
        Graph negativeCycleGraph = new Graph();
        negativeCycleGraph.addEdges(0, 1, 1, true);
        negativeCycleGraph.addEdges(1, 2, -1, true);
        negativeCycleGraph.addEdges(2, 0, -1, true); // This creates a negative cycle (0 -> 1 -> 2 -> 0 with total weight 1 - 1 - 1 = -1)
        
        for (int i = 0; i <= 2; i++) {
            negativeCycleGraph.adj.putIfAbsent(i, new ArrayList<>());
        }
        
        negativeCycleGraph.print();
        negativeCycleGraph.BellmanFord(0, 2); // Source 0, N = 2 (nodes 0, 1, 2)

        System.out.println("\n---");
        System.out.println("Floyd-Warshall Algorithm:");
        Graph floydWarshallGraph = new Graph();
        floydWarshallGraph.addEdges(0, 1, 3, true);
        floydWarshallGraph.addEdges(0, 3, 7, true);
        floydWarshallGraph.addEdges(1, 0, 8, true);
        floydWarshallGraph.addEdges(1, 2, 2, true);
        floydWarshallGraph.addEdges(2, 0, 5, true);
        floydWarshallGraph.addEdges(2, 3, 1, true);
        floydWarshallGraph.addEdges(3, 0, 2, true);

        // Ensure all nodes are present in the map
        for (int i = 0; i < 4; i++) {
            floydWarshallGraph.adj.putIfAbsent(i, new ArrayList<>());
        }
        
        floydWarshallGraph.print();
        floydWarshallGraph.floydWarshall(4); // N = 4 (nodes 0, 1, 2, 3)

        System.out.println("\n---");
        System.out.println("Floyd-Warshall Algorithm with a Negative Cycle:");
        Graph fwNegativeCycleGraph = new Graph();
        fwNegativeCycleGraph.addEdges(0, 1, 1, true);
        fwNegativeCycleGraph.addEdges(1, 2, -1, true);
        fwNegativeCycleGraph.addEdges(2, 0, -1, true); // Negative cycle
        
        for (int i = 0; i < 3; i++) {
            fwNegativeCycleGraph.adj.putIfAbsent(i, new ArrayList<>());
        }
        
        fwNegativeCycleGraph.print();
        fwNegativeCycleGraph.floydWarshall(3); // N = 3 (nodes 0, 1, 2)
    }
}