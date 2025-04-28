package org.example;

import java.util.ArrayList;
import java.util.List;

import static org.example.MyGraph.calculateConnectedComponents;

/**
 * Main method used for running and testing the graph
 */
public class Main {
    /**
     * Main runnable method
     * @param args
     */
    public static void main(String[] args) {
        MyGraph ccGraph = new MyGraph();//connected components graph
        for (int i = 0; i < 10; i++) {//add all vertices to the graph
            ccGraph.addVertex(i);
        }
        //add all graph edges
        ccGraph.addEdge(0, 2, 13);
        ccGraph.addEdge(0, 1, 2);
        ccGraph.addEdge(3, 5, 1);
        ccGraph.addEdge(4, 7, 1);
        ccGraph.addEdge(6, 8, 1);
        ccGraph.addEdge(6, 9, 4);

        //new array to store output of calculateConnectedComponents() method
        int[] components = calculateConnectedComponents(ccGraph);

        //connected components array printing format
        System.out.println("Connected Components:");
        System.out.println("Vertex " + "   Component #");
        for (int i = 0; i < components.length; i++) {

            System.out.println(ccGraph.vertices.get(i) + "        " + components[i]);
        }
        //second graph
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

    /**
     * method getting the min frontier edge showing which adjacent vertex has not been visited yet
     * @param g
     * @param visited
     * @return
     */
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

    /**
     * Minnumim spanning tree method to showing where all vertices are connected and the minimum
     * weight of all edges in the subgraph is minimized
     * and there are no cycles.
     * @param g
     * @param startingVertex
     * @return
     */
    public static MyGraph minimumSpanningTree(MyGraph g, int startingVertex){
        boolean[] visited = new boolean[g.vertices.size()];//track visited vertices default false
        MyGraph mst = new MyGraph();//new graph for MST


        for (int i = 0; i < g.vertices.size(); i++) {//add all vertices to the graph
            mst.addVertex(i);
        }


        int startIndex = g.vertices.indexOf(startingVertex);//input starting vertx is the start point (0)
        visited[startIndex] = true;//set start to true


        while (true) {
            // find the lowest frontier edge
            Edge min = getMinFrontierEdge(g, visited);
            if (min.weight == Integer.MAX_VALUE) break;// no more edges

            // mark its endpoints visited
            int sourceIndex = g.vertices.indexOf(min.v1);
            int destIndex = g.vertices.indexOf(min.v2);
            visited[sourceIndex] = true;
            visited[destIndex] = true;

            // add that edge into our MST
            mst.addEdge(min.v1, min.v2, min.weight);
        }

        return mst;
    }

    /**
     * method GetMinDistVertex searches the list of unvisited vertices for
     * the one that is closest to the source
     * @param g
     * @param unvisitedList
     * @param dist
     * @return
     */
    public static int getMinDistVertex(MyGraph g, List<Integer> unvisitedList, int[] dist) {

        if (unvisitedList.isEmpty()) {
            return -1; //if the unvisited list is empty
        }

        int minNeighborVertex = unvisitedList.get(0);
        int minNeighborDist = dist[g.vertices.indexOf(minNeighborVertex)];

        //go through all vertices in unvisited list
        for (int v : unvisitedList) {
            int vIndex = g.vertices.indexOf(v);
            // If  smaller distance found, update our variables
            if (dist[vIndex] < minNeighborDist) {
                minNeighborDist = dist[vIndex];
                minNeighborVertex = v;
            }
        }

        return minNeighborVertex;
    }

    /**
     *method to find the shortest path between vertices in a graph
     * @param g
     * @param startingVertex
     */
    public static void shortestPath(MyGraph g, int startingVertex) {
        int numVertices = g.vertices.size();

        //needed arrays
        int[] dist = new int[numVertices];
        int[] previous = new int[numVertices];
        boolean[] visited = new boolean[numVertices];
        List<Integer> unvisitedList = new ArrayList<>();

        // Set all vertex distances to max value, all previous to -1, all visited to F,
        for (int i = 0; i < numVertices; i++) {
            dist[i] = Integer.MAX_VALUE;
            previous[i] = -1;
            visited[i] = false;
            unvisitedList.add(g.vertices.get(i)); //add vertex values to unvisitedList
        }

        //starting vertex distance = 0
        int startIndex = g.vertices.indexOf(startingVertex);
        dist[startIndex] = 0;

        //loop to process unvisited neighbors
        while (!unvisitedList.isEmpty()) {
            //get min distance
            int currV = getMinDistVertex(g, unvisitedList, dist);

            //remove current vertex from unvisited list and mark as visited
            unvisitedList.remove(Integer.valueOf(currV));
            int currVIndex = g.vertices.indexOf(currV);
            visited[currVIndex] = true;

            //check unvisited neighbors of current
            List<Edge> edges = g.adjacencyList.get(currV);
            for (Edge edge : edges) {
                int neighborVertex = edge.v2;
                int neighborIndex = g.vertices.indexOf(neighborVertex);

                if (!visited[neighborIndex]) {
                    //calculate distance
                    int possibleDist = dist[currVIndex] + edge.weight;

                    //ifshorter path found, update distance and previous
                    if (possibleDist < dist[neighborIndex]) {
                        dist[neighborIndex] = possibleDist;
                        previous[neighborIndex] = currV;
                    }
                }
            }
        }



        System.out.println("Vertex      Distance       Previous");

        for (int i = 0; i < numVertices; i++) {
            int vertex = g.vertices.get(i);
            String distance = (dist[i] == Integer.MAX_VALUE) ? "∞" : String.valueOf(dist[i]);
            System.out.println(vertex + "               " + distance + "            " + previous[i]);
        }
    }



}