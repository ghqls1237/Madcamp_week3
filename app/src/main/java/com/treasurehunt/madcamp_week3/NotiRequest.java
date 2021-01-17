package com.treasurehunt.madcamp_week3;

import java.util.List;

public class NotiRequest {
    String operation; //"create"
    String notification_key_name; //grou pidentifier
    List<String> registration_ids; //list of device tokens

    NotiRequest(String operation, String nkn, List<String> ri){
        this.operation = operation;
        this.notification_key_name = nkn;
        this.registration_ids = ri;
    }

}
