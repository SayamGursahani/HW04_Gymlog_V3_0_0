package com.example.hw04_gymlog_v300;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface GymLogDAO {

    @Insert
    long insert(GymLog log);

    @Query("SELECT * FROM gym_logs WHERE username = :username ORDER BY createdAtMillis DESC")
    List<GymLog> getAllForUser(String username);

    @Query("DELETE FROM gym_logs")
    void nuke();
}
