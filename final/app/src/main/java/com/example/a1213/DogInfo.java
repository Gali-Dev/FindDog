package com.example.a1213;


import java.util.Date;

public class DogInfo {
    public int dogNo;
    public String dogName;
    public int dogImage;
    public String dogInfo;
    public String dogKinds;
    public String relationship;
    public double lat;
    public double lng;
    public double distance;

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public void setDogNo(int dogNo) {
        this.dogNo = dogNo;
    }

    public void setDogName(String dogName) {
        this.dogName = dogName;
    }

    public void setDogImage(int dogImage) {
        this.dogImage = dogImage;
    }

    public void setDogInfo(String dogInfo) {
        this.dogInfo = dogInfo;
    }

    public void setDogKinds(String dogKinds) {
        this.dogKinds = dogKinds;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public int getDogNo() {
        return dogNo;
    }

    public String getDogName() {
        return dogName;
    }

    public int getDogImage() {
        return dogImage;
    }

    public String getDogInfo() {
        return dogInfo;
    }

    public String getDogKinds() {
        return dogKinds;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }

    public double getDistance() {
        return distance;
    }
}