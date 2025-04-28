package org.example;

import java.util.ArrayList;
import java.util.List;

import static org.example.MyGraph.calculateConnectedComponents;

public class Main {
    public static void main(String[] args) {
        MyGraph ccGraph = new MyGraph();
        for (int i = 0; i < 10; i++) {
            ccGraph.addVertex(i);
        }
        ccGraph.addEdge(0, 2, 13);
        ccGraph.addEdge(0, 1, 2);
        ccGraph.addEdge(3, 5, 1);
        ccGraph.addEdge(4, 7, 1);
        ccGraph.addEdge(6, 8, 1);
        ccGraph.addEdge(6, 9, 4);

        int[] components = calculateConnectedComponents(ccGraph);

        // Print the components
        System.out.println("Connected Components:");
        System.out.println("Vertex " + "   Component #");
        for (int i = 0; i < components.length; i++) {

            System.out.println(ccGraph.vertices.get(i) + "        " + components[i]);
        }
            MyGraph g = new MyGraph();
            g.addEdge(0, 2, 13);
            g.addEdge(0, 1, 2);
            g.addEdge(0, 3, 7);
            g.addEdge(1, 3, 2);
            g.addEdge(2, 4, 2);
            g.addEdge(2, 5, 7);
            g.addEdge(3, 5, 1);
            g.addEdge(3, 6, 5);
            g.addEdge(4, 7, 1);
            g.addEdge(4, 7, 4);
            g.addEdge(5, 8, 1);
            g.addEdge(6, 8, 1);
            g.addEdge(6, 9, 9);
            g.addEdge(7, 8, 2);
            g.addEdge(8, 9, 4);

//            int[] components2 = calculateConnectedComponents(g);
//
//            System.out.println("Connected Components 2:");
//            System.out.println("Vertex " + "   Component #");
//            for (int k = 0; k < components2.length; k++) {
//
//                System.out.println(g.vertices.get(k) + "        " + components2[k]);
//
//
//            }
        System.out.println("Shortest Path:");
        shortestPath(g,0);
        MyGraph mstGraph = minimumSpanningTree(g,0);
        System.out.println("Minimum Spanning Tree:");
        mstGraph.showGraph();

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
                    minEdge = e;
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


        int startIndex = g.vertices.indexOf(startingVertex);
        visited[startIndex] = true;


        while (true) {
            // find the lowest frontier edge
            Edge e = getMinFrontierEdge(g, visited);
            if (e.weight == Integer.MAX_VALUE) break;// no more edges

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

        if (unvisitedList.isEmpty()) {
            return -1; //if the unvisited list is empty
        }

        int minNeighborVertex = unvisitedList.get(0);
        int minNeighborDist = dist[g.vertices.indexOf(minNeighborVertex)];

        // Iterate through all vertices in unvisited list
        for (int v : unvisitedList) {
            int vIndex = g.vertices.indexOf(v);
            // If we find a vertex with smaller distance, update our variables
            if (dist[vIndex] < minNeighborDist) {
                minNeighborDist = dist[vIndex];
                minNeighborVertex = v;
            }
        }

        return minNeighborVertex;
    }

    public static void shortestPath(MyGraph g, int startingVertex) {
        int numVertices = g.vertices.size();

        // Initialize arrays
        int[] dist = new int[numVertices];
        int[] previous = new int[numVertices];
        boolean[] visited = new boolean[numVertices];
        List<Integer> unvisitedList = new ArrayList<>();

        // Set all vertex distances to max value (infinity)
        for (int i = 0; i < numVertices; i++) {
            dist[i] = Integer.MAX_VALUE;
            previous[i] = -1;
            visited[i] = false;
            unvisitedList.add(g.vertices.get(i)); // Add actual vertex values to unvisitedList
        }

        // Set starting vertex distance to 0
        int startIndex = g.vertices.indexOf(startingVertex);
        dist[startIndex] = 0;

        // Main algorithm loop
        while (!unvisitedList.isEmpty()) {
            // Get vertex with min distance
            int currV = getMinDistVertex(g, unvisitedList, dist);

            // Remove current vertex from unvisited list and mark as visited
            unvisitedList.remove(Integer.valueOf(currV));
            int currVIndex = g.vertices.indexOf(currV);
            visited[currVIndex] = true;

            // Check all unvisited neighbors of currV
            List<Edge> edges = g.adjacencyList.get(currV);
            for (Edge edge : edges) {
                int neighborVertex = edge.v2;
                int neighborIndex = g.vertices.indexOf(neighborVertex);

                if (!visited[neighborIndex]) {
                    // Calculate possible distance through current vertex
                    int possibleDist = dist[currVIndex] + edge.weight;

                    // If we found a shorter path, update distance and previous
                    if (possibleDist < dist[neighborIndex]) {
                        dist[neighborIndex] = possibleDist;
                        previous[neighborIndex] = currV;
                    }
                }
            }
        }

        // Print results
        System.out.println("Shortest paths from vertex " + startingVertex + ":");
        System.out.println("Vertex\tDistance\tPrevious");

        for (int i = 0; i < numVertices; i++) {
            int vertex = g.vertices.get(i);
            String distance = (dist[i] == Integer.MAX_VALUE) ? "∞" : String.valueOf(dist[i]);
            System.out.println(vertex + "\t" + distance + "\t\t" + previous[i]);
        }
    }



}