import java.util.*;

class Pair implements Comparable<Pair>{
    int first;
    int second;
    
    Pair(int first, int second) {
        this.first = first;
        this.second = second;
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
        PriorityQueue<Pair> pq = new PriorityQueue();
        
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
        
        System.out.println("\nTopological sort using DFS:");
        System.out.println("\nCreating a Directed Acyclic Graph for Topological Sort and Shortest Path (DFS):");
        Graph directedGraph = new Graph();
        directedGraph.addEdges(1, 2, 1, true);
        directedGraph.addEdges(1, 3, 1, true);
        directedGraph.addEdges(2, 4, 1, true);
        directedGraph.addEdges(3, 4, 1, true);
        directedGraph.addEdges(4, 5, 1, true);
        directedGraph.addEdges(4, 6, 1, true);
        directedGraph.addEdges(5, 7, 1, true);
        directedGraph.addEdges(6, 7, 1, true);
        
        directedGraph.print();
        
        if (!directedGraph.hasCycleDFS()) { 
            directedGraph.topologicalSortDFS();
            System.out.println("\nShortest path from node 1 using DFS (for DAG):");
            directedGraph.ShortestPathDFS(1);
        } else {
            System.out.println("Cannot perform topological sort or shortest path using DFS on this graph as it contains a cycle.");
        }

        System.out.println("\nTopological sort using BFS (Kahn's Algorithm):");
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
        
        // Ensure all nodes are present in the map even if they have no outgoing edges
        for (int i = 0; i <= 8; i++) {
            weightedGraph.adj.putIfAbsent(i, new ArrayList<>());
        }
        
        weightedGraph.print();
        weightedGraph.dijkstraAlgo(0);
    }
}