package org.example;

import java.util.*;

/**
 * MyGraph class using a List for the vertices and a hash map for the adjacency list
 */
public class MyGraph {

    List<Integer> vertices;
    Map<Integer, List<Edge>> adjacencyList;

    /**
     * graph constructor creates an instance of the
     * vertex list and an instance of the adjacency list map
     */
    public MyGraph(){
        this.vertices = new ArrayList<>();
        this.adjacencyList = new HashMap<>();
    }

    /**
     * add vertex method that can add a vertex onto the graph
     * @param v
     */
    void addVertex(int v) {
        if (!vertices.contains(v)) {
            vertices.add(v);
            adjacencyList.put(v, new ArrayList<>()); // Empty adjacency list
        }
    }

    /**
     * add edge method that adds a new edge to the adjacency lists for both
     * v1 and v2.
     * @param v1
     * @param v2
     * @param weight
     */
    void addEdge(int v1, int v2, int weight){
        if (!adjacencyList.containsKey(v1)) addVertex(v1);
        if (!adjacencyList.containsKey(v2)) addVertex(v2);

        Edge e1 = new Edge(v1, v2, weight);
        Edge e2 = new Edge(v2, v1, weight);

        adjacencyList.get(v1).add(e1);
        adjacencyList.get(v2).add(e2);
    }

    /**
     * method to print the created graph
     */
    void showGraph(){
        for(Map.Entry<Integer, List<Edge>> entry: adjacencyList.entrySet()){
            System.out.println(entry.getKey() + ", " + entry.getValue());
        }
    }

    /**
     * method implemented to calculate the number of connected components
     * @param g
     * @returns an array of the vertex # and which component it belongs to
     */
    public static int[] calculateConnectedComponents(MyGraph g){

        int numVertices = g.vertices.size();
        int[] componentMap = new int[numVertices]; // component number for each vertex (indexed by vertex index in g.vertices)
        boolean[] visited = new boolean[numVertices];
        int numComponents = 0;

        for (int i = 0; i < numVertices; i++) {
            int currVertex = g.vertices.get(i);
            if (!visited[i]) {
                numComponents++;
                Queue<Integer> bfsQueue = new LinkedList<>();
                bfsQueue.add(currVertex);

                while (!bfsQueue.isEmpty()) {
                    int compV = bfsQueue.remove();
                    int index = g.vertices.indexOf(compV);
                    if (visited[index]) continue;

                    visited[index] = true;
                    componentMap[index] = numComponents;

                    for (Edge e : g.adjacencyList.get(compV)) {
                        int destVertex = e.v2;
                        int destIndex = g.vertices.indexOf(destVertex);
                        if (!visited[destIndex]) {
                            bfsQueue.add(destVertex);
                        }
                    }
                }
            }
        }

        return componentMap;
    }
    public static Edge getMinFrontierEdge(MyGraph g, boolean[] visited){
        Edge minEdge = new Edge(0,0,Integer.MAX_VALUE);

        for (int i = 0; i < g.vertices.size(); i++) {
                if (!visited[i]) continue;            // only from visited vertices
                int v = g.vertices.get(i);

                // scan each edge out of v
                for (Edge e : g.adjacencyList.get(v)) {
                    // find the index of the other endpoint
                    int w = e.v2;
                    int wIndex = g.vertices.indexOf(w);

                    // if w is unvisited, e is on the frontier
                    if (!visited[wIndex] && e.weight < minEdge.weight) {
                        minEdge.weight = e.weight;
                        minEdge    = e;
                    }
                }
            }

            return minEdge;
        }





    public static MyGraph minimumSpanningTree(MyGraph g, int startingVertex){

        boolean[] visited = new boolean[g.vertices.size()];
        MyGraph mst = new MyGraph();


        for (int v : g.vertices) {
            mst.addVertex(v);
        }


        int startIndex = g.vertices.indexOf(0);
        visited[startIndex] = true;


        while (true) {
            // find the lowest frontier edge
            Edge e = getMinFrontierEdge(g, visited);
            if (e == null) break;   // no more edges

            // mark its endpoints visited
            int uIndex = g.vertices.indexOf(e.v1);
            int vIndex = g.vertices.indexOf(e.v2);
            visited[uIndex] = true;
            visited[vIndex] = true;

            // add that edge into our MST
            mst.addEdge(e.v1, e.v2, e.weight);
        }

        return mst;
    }

    public static int getMinDistVertex(MyGraph g, List<Integer> unvisitedList, int[] dist) {
        int minVertex = -1;
        int minDist = Integer.MAX_VALUE;

        for (int v : unvisitedList) {
            if (dist[v] < minDist) {
                minDist = dist[v];
                minVertex = v;
            }
        }

        return minVertex;
    }

    public static void shortestPath(MyGraph g, int startingVertex) {
        int n = g.vertices.size();
        int[] dist = new int[n];
        int[] prev = new int[n];
        boolean[] visited = new boolean[n];
        List<Integer> unvisited = new ArrayList<>(n);

        // 1) Initialization
        for (int i = 0; i < n; i++) {
            dist[i] = Integer.MAX_VALUE;
            prev[i] = -1;
            visited[i] = false;
            unvisited.add(i);
        }
        dist[startingVertex] = 0;

        // 2) Main loop
        while (!unvisited.isEmpty()) {
            int u = getMinDistVertex(g, unvisited, dist);
            // If remaining vertices are unreachable, we can break early
            if (u == -1 || dist[u] == Integer.MAX_VALUE) {
                break;
            }

            unvisited.remove((Integer)u);
            visited[u] = true;

            // Relax every edge u → w
            for (MyGraph. e : g.getNeighbors(u)) {
                int w = e.to;
                if (!visited[w]) {
                    int possibleDist = dist[u] + e.weight;
                    if (possibleDist < dist[w]) {
                        dist[w] = possibleDist;
                        prev[w] = u;
                    }
                }
            }
        }

        // 3) Print results
        System.out.println("Vertex : Distance from " + startingVertex + " : Previous");
        for (int v = 0; v < n; v++) {
            String d = (dist[v] == Integer.MAX_VALUE ? "∞" : Integer.toString(dist[v]));
            System.out.printf("  %2d   :       %4s       :   %2d%n",
                    v, d, prev[v]);
        }
    }


}





