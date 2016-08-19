package com.pcastanha.travelguide.models;

/**
 * Created by pedro.matos.castanha on 8/19/2016.
 */
public class Location {

    private long idSequence;
    private String name;
    private double latitude;
    private double longitude;
    private String description;
    private double numLikes;
    private String country;
    private String city;
    private String province;

    public void setIdSequence(long idSequence) {
        this.idSequence = idSequence;
    }

    public long getIdSequence() {
        return idSequence;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setNumLikes(double numLikes) {
        this.numLikes = numLikes;
    }

    public double getNumLikes() {
        return numLikes;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountry() {
        return country;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCity() {
        return city;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getProvince() {
        return province;
    }
}
