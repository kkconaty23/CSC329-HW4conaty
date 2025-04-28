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
    public MyGraph() {
        this.vertices = new ArrayList<>();
        this.adjacencyList = new HashMap<>();
    }

    /**
     * add vertex method that can add a vertex onto the graph
     *
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
     *
     * @param v1
     * @param v2
     * @param weight
     */
    void addEdge(int v1, int v2, int weight) {
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
    void showGraph() {
        for (Map.Entry<Integer, List<Edge>> entry : adjacencyList.entrySet()) {
            // Print vertex and its edges
            System.out.println(entry.getKey() + ", " + entry.getValue());
        }
    }

    /**
     * method implemented to calculate the number of connected components
     *
     * @param g
     * @returns an array of the vertex # and which component it belongs to
     */
    public static int[] calculateConnectedComponents(MyGraph g) {

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


}





