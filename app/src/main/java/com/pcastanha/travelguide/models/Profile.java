package com.pcastanha.travelguide.models;

import android.graphics.Bitmap;

public class Profile {

    private long idSequence;
    private String userName;
    private int citiesVisited;
    private int upcomingTrips;
    private long likesTotal;
    private Bitmap photo;
    private double complete;

    public void setIdSequence(long idSequence) {
        this.idSequence = idSequence;
    }

    public long getIdSequence() {
        return this.idSequence;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setCitiesVisited(int citiesVisited) {
        this.citiesVisited = citiesVisited;
    }

    public int getCitiesVisited() {
        return citiesVisited;
    }

    public void setUpcomingTrips(int upcomingTrips) {
        this.upcomingTrips = upcomingTrips;
    }

    public int getUpcomingTrips() {
        return upcomingTrips;
    }

    public void setLikesTotal(long likesTotal) {
        this.likesTotal = likesTotal;
    }

    public long getLikesTotal() {
        return likesTotal;
    }

    public void setPhoto(Bitmap photo) {
        this.photo = photo;
    }

    public Bitmap getPhoto() {
        return photo;
    }

    public void setComplete(float complete) {
        this.complete = complete;
    }

    public double getComplete() {
        return complete;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Profile) {
            if (((Profile) obj).getIdSequence() == this.getIdSequence()) {
                return true;
            }
            return false;
        }
        return false;
    }

    @Override
    public String toString() {
        return this.getUserName();
    }
}