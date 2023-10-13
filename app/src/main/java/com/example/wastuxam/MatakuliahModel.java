package com.example.wastuxam;

public class MatakuliahModel {

    private String docID;
    private String name;
    private int noOfLevels;

    public MatakuliahModel(String docID, String name, int noOfLevels) {
        this.docID = docID;
        this.name = name;
        this.noOfLevels = noOfLevels;
    }

    public String getDocID() {
        return docID;
    }

    public void setDocID(String docID) {
        this.docID = docID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNoOfLevels() {
        return noOfLevels;
    }

    public void setNoOfLevels(int noOfLevels) {
        this.noOfLevels = noOfLevels;
    }
}
