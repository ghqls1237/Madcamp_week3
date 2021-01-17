package com.treasurehunt.madcamp_week3;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NotiKey {
    @SerializedName("notification_key")
    @Expose
    String notification_key;

    NotiKey(String nk){
        this.notification_key = nk;
    }

    public String getNotification_key() {
        return notification_key;
    }

}
