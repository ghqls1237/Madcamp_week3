package com.treasurehunt.madcamp_week3;

public class User {
    final String uid;
    final String email;
    final String method;
    final String nickname;
    final String token;
    User(String uid, String email, String method, String nickname, String token){
        this.uid = uid;
        this.email = email;
        this.method = method;
        this.nickname = nickname;
        this.token = token;
    }

}
