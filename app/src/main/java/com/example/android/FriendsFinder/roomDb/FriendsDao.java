package com.example.android.FriendsFinder.roomDb;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.android.FriendsFinder.model.FriendInfo;

import java.util.List;

@Dao
public interface FriendsDao {


    @Insert
    void insertAll(Friends... friend);

    @Query("SELECT * FROM friends")
    List<FriendInfo> getAll();



}
