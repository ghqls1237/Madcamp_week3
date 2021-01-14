package com.example.madcamp_week3;

public class User {
    final String uid;
    final String email;
    final String method;
    final String nickname;
    User(String uid, String email, String method, String nickname){
        this.uid = uid;
        this.email = email;
        this.method = method;
        this.nickname = nickname;
    }
}
