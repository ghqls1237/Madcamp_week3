package com.treasurehunt.madcamp_week3;

public class DeviceLocation {
    final String token;
    final String latitude;
    final String longitude;

    DeviceLocation(String token, String lat, String lon){
        this.token = token;
        this.latitude = lat;
        this.longitude = lon;
    }
}