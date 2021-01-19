package com.treasurehunt.madcamp_week3;


import android.content.Intent;
//import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseOptions;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.JsonArray;
import com.treasurehunt.madcamp_week3.R;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
//import com.google.android.gms.common.api.Response;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends AppCompatActivity {
    FirebaseAuth auth ;
    CallbackManager callbackManager;
    String[] info_list = {"email", "public_profile"};
    String device_token;

    //retrofit
    RetrofitClient retrofitClient = new RetrofitClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        System.out.println("login activity");
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

        setContentView(R.layout.activity_login);
        FirebaseApp.initializeApp(this);
        auth = FirebaseAuth.getInstance();
        callbackManager = CallbackManager.Factory.create();

        //Read the token of the phone.
        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.w("FirebaseSettingEx", "getInstanceId failed", task.getException());
                return;
            }
            device_token = task.getResult().getToken();
//            System.out.println("token: " + device_token);
//            System.out.println("token length: " + device_token.length());
        });


//        String operation = "create";
//        String nkn = auth.getCurrentUser().getDisplayName();
//        List<String> ri = new ArrayList<>();
//        ri.add(device_token);
////        String[] ri = {device_token};
//
//        NotiRequest notiRequest = new NotiRequest(operation, nkn, ri);
////        Call<NotiKey> call = retrofitNotiClient.apiService.getNotiKey(notiRequest);
//        Call<NotiKey> call = retrofitNotiClient.apiService.getNotiKey(operation, nkn, ri);
//        System.out.println("before enqueing getNotiKey request");
//        call.enqueue(new Callback<NotiKey>() {
//            @Override
//            public void onResponse(Call<NotiKey> call, Response<NotiKey> response) {
//                if (response.isSuccessful()) {
//                    System.out.println("getnotikey response is successful");
//                    String notiKey = response.body().getNotification_key();
//                    System.out.println("notikey: " + notiKey);
//                }
//                else{
//                    System.out.println("getnotikey response is not successful");
//                }
//            }
//            @Override
//            public void onFailure(Call<NotiKey> call, Throwable t) {
//                System.out.println("getnotikey failure");
//            }
//        });


        if(auth.getCurrentUser() != null){
//            System.out.println("current user exists");
            //Login state
//            System.out.println(auth.getCurrentUser().getUid());
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();

        }
        else{
            //should login
//            System.out.println("you should login first!!");
        }
        Button facebookBtn = findViewById(R.id.login_button);
        facebookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                facebookLogin();
            }
        });

    }

    private void facebookLogin() {
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList(info_list));
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

            @Override
            public void onSuccess(LoginResult loginResult) {
//                System.out.println("Facebook Login Success");
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
//                System.out.println("Facebook Login Cancel");
            }

            @Override
            public void onError(FacebookException error) {
//                System.out.println("Facebook Login Error");
            }
        });
    }

    private void handleFacebookAccessToken(AccessToken token){
//        System.out.println("handleFacebookAccessToken : " + token.toString());
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
//        System.out.println("Credential is " + credential);
        auth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    //Login success
//                    System.out.println("signInWithCredential:Success");
                    String email = auth.getCurrentUser().getEmail();
                    String nickname = auth.getCurrentUser().getDisplayName();
                    String uid = auth.getCurrentUser().getUid();
                    String method =  auth.getCurrentUser().getProviderData().get(1).getProviderId().toString().toLowerCase();
                    User user = new User(uid, email, method, nickname, device_token);
//                    System.out.println(email);
//                    System.out.println(nickname);
//                    System.out.println(uid);
//                    System.out.println(method);
//                    System.out.println(device_token);
                    //Retrofit use
                    Call<String> call = retrofitClient.apiService.login(user);
                    call.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
//                            System.out.println("login 통신 성공함");
                        }
                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
//                            System.out.println("login 통신 실패");
                        }

                    });

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    //Login Fail
//                    System.out.println("signInWithCredential:Failure : " + task);
                    Toast.makeText(LoginActivity.this, "Login fail", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 페이스북 콜백 등록
        callbackManager.onActivityResult(requestCode, resultCode, data);
        //
    }

//    private void logout(){
//        auth.signOut();
//    }




}