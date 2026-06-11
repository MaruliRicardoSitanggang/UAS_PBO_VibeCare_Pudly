package com.CareVibe.model;

public class DailyMission {
    private String missionName;
    private String description;
    private int rewardPoints;
    private boolean isCompleted;

    public DailyMission(String missionName, String description, int rewardPoints) {
        this.missionName = missionName;
        this.description = description;
        this.rewardPoints = rewardPoints;
        this.isCompleted = false;
    }

    public String getMissionName() { return missionName; }
    public String getDescription() { return description; }
    public int getRewardPoints() { return rewardPoints; }
    public boolean isCompleted() { return isCompleted; }
    public void setCompleted(boolean completed) { isCompleted = completed; }
}