package org.example;

/**
 * Edge class used to make the edges between vertices
 * has a start, end, and a weight
 */
public class Edge {
    int v1;
    int v2;
    int weight;

    /**
     * Edge default constructor
     */
    public Edge(){
        this.v1 = 0;
        this.v2 =1;
        this.weight = 0;
    }

    /**
     * edge constructor for setting the v1, v2, and weight of an edge
     * @param v1
     * @param v2
     * @param weight
     */
    public Edge(int v1, int v2, int weight){
        this.v1 = v1;
        this.v2 = v2;
        this.weight = weight;
    }

    /**
     * to string method to print out the edges of a graph
     * @return
     */
    @Override
    public String toString() {
        return ("(" +v1 + " , " + v2 + ", " + weight+")");
    }
}

