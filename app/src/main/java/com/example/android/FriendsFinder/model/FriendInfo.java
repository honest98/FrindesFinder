package com.example.android.FriendsFinder.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;

public class FriendInfo {

    private String name;

    private Double lat;

    private Double lng;

    private String email;

    private String imageUrl;

    private String userId;


    public FriendInfo(String name,Double lat,Double lng,String email, String imageUrl, String userId) {
        this.imageUrl = imageUrl;
        this.name = name;
        this.lat = lat;
        this.lng = lng;
        this.email = email;
        this.userId = userId;
    }

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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
