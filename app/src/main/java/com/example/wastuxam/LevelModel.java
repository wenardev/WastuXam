package com.example.wastuxam;

public class LevelModel {

    private String levelID;
    private int topScore;
    private int time;

    public LevelModel(String levelID, int topScore, int time) {
        this.levelID = levelID;
        this.topScore = topScore;
        this.time = time;
    }

    public String getLevelID() {
        return levelID;
    }

    public void setLevelID(String levelID) {
        this.levelID = levelID;
    }

    public int getTopScore() {
        return topScore;
    }

    public void setTopScore(int topScore) {
        this.topScore = topScore;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
