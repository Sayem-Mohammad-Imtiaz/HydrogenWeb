package model;

import java.util.ArrayList;

public class Summary {
    private String version1Name;
    private String version2Name;
    private Integer version1Loc;
    private Integer version2Loc;
    private Integer churnRate;
    private Integer mvicfgNumNode;

    public ArrayList<String> getPathsRemoved() {
        return pathsRemoved;
    }

    public void setPathsRemoved(ArrayList<String> pathsRemoved) {
        this.pathsRemoved = pathsRemoved;
    }

    private Float mvicfgBuildTime;
    private ArrayList<String> pathsAdded;
    private ArrayList<String> pathsRemoved;

    public ArrayList<String> getPathsAdded() {
        return pathsAdded;
    }

    public void setPathsAdded(ArrayList<String> pathsAdded) {
        this.pathsAdded = pathsAdded;
    }

    public Float getMvicfgBuildTime() {
        return mvicfgBuildTime;
    }

    public void setMvicfgBuildTime(Float mvicfgBuildTime) {
        this.mvicfgBuildTime = mvicfgBuildTime;
    }

    public Integer getMvicfgNumNode() {
        return mvicfgNumNode;
    }

    public void setMvicfgNumNode(Integer mvicfgNumNode) {
        this.mvicfgNumNode = mvicfgNumNode;
    }

    public Integer getMvicfgNumEdge() {
        return mvicfgNumEdge;
    }

    public void setMvicfgNumEdge(Integer mvicfgNumEdge) {
        this.mvicfgNumEdge = mvicfgNumEdge;
    }

    private Integer mvicfgNumEdge;


    public String getVersion1Name() {
        return version1Name;
    }

    public void setVersion1Name(String version1Name) {
        this.version1Name = version1Name;
    }

    public String getVersion2Name() {
        return version2Name;
    }

    public void setVersion2Name(String version2Name) {
        this.version2Name = version2Name;
    }

    public Integer getVersion1Loc() {
        return version1Loc;
    }

    public Integer getChurnRate() {
        return churnRate;
    }

    public void setChurnRate(Integer churnRate) {
        this.churnRate = churnRate;
    }

    public void setVersion1Loc(Integer version1Loc) {
        this.version1Loc = version1Loc;
    }

    public Integer getVersion2Loc() {
        return version2Loc;
    }

    public void setVersion2Loc(Integer version2Loc) {
        this.version2Loc = version2Loc;
    }

}

