package com.example.android.FriendsFinder.roomDb;


import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Friends.class}, version = 2)
public abstract class FriendsDatabase extends RoomDatabase {

    public abstract FriendsDao friendsDao();

}
