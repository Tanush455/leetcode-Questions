import java.util.*;

class Graph {
    public HashMap<Integer, ArrayList<Integer>> edges = new HashMap<>();

    public void addEdges(int u, int v, boolean direction) {
        edges.putIfAbsent(u, new ArrayList<>());
        edges.get(u).add(v);
        if (!direction) {
            edges.putIfAbsent(v, new ArrayList<>());
            edges.get(v).add(u);
        }
    }
}

public class leetcode11 {

    public static Set<Integer> findGuardedPlanets(int N, int startNode, int maxDistance, HashMap<Integer, ArrayList<Integer>> graphEdges) {
        int[] distance = new int[N + 1];
        Arrays.fill(distance, Integer.MAX_VALUE);
        
        Queue<Integer> q = new LinkedList<>();
        Set<Integer> guardedPlanets = new HashSet<>();

        distance[startNode] = 0;
        q.add(startNode);
        guardedPlanets.add(startNode);

        while (!q.isEmpty()) {
            int node = q.poll();

            if (distance[node] >= maxDistance) {
                continue;
            }

            for (int nbr : graphEdges.getOrDefault(node, new ArrayList<>())) {
                if (distance[node] + 1 < distance[nbr]) {
                    distance[nbr] = distance[node] + 1;
                    q.add(nbr);
                    guardedPlanets.add(nbr);
                }
            }
        }
        return guardedPlanets;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt(); 
        int M = sc.nextInt(); 
        int K = sc.nextInt(); 

        Graph graph = new Graph();

        for (int i = 0; i < M; i++) {
            int u = sc.nextInt();
            int v = sc.nextInt();
            graph.addEdges(u, v, false); 
        }

        Set<Integer> allProtectedPlanets = new HashSet<>();

        for (int i = 0; i < K; i++) {
            int guardianPlanet = sc.nextInt();
            int guardianDistance = sc.nextInt();
            allProtectedPlanets.addAll(findGuardedPlanets(N, guardianPlanet, guardianDistance, graph.edges));
        }

        List<Integer> sortedProtectedPlanets = new ArrayList<>(allProtectedPlanets);
        Collections.sort(sortedProtectedPlanets);

        System.out.println(sortedProtectedPlanets.size());
        for (int i = 0; i < sortedProtectedPlanets.size(); i++) {
            System.out.print(sortedProtectedPlanets.get(i) + (i == sortedProtectedPlanets.size() - 1 ? "" : " "));
        }
        System.out.println();

        sc.close();
    }
}