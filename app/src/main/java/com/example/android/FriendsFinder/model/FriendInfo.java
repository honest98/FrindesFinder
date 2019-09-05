package com.example.android.FriendsFinder.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;

@Entity(tableName = "friends")
public class FriendInfo {

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "lat")
    private Double lat;

    @ColumnInfo(name = "lng")
    private Double lng;

    @ColumnInfo(name = "email")
    private String email;

    @ColumnInfo(name = "imageUrl")
    private String imageUrl;


    public FriendInfo(String name,Double lat,Double lng,String email, String imageUrl) {
        this.imageUrl = imageUrl;
        this.name = name;
        this.lat = lat;
        this.lng = lng;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
