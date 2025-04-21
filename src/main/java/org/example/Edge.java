package org.example;

public class Edge {
    int v1;
    int v2;
    int weight;

    public Edge(){
        this.v1 = 0;
        this.v2 =1;
        this.weight = 0;
    }

    public Edge(int v1, int v2, int weight){
        this.v1 = v1;
        this.v2 = v2;
        this.weight = weight;
    }
}

