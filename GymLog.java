package com.example.hw04_gymlog_v300;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "gym_logs")
public class GymLog {
    @PrimaryKey(autoGenerate = true)
    public long id;

    public String username;   // who created it
    public String exercise;
    public int setsCount;
    public int repsCount;
    public long createdAtMillis;

    public GymLog(String username, String exercise, int setsCount, int repsCount, long createdAtMillis) {
        this.username = username;
        this.exercise = exercise;
        this.setsCount = setsCount;
        this.repsCount = repsCount;
        this.createdAtMillis = createdAtMillis;
    }
}
