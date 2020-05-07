package model;

import java.util.ArrayList;

public class Graph {
    private ArrayList<Integer> nodes;

    public Graph() {
        this.nodes=new ArrayList<>();
        adj= new ArrayList<ArrayList<Integer> >(10000);
        for (int i = 0; i < 10000; i++)
            adj.add(new ArrayList<Integer>());
    }

    public ArrayList<Integer> getNodes() {
        return nodes;
    }

    public void setNodes(ArrayList<Integer> nodes) {
        this.nodes = nodes;
    }

    public ArrayList<ArrayList<Integer>> getAdj() {
        return adj;
    }

    public void setAdj(ArrayList<ArrayList<Integer>> adj) {
        this.adj = adj;
    }

    private ArrayList<ArrayList<Integer>> adj;

}
