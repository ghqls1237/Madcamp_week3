package com.treasurehunt.madcamp_week3;

public class DeviceLocation {
    final String token;
    final String latitude;
    final String longitude;
    final String uid;

    DeviceLocation(String token, String lat, String lon, String uid){
        this.token = token;
        this.latitude = lat;
        this.longitude = lon;
        this.uid = uid;
    }
}