package com.treasurehunt.madcamp_week3;

public class ListViewItem {
    private String time;
    private String latitude;
    private String longitude;

    public void setTime(String time2){
        time = time2;
    }

    public void setLatitude(String latitude2){
        latitude = latitude2;
    }

    public void setLongitude(String longitude2){
        longitude = longitude2;
    }

    public String getTime(){
        return this.time;
    }
    public String getLatitude(){
        return this.latitude;
    }
    public String getLongitude(){
        return this.longitude;
    }
}
